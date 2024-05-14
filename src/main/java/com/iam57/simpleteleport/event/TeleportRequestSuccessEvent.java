package com.iam57.simpleteleport.event;

import com.iam57.simpleteleport.entity.TeleportRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author iam57
 * @since 2024-05-14 09:34
 */
@Getter
@AllArgsConstructor
public class TeleportRequestSuccessEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private TeleportRequest teleportRequest;

    @NonNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
