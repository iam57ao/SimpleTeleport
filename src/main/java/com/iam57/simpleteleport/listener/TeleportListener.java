package com.iam57.simpleteleport.listener;

import com.iam57.simpleteleport.util.MessageUtil;
import com.iam57.simpleteleport.entity.TeleportRequest;
import com.iam57.simpleteleport.event.*;
import com.iam57.simpleteleport.service.TeleportManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author iam57
 * @since 2024-05-14 11:47
 */
public class TeleportListener implements Listener {
    private static final TeleportManager teleportManager = TeleportManager.getInstance();

    private void handleTeleportRequest(TeleportRequest teleportRequest, String requesterMessageKey, String recipientMessageKey) {
        Player requester = teleportRequest.getRequester();
        Player recipient = teleportRequest.getRecipient();
        requester.sendMessage(MessageUtil.getAndReplacePlayer(requesterMessageKey, recipient.getName()));
        recipient.sendMessage(MessageUtil.getAndReplacePlayer(recipientMessageKey, requester.getName()));
    }

    @EventHandler
    public void onTeleportRequestSuccessEvent(TeleportRequestSuccessEvent event) {
        TeleportRequest teleportRequest = event.getTeleportRequest();
        Player requester = teleportRequest.getRequester();
        switch (teleportRequest.getType()) {
            case REQUEST -> {
                requester.sendMessage(MessageUtil.get("tp-request-sent"));
                teleportRequest.getRecipient().sendMessage(MessageUtil.getAndReplacePlayer("tp-request", requester.getName()));
            }
            case INVITE -> {
                requester.sendMessage(MessageUtil.get("tp-invite-sent"));
                teleportRequest.getRecipient().sendMessage(MessageUtil.getAndReplacePlayer("tp-invite", requester.getName()));
            }
        }

    }

    @EventHandler
    public void onTeleportRequestFailEvent(TeleportRequestFailEvent event) {
        Player requester = event.getPlayer();
        switch (event.getReason()) {
            case PLAYER_NOT_FOUND -> requester.sendMessage(MessageUtil.get("player-not-found"));
            case PLAYER_IN_COOLDOWN -> requester.sendMessage(MessageUtil.get("player-in-cooldown"));
            case REQUEST_ALREADY_SENT -> requester.sendMessage(MessageUtil.get("request-already-sent"));
            case SELF_TELEPORT -> requester.sendMessage(MessageUtil.get("self-teleport"));
            case NO_REQUEST -> requester.sendMessage(MessageUtil.get("no-request"));
        }
    }

    @EventHandler
    public void onTeleportRequestTimeoutEvent(TeleportRequestTimeoutEvent event) {
        TeleportRequest teleportRequest = event.getTeleportRequest();
        switch (teleportRequest.getType()) {
            case INVITE -> handleTeleportRequest(
                    teleportRequest,
                    "tp-invite-sent-expiration",
                    "tp-invite-expiration"
            );
            case REQUEST -> handleTeleportRequest(
                    teleportRequest,
                    "tp-request-sent-expiration",
                    "tp-request-expiration"
            );
        }
    }

    @EventHandler
    public void onAcceptTeleportRequestEvent(AcceptTeleportRequestEvent event) {
        TeleportRequest teleportRequest = event.getTeleportRequest();
        switch (teleportRequest.getType()) {
            case INVITE -> handleTeleportRequest(
                    teleportRequest,
                    "tp-invite-accept",
                    "tp-invite-sent-accept"
            );
            case REQUEST -> handleTeleportRequest(
                    teleportRequest,
                    "tp-request-accept",
                    "tp-request-sent-accept"
            );
        }
    }

    @EventHandler
    public void onRejectTeleportRequestEvent(RejectTeleportRequestEvent event) {
        TeleportRequest teleportRequest = event.getTeleportRequest();
        switch (teleportRequest.getType()) {
            case INVITE -> handleTeleportRequest(
                    teleportRequest,
                    "tp-invite-deny",
                    "tp-invite-sent-deny"
            );
            case REQUEST -> handleTeleportRequest(
                    teleportRequest,
                    "tp-request-deny",
                    "tp-request-sent-deny"
            );
        }
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        teleportManager.removeTeleportPlayer(event.getPlayer());
    }
}
