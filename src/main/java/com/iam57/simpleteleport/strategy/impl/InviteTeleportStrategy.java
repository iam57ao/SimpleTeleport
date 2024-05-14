package com.iam57.simpleteleport.strategy.impl;

import com.iam57.simpleteleport.entity.TeleportRequest;
import com.iam57.simpleteleport.strategy.TeleportStrategy;
import org.bukkit.entity.Player;

/**
 * @author iam57
 * @since 2024-05-13 20:08
 */
public class InviteTeleportStrategy implements TeleportStrategy {
    @Override
    public void executeTeleport(TeleportRequest teleportRequest) {
        Player requester = teleportRequest.getRequester();
        Player recipient = teleportRequest.getRecipient();
        recipient.teleport(requester);
    }
}
