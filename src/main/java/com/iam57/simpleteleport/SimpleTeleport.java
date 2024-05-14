package com.iam57.simpleteleport;

import com.iam57.simpleteleport.command.TpaCommand;
import com.iam57.simpleteleport.command.TpaHereCommand;
import com.iam57.simpleteleport.listener.TeleportListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class SimpleTeleport extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Objects.requireNonNull(getCommand("tpa")).setExecutor(new TpaCommand());
        Objects.requireNonNull(getCommand("tpahere")).setExecutor(new TpaHereCommand());
        Bukkit.getPluginManager().registerEvents(new TeleportListener(), this);
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
    }
}
