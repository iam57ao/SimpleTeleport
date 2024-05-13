package com.iam57.simpleteleport.entity;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.time.Instant;
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
    }

    public boolean inCooldown() {
        return requestCooldownTime == null || Instant.now().isAfter(lastSentRequestTime.plusSeconds(requestCooldownTime));
    }
}
