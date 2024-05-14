package com.iam57.simpleteleport.strategy;

import com.iam57.simpleteleport.entity.TeleportRequest;

/**
 * @author iam57
 * @since 2024-05-13 20:06
 */
public interface TeleportStrategy {
    void executeTeleport(TeleportRequest teleportRequest);
}
