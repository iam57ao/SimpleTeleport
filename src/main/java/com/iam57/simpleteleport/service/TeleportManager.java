package com.iam57.simpleteleport.service;

import com.iam57.simpleteleport.Config;

/**
 * @author iam57
 * @since 2024-05-13 20:16
 */
public final class TeleportManager {
    private static Long requestCooldownTime;
    private static Long requestExpirationTime;
    private static TeleportManager instance;

    private TeleportManager() {
        requestCooldownTime = Config.getRequestCooldownTime();
        requestExpirationTime = Config.getRequestExpirationTime();
    }

    public static TeleportManager getInstance() {
        if (instance == null) {
            instance = new TeleportManager();
        }
        return instance;
    }

}
