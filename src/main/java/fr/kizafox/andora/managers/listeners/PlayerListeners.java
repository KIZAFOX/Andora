package fr.kizafox.andora.managers.listeners;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.tools.database.Account;
import fr.kizafox.andora.tools.game.tasks.NewPlayerTask;
import fr.kizafox.andora.tools.scoreboard.FastBoard;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;

public class PlayerListeners implements Listener {

    protected final Andora instance;

    public PlayerListeners(Andora instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onLogin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        Account account = new Account(player);
        FastBoard fastBoard = new FastBoard(player);

        if(!account.hasAccount()){
            event.setJoinMessage(ChatColor.LIGHT_PURPLE + "Bienvenue sur le serveur " + player.getName() + " !");
            new NewPlayerTask(this.instance, player).runTaskTimer(this.instance, 20L, 20L);
        }else{
            this.instance.getServer().getScheduler().runTaskAsynchronously(this.instance, account::setup);

            for(PotionEffect potionEffect : player.getActivePotionEffects()) {
                player.removePotionEffect(potionEffect.getType());
            }

            player.setGameMode(GameMode.ADVENTURE);
            player.setFoodLevel(20);
            player.setWalkSpeed(0.20F);
            player.setFlySpeed(0.15F);
            player.setAllowFlight(false);
            player.setMaxHealth(this.instance.getHeroes().getHealth(account.getHeroe()));

            fastBoard.updateTitle(Andora.PREFIX.replace("[", "").replace("]", ""));
            Andora.BOARD.put(player.getUniqueId(), fastBoard);

            event.setJoinMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "+" + ChatColor.GRAY + "] " + player.getName());
            player.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "/andora pack - Pour être plus immergé dans le jeu !");
        }
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent event){
        Player player = event.getPlayer();
        Account.getAccount(player).delete();
        FastBoard fastBoard = Andora.BOARD.remove(player.getUniqueId());

        if(fastBoard != null) fastBoard.delete();

        event.setQuitMessage(ChatColor.GRAY + "[" + ChatColor.RED + "-" + ChatColor.GRAY + "] " + player.getName());
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        Account account = Account.getAccount(player);

        event.setFormat(this.instance.getHeroes().getPrefix(account.getHeroe()) + " " + ChatColor.GRAY + event.getPlayer().getName() + ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + event.getMessage());
    }
}
