package com.iam57.simpleteleport.command;

import com.iam57.simpleteleport.service.TeleportManager;
import org.bukkit.entity.Player;

/**
 * @author iam57
 * @since 2024-05-14 19:11
 */
public class TpAcceptCommand extends BasePlayerCommand {
    private static final TeleportManager teleportManager = TeleportManager.getInstance();

    @Override
    protected boolean execute(Player player, String[] args) {
        teleportManager.handleTeleportRequest(player, true);
        return true;
    }
}
