package com.iam57.simpleteleport.entity;

import com.iam57.simpleteleport.enums.TeleportRequestType;
import com.iam57.simpleteleport.strategy.TeleportStrategy;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

/**
 * @author iam57
 * @since 2024-05-13 19:50
 */
@Getter
public class TeleportRequest {
    private final Player requester;
    private final Player recipient;
    private final TeleportRequestType type;
    private final TeleportStrategy teleportStrategy;
    @Setter
    private BukkitTask expirationTimer;

    public TeleportRequest(Player requester, Player recipient, TeleportRequestType type, TeleportStrategy teleportStrategy) {
        this.requester = requester;
        this.teleportStrategy = teleportStrategy;
        this.type = type;
        this.recipient = recipient;
    }
}
