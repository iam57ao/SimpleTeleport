package com.iam57.simpleteleport.util;

import com.iam57.simpleteleport.Config;

/**
 * @author iam57
 * @since 2024-05-13 09:02
 */
public class MessageUtil {

    private static String getPrefix() {
        return Config.getConfig().getString("messages.prefix");
    }

    public static String get(String key) {
        String message = Config.getConfig().getString(String.format("messages.%s", key));
        return getPrefix() + " " + message;
    }

    public static String getAndReplace(String key, String placeholder, String... args) {
        String message = get(key);
        for (String arg : args) {
            message = message.replace(placeholder, arg);
        }
        return message;
    }

    public static String getAndReplacePlayer(String key, String... args) {
        return getAndReplace(key, "%player%", args);
    }
}
