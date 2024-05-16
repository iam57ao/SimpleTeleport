package com.iam57.simpleteleport;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;


/**
 * @author iam57
 * @since 2024-05-13 20:24
 */
public class Config {
    private static final Plugin plugin = SimpleTeleport.getPlugin(SimpleTeleport.class);

    public static FileConfiguration getConfig() {
        return plugin.getConfig();
    }

    public static Long getRequestCooldownTime() {
        return getConfig().getLong("configs.request-cooldown-time");
    }

    public static Long getRequestExpirationTime() {
        return getConfig().getLong("configs.request-expiration-time");
    }
}
