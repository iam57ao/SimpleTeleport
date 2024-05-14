package com.iam57.simpleteleport.listener;

import com.iam57.simpleteleport.Message;
import com.iam57.simpleteleport.entity.TeleportRequest;
import com.iam57.simpleteleport.enums.TeleportRequestType;
import com.iam57.simpleteleport.event.TeleportRequestFailEvent;
import com.iam57.simpleteleport.event.TeleportRequestSuccessEvent;
import com.iam57.simpleteleport.event.TeleportRequestTimeoutEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * @author iam57
 * @since 2024-05-14 11:47
 */
public class TeleportListener implements Listener {
    @EventHandler
    public void onTeleportRequestSuccessEvent(TeleportRequestSuccessEvent event) {
        TeleportRequest teleportRequest = event.getTeleportRequest();
        TeleportRequestType type = teleportRequest.getType();
        Player requester = teleportRequest.getRequester();
        switch (type) {
            case REQUEST -> {
                requester.sendMessage(Message.get("tp-request-sent"));
                teleportRequest.getRecipient().sendMessage(Message.getAndReplacePlayer("tp-request", requester.getName()));
            }
            case INVITE -> {
                requester.sendMessage(Message.get("tp-invite-sent"));
                teleportRequest.getRecipient().sendMessage(Message.getAndReplacePlayer("tp-invite", requester.getName()));
            }
        }

    }

    @EventHandler
    public void onTeleportRequestFailEvent(TeleportRequestFailEvent event) {
        Player requester = event.getPlayer();
        switch (event.getReason()) {
            case PLAYER_NOT_FOUND -> requester.sendMessage(Message.get("player-not-found"));
            case PLAYER_IN_COOLDOWN -> requester.sendMessage(Message.get("player-in-cooldown"));
            case REQUEST_ALREADY_SENT -> requester.sendMessage(Message.get("request-already-sent"));
            case SELF_TELEPORT -> requester.sendMessage(Message.get("self-teleport"));
        }
    }

    @EventHandler
    public void onTeleportRequestTimeoutEvent(TeleportRequestTimeoutEvent event) {
        TeleportRequest teleportRequest = event.getTeleportRequest();
        TeleportRequestType type = teleportRequest.getType();
        Player requester = teleportRequest.getRequester();
        Player recipient = teleportRequest.getRecipient();
        switch (type) {
            case INVITE -> {
                requester.sendMessage(Message.getAndReplacePlayer("tp-invite-sent-expiration", recipient.getName()));
                recipient.sendMessage(Message.getAndReplacePlayer("tp-invite-expiration", requester.getName()));
            }
            case REQUEST -> {
                requester.sendMessage(Message.getAndReplacePlayer("tp-request-sent-expiration", recipient.getName()));
                recipient.sendMessage(Message.getAndReplacePlayer("tp-request-expiration", requester.getName()));
            }
        }

    }
}
