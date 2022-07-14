package fr.kizafox.andora.tools.starter;

import fr.kizafox.andora.Andora;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class StarterTask extends BukkitRunnable {

    protected final Andora instance;
    private final Player player;

    public static Integer timer = 30;

    public StarterTask(Andora instance, Player player) {
        this.instance = instance;
        this.player = player;
    }

    @Override
    public void run() {
        timer--;

        if(StatusPlugin.getStatus().equals(StatusPlugin.IN_USE)){
            this.cancel();
        }

        if(timer == 20 || timer == 15 || timer == 10 || timer == 5){
            player.sendTitle(Andora.PREFIX, ChatColor.GREEN + "Encore " + ChatColor.BOLD + timer + ChatColor.RESET + ChatColor.GREEN + "s pour entrer le code secret !", 20, 20, 20);
        }

        if(timer == 0){
            player.kickPlayer(Andora.PREFIX + ChatColor.RED + "\n\nVous avez dépassé le temps pour entrer le code secret !");
            Bukkit.getServer().shutdown();
        }
    }
}
