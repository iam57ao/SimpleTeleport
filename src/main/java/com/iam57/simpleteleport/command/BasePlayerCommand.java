package com.iam57.simpleteleport.command;

import lombok.NonNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author iam57
 * @since 2024-05-13 20:40
 */
public abstract class BasePlayerCommand implements CommandExecutor {
    private boolean senderIsPlayer(CommandSender sender) {
        return sender instanceof Player;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, @NonNull String[] args) {
        if (!senderIsPlayer(sender)) {
            sender.sendMessage("只有玩家可以使用此命令");
            return true;
        }
        if (!validateArgs(args)) {
            return false;
        }
        return execute((Player) sender, args);
    }

    protected boolean validateArgs(String[] args) {
        return true;
    }

    protected abstract boolean execute(Player player, String[] args);
}
