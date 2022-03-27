package commands;

import java.util.HashMap;
import java.util.UUID;

import com.glicio.ChatUser;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetChatUsersCommand implements CommandExecutor {
    HashMap<UUID, ChatUser> chatusers;

    public GetChatUsersCommand(HashMap<UUID, ChatUser> chatusers) {
        this.chatusers = chatusers;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            sender.sendMessage("Informações mostradas no console...");
        }
        System.out.println(chatusers);
        return true;
    }

}
