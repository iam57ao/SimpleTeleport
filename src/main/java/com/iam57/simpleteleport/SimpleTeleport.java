package com.iam57.simpleteleport;

import com.iam57.simpleteleport.command.TpAcceptCommand;
import com.iam57.simpleteleport.command.TpDenyCommand;
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
        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("tpa")).setExecutor(new TpaCommand());
        Objects.requireNonNull(getCommand("tpahere")).setExecutor(new TpaHereCommand());
        Objects.requireNonNull(getCommand("tpaccept")).setExecutor(new TpAcceptCommand());
        Objects.requireNonNull(getCommand("tpdeny")).setExecutor(new TpDenyCommand());
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new TeleportListener(), this);
    }
}
