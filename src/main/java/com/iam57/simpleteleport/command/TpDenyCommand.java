package com.iam57.simpleteleport.command;

import com.iam57.simpleteleport.service.TeleportManager;
import org.bukkit.entity.Player;

/**
 * @author iam57
 * @since 2024-05-14 19:46
 */
public class TpDenyCommand extends BasePlayerCommand {
    private static final TeleportManager teleportManager = TeleportManager.getInstance();

    @Override
    protected boolean execute(Player player, String[] args) {
        teleportManager.handleTeleportRequest(player, false);
        return true;
    }
}
