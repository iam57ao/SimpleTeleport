package com.iam57.simpleteleport.event;

import lombok.NonNull;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author iam57
 * @since 2024-05-13 19:29
 */
public class PlayerSendsTeleportRequestEvent extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @NonNull
    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}
