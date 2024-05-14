package com.iam57.simpleteleport.entity;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

/**
 * @author iam57
 * @since 2024-05-13 21:13
 */
@Getter
public class TeleportPlayer {
    private final Player player;
    @Setter
    private Long requestCooldownTime;
    @Setter
    private Instant lastSentRequestTime;
    @Setter
    private List<TeleportRequest> teleportRequests;

    public TeleportPlayer(Player player, Long requestCooldownTime) {
        this.player = player;
        this.requestCooldownTime = requestCooldownTime;
        teleportRequests = new LinkedList<>();
    }

    public boolean inCooldown() {
        return lastSentRequestTime != null && Instant.now().isBefore(lastSentRequestTime.plusSeconds(requestCooldownTime));
    }
}
