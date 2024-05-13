package com.iam57.simpleteleport.entity;

import com.iam57.simpleteleport.enums.TeleportRequestType;
import com.iam57.simpleteleport.strategy.TeleportStrategy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

/**
 * @author iam57
 * @since 2024-05-13 19:50
 */
@Getter
@AllArgsConstructor
public class TeleportRequest {
    private Player requester;
    private Player recipient;
    private TeleportRequestType type;
    private TeleportStrategy teleportStrategy;
}
