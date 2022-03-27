package commands;

import java.util.HashMap;
import java.util.UUID;

import com.glicio.ChatUser;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class SelectChatCmds implements CommandExecutor {
    HashMap<UUID, ChatUser> chatusers;

    public SelectChatCmds(HashMap<UUID, ChatUser> chatusers) {
        this.chatusers = chatusers;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Esse comando só pode ser usado pro jogadores.");
            return false;
        }
        Player player = (Player) sender;
        ChatUser user = chatusers.get(player.getUniqueId());
        switch (label) {
            case "l":
                user.setCurrentChat("local");
                player.sendMessage(ChatColor.YELLOW + "Você entrou no chat local.");
                return true;
            case "g":
                user.setCurrentChat("global");
                player.sendMessage(ChatColor.GREEN + "Você entrou no chat Global.");
                return true;
            default:
                return false;
        }
    }
}
