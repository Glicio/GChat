package utils;

import org.bukkit.ChatColor;

public class ChatMsg {
    String chatLogo;
    String prefix;
    String senderName;
    ChatColor msgColor;
    String menssage;

    public ChatMsg(String chatName, String prefix, String senderName, ChatColor msgColor, String menssage) {
        this.chatLogo = getChatLogo(chatName);
        this.prefix = prefix;
        this.senderName = senderName;
        this.msgColor = msgColor;
        this.menssage = menssage;

    }

    public String getMsg() {
        return String.format("[%s] %s %s: %s %s",
                chatLogo,
                prefix,
                senderName,
                msgColor,
                menssage);
    }

    String getChatLogo(String chatName) {
        switch (chatName) {
            case "local":
                return "L";
            case "global":
                return "G";
            default:
                return "";
        }
    }
}
