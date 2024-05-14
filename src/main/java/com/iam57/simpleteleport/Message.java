package com.iam57.simpleteleport;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author iam57
 * @since 2024-05-13 09:02
 */
public class Message {
    private static final FileConfiguration config;
    private static final Map<String, String> messageCache;
    private static final String prefix;

    static {
        config = Config.getConfig();
        prefix = (String) config.get("configs.prefix");
        messageCache = new HashMap<>();
    }

    public static String get(String key) {
        String message = messageCache.computeIfAbsent(key, (k) -> config.getString(String.format("messages.%s", key)));
        return prefix + " " + message;
    }

    public static String getAndReplace(String key, String placeholder, String... args) {
        String message = get(key);
        StringBuilder sb = new StringBuilder(message);
        int startIndex = 0;
        int argIndex = 0;
        while ((startIndex = sb.indexOf(placeholder, startIndex)) != -1 && argIndex < args.length) {
            sb.replace(startIndex, startIndex + placeholder.length(), args[argIndex]);
            startIndex++;
            argIndex++;
        }
        return sb.toString();
    }

    public static String getAndReplacePlayer(String key, String... args) {
        return getAndReplace(key, "%player%", args);
    }
}
