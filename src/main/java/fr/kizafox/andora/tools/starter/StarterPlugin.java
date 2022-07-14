package fr.kizafox.andora.tools.starter;

import fr.kizafox.andora.Andora;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Random;
import java.util.logging.Level;

public class StarterPlugin implements Listener {

    protected final Andora instance;
    public static int RANDOM_ID;

    public StarterPlugin(Andora instance){
        this.instance = instance;

        Bukkit.getPluginManager().registerEvents(this, this.instance);
    }

    public void check() {
        StatusPlugin.setStatus(StatusPlugin.LOAD);

        this.instance.getLogger().log(Level.INFO, "Secret code in creation..");
        RANDOM_ID = new Random().nextInt(9999);
        this.instance.getDiscordManager().execute(">> '" + RANDOM_ID + "' <<");
    }

    @EventHandler
    public void onLogin(PlayerJoinEvent event){
        if(StatusPlugin.getStatus().equals(StatusPlugin.LOAD)){
            Player player = event.getPlayer();
            if(player.isOp()){
                new StarterTask(this.instance, player).runTaskTimer(this.instance, StarterTask.timer, 20L);
                player.sendMessage(Andora.PREFIX + ChatColor.RED + "Vous avez " + ChatColor.BOLD + StarterTask.timer + ChatColor.RESET + ChatColor.RED + " secondes pour entrer le code secret !");
            }else{
                player.kickPlayer(Andora.PREFIX + ChatColor.RED + "\n\nVous devez avoir les permissions opérateurs pour accéder au serveur !");
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        if(StatusPlugin.getStatus().equals(StatusPlugin.LOAD)){
            Player player = event.getPlayer();
            if(player.isOp()){
                if(event.getMessage().equalsIgnoreCase("" + RANDOM_ID)){
                    event.setCancelled(true);
                    StatusPlugin.setStatus(StatusPlugin.IN_USE);

                    player.sendTitle(Andora.PREFIX, ChatColor.GREEN + "Vous avez maintenant l'accès complet !");
                }else{
                    event.setCancelled(true);
                    player.sendMessage(Andora.PREFIX + ChatColor.DARK_RED + ChatColor.BOLD + "Mauvais code !");
                }
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        if(StatusPlugin.getStatus().equals(StatusPlugin.LOAD)) event.setCancelled(true);
    }
}
