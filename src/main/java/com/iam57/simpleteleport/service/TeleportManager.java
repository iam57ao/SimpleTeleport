package com.iam57.simpleteleport.service;

import com.iam57.simpleteleport.Config;
import com.iam57.simpleteleport.SimpleTeleport;
import com.iam57.simpleteleport.entity.TeleportPlayer;
import com.iam57.simpleteleport.entity.TeleportRequest;
import com.iam57.simpleteleport.enums.TeleportCommandFailReason;
import com.iam57.simpleteleport.enums.TeleportRequestType;
import com.iam57.simpleteleport.event.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitTask;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * @author iam57
 * @since 2024-05-13 20:16
 */
public final class TeleportManager {
    private static TeleportManager instance;

    private final Plugin plugin;
    private final PluginManager pluginManager;
    private final Map<Player, TeleportPlayer> teleportPlayers;
    private Long requestCooldownTime;
    private Long requestExpirationTime;

    private TeleportManager() {
        requestCooldownTime = Config.getRequestCooldownTime();
        requestExpirationTime = Config.getRequestExpirationTime();
        pluginManager = Bukkit.getPluginManager();
        plugin = SimpleTeleport.getPlugin(SimpleTeleport.class);
        teleportPlayers = new HashMap<>();
    }

    public static TeleportManager getInstance() {
        TeleportManager result = instance;
        if (result != null) {
            return result;
        }
        synchronized (TeleportManager.class) {
            if (instance == null) {
                instance = new TeleportManager();
            }
            return instance;
        }
    }

    private TeleportPlayer getTeleportPlayer(Player player) {
        return teleportPlayers.computeIfAbsent(player, (k) -> new TeleportPlayer(player, requestCooldownTime));
    }

    private boolean canSendTeleportRequest(Player requester, String recipientName) {
        TeleportPlayer requesterTeleportPlayer = getTeleportPlayer(requester);
        if (requesterTeleportPlayer.inCooldown()) {
            pluginManager.callEvent(new TeleportRequestFailEvent(requester, TeleportCommandFailReason.PLAYER_IN_COOLDOWN));
            return false;
        }
        Player recipient = Bukkit.getPlayer(recipientName);
        if (requesterTeleportPlayer.getTeleportRequests().stream().anyMatch(teleportRequest -> teleportRequest.getRecipient().equals(recipient))) {
            pluginManager.callEvent(new TeleportRequestFailEvent(requester, TeleportCommandFailReason.REQUEST_ALREADY_SENT));
            return false;
        }
        if (recipient == null || !recipient.isOnline()) {
            pluginManager.callEvent(new TeleportRequestFailEvent(requester, TeleportCommandFailReason.PLAYER_NOT_FOUND));
            return false;
        }
        if (requester.equals(recipient)) {
            pluginManager.callEvent(new TeleportRequestFailEvent(requester, TeleportCommandFailReason.SELF_TELEPORT));
            return false;
        }
        return true;
    }

    private boolean canHandleTeleportRequest(Player recipient) {
        TeleportRequest lastRecipientTeleportRequest = getLastRecipientTeleportRequest(recipient);
        if (lastRecipientTeleportRequest == null) {
            pluginManager.callEvent(new TeleportRequestFailEvent(recipient, TeleportCommandFailReason.NO_REQUEST));
            return false;
        }
        if (!lastRecipientTeleportRequest.getRequester().isOnline()) {
            pluginManager.callEvent(new TeleportRequestFailEvent(recipient, TeleportCommandFailReason.PLAYER_NOT_FOUND));
            removeTeleportRequest(lastRecipientTeleportRequest);
            return false;
        }
        return true;
    }

    private void setTeleportRequestExpirationTimer(TeleportRequest teleportRequest) {
        BukkitTask expirationTimer = Bukkit.getScheduler().runTaskLater(plugin, () -> {
            removeTeleportRequest(teleportRequest);
            pluginManager.callEvent(new TeleportRequestTimeoutEvent(teleportRequest));
        }, requestExpirationTime * 20L);
        teleportRequest.setExpirationTimer(expirationTimer);
    }

    private TeleportRequest createTeleportRequest(Player requester, Player recipient, TeleportRequestType type) {
        TeleportRequest teleportRequest = new TeleportRequest(requester, recipient, type);
        setTeleportRequestExpirationTimer(teleportRequest);
        return teleportRequest;
    }

    private void removeTeleportRequest(TeleportRequest teleportRequest) {
        teleportRequest.getExpirationTimer().cancel();
        getTeleportPlayer(teleportRequest.getRequester()).getTeleportRequests().remove(teleportRequest);
        getTeleportPlayer(teleportRequest.getRecipient()).getTeleportRequests().remove(teleportRequest);
    }

    private TeleportRequest getLastRecipientTeleportRequest(Player recipient) {
        return getTeleportPlayer(recipient).getTeleportRequests().stream().filter(teleportRequest -> teleportRequest.getRecipient().equals(recipient)).findFirst().orElse(null);
    }

    public void removeTeleportPlayer(Player player) {
        teleportPlayers.remove(player);
    }

    public void sendTeleportRequest(Player requester, String recipientName, TeleportRequestType type) {
        if (!canSendTeleportRequest(requester, recipientName)) {
            return;
        }
        Player recipient = Bukkit.getPlayer(recipientName);
        TeleportRequest teleportRequest = createTeleportRequest(requester, recipient, type);
        TeleportPlayer requesterTeleportPlayer = getTeleportPlayer(requester);
        TeleportPlayer recipientTeleportPlayer = getTeleportPlayer(recipient);
        requesterTeleportPlayer.getTeleportRequests().add(0, teleportRequest);
        recipientTeleportPlayer.getTeleportRequests().add(0, teleportRequest);
        requesterTeleportPlayer.setLastSentRequestTime(Instant.now());
        pluginManager.callEvent(new TeleportRequestSuccessEvent(teleportRequest));
    }

    public void handleTeleportRequest(Player recipient, boolean isAccept) {
        if (!canHandleTeleportRequest(recipient)) {
            return;
        }
        TeleportRequest lastRecipientTeleportRequest = getLastRecipientTeleportRequest(recipient);
        if (isAccept) {
            lastRecipientTeleportRequest.getTeleportStrategy().executeTeleport(lastRecipientTeleportRequest);
            pluginManager.callEvent(new AcceptTeleportRequestEvent(lastRecipientTeleportRequest));
        } else {
            pluginManager.callEvent(new RejectTeleportRequestEvent(lastRecipientTeleportRequest));
        }
        removeTeleportRequest(lastRecipientTeleportRequest);
    }
}
