package eventhandlers;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import com.glicio.ChatUser;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import utils.FormattedMsg;
import utils.FormattedText;

public class ChatHandler implements Listener {

    public LuckPerms getLpApi() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        LuckPerms api = provider.getProvider();
        return api;
    }

    private FileConfiguration config;
    private HashMap<UUID, ChatUser> chatusers;
    private LuckPerms api = getLpApi();
    private Collection<? extends Player> players;
    private Server server;
    double local_chat_radius;

    public ChatHandler(FileConfiguration config, HashMap<UUID, ChatUser> chatusers, Server server) {
        this.config = config;
        this.chatusers = chatusers;
        this.players = server.getOnlinePlayers();
        this.server = server;
        this.local_chat_radius = config.getDouble("local_chat_radius");

    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {

        Player ePlayer = event.getPlayer();
        User user = api.getPlayerAdapter(Player.class).getUser(event.getPlayer());
        ChatUser chatuser = chatusers.get(event.getPlayer().getUniqueId());

        FormattedMsg msg = new FormattedMsg(event.getMessage(), user);

        FormattedText prefix = new FormattedText(user.getCachedData().getMetaData().getPrefix());
        // Formata a msg do chat global, AsyncPlayerChatEvent cuida do resto
        event.setMessage(msg.getFormattedMsg());
        // define o formato do chat
        event.setFormat(String.format("\u00A72\u00A7l[G]\u00A7r %s %s: %s",
                prefix.getFormattedTexString(),
                ePlayer.getName(),
                event.getMessage()));
        // lidando com o chat local
        if (chatuser.getCurrentChat() == "local") {

            event.setCancelled(true);
            int count = 0;

            for (Player player : players) {

                String menssage = String.format("\u00A7e\u00A7l[L]\u00A7r %s %s:\u00A7e %s",
                        prefix.getFormattedTexString(),
                        ePlayer.getName(),
                        event.getMessage());

                server.getLogger().info(menssage);

                if (player.getLocation().distanceSquared(ePlayer.getLocation()) <= local_chat_radius
                        * local_chat_radius) {
                    count++;
                    player.sendMessage(menssage);

                } else if (player.getLocation().distanceSquared(ePlayer.getLocation()) <= (local_chat_radius * 2)
                        * (local_chat_radius * 2)) {
                    count++;

                    player.sendMessage(String.format("\u00A7e\u00A7l[L]\u00A7r %s %s:\u00A78\u00A7o %s",
                            prefix.getFormattedTexString(),
                            ePlayer.getName(),
                            event.getMessage()));

                }
                if (count == 0) {
                    ePlayer.sendMessage(
                            ChatColor.YELLOW + "Nenhum jogador por perto!\nUse /g para ir para o chat global");
                }

                // FUTURO COMANDO /perto
                // ePlayer.sendMessage(String.format("Jogador %s está à %sm²", player.getName(),
                // player.getLocation().distanceSquared(ePlayer.getLocation())));

            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        ChatUser user = new ChatUser(e.getPlayer(), "local");
        // Registrando o ChatUser
        System.out.println(e.getPlayer().getUniqueId());
        chatusers.put(e.getPlayer().getUniqueId(), user);
        String msg = config.getString("join_msg");
        e.setJoinMessage(String.format(msg, e.getPlayer().getName(), config.getString("server_name")));
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        chatusers.remove(e.getPlayer().getUniqueId());
    }
}
