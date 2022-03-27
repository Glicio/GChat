package com.glicio;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import commands.GetChatUsersCommand;
import commands.SelectChatCmds;
import eventhandlers.ChatHandler;

/**
 * Hello world!
 */
public class App extends JavaPlugin {
    public final FileConfiguration config = getConfig();
    public HashMap<UUID, ChatUser> chatusers = new HashMap<UUID, ChatUser>();

    @Override
    public void onEnable() {
        // Setando configurações padrão
        config.addDefault("server_name", "GCraft");
        config.addDefault("join_msg", "Bem vindo, %s ao %s");
        config.addDefault("local_chat_radius", 20);

        config.options().copyDefaults(true);

        saveConfig();
        // fim das configs

        getLogger().info("Carregando Plugin");
        getServer().getPluginManager().registerEvents(new ChatHandler(config, chatusers, this.getServer()), this);

        CommandExecutor cmdselectchat = new SelectChatCmds(chatusers);
        this.getCommand("getchatusers").setExecutor(new GetChatUsersCommand(chatusers));
        this.getCommand("l").setExecutor(cmdselectchat);
        this.getCommand("g").setExecutor(cmdselectchat);
    }

    @Override
    public void onDisable() {
        getLogger().info("Desativando Plugin");
    }
}
