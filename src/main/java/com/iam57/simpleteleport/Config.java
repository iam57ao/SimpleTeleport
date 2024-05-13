package com.iam57.simpleteleport;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author iam57
 * @since 2024-05-13 20:24
 */
public class Config {
    private static final Map<String, Object> cache = new HashMap<>();

    public static FileConfiguration getConfig() {
        return SimpleTeleport.getPlugin(SimpleTeleport.class).getConfig();
    }

    public static Object get(String path) {
        return cache.computeIfAbsent(path, (k) -> getConfig().get(path));
    }

    public static Long getRequestCooldownTime() {
        return (Long) get("configs.request-cooldown-time");
    }

    public static Long getRequestExpirationTime() {
        return (Long) get("configs.request-expiration-time");
    }
}
