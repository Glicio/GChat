package com.glicio;

import java.util.UUID;

import org.bukkit.entity.Player;

public class ChatUser {
    private Player player;
    private String name;
    private UUID uuid;
    private String currentChat;

    public ChatUser(Player player, String currentChat) {
        this.player = player;
        this.name = player.getName();
        this.uuid = player.getUniqueId();
        this.currentChat = currentChat;
    }

    public String getName() {
        return this.name;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getCurrentChat() {
        return this.currentChat;
    }

    public void setCurrentChat(String currentChat) {
        this.currentChat = currentChat;
    }

    public Player getPlayer() {
        return player;
    }

}
