package com.iam57.simpleteleport.strategy;

import com.iam57.simpleteleport.enums.TeleportRequestType;
import com.iam57.simpleteleport.strategy.impl.InviteTeleportStrategy;
import com.iam57.simpleteleport.strategy.impl.RequestTeleportStrategy;

/**
 * @author iam57
 * @since 2024-05-14 20:40
 */
public class TeleportStrategyFactory {
    public static TeleportStrategy createStrategy(TeleportRequestType type) {
        return switch (type) {
            case REQUEST -> new RequestTeleportStrategy();
            case INVITE -> new InviteTeleportStrategy();
        };
    }
}
