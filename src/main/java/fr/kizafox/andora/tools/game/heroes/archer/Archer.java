package fr.kizafox.andora.tools.game.heroes.archer;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.tools.game.heroes.Weapons;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class Archer implements Listener {

    protected final Andora instance;
    private Weapons bow;

    private final Map<Projectile, BukkitTask> tasks = new HashMap<>();

    public Archer(Andora instance){
        this.instance = instance;

        Bukkit.getPluginManager().registerEvents(this, this.instance);
    }

    public void give(Player player, Weapons bow){
        try {
            this.bow = bow;
            player.getInventory().addItem(bow.getItemStack());
            player.sendMessage(ChatColor.GREEN + "Arme reçus !");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player) {
            tasks.put(event.getEntity(), new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.getOnlinePlayers().forEach(players -> bow.getParticleEffect().display(event.getEntity().getLocation()));
                }
            }.runTaskTimer(Andora.get(), 0L, 1L));
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity().getShooter() instanceof Player) {
            BukkitTask task = tasks.get(event.getEntity());
            if (task != null) {
                task.cancel();
                tasks.remove(event.getEntity());
            }
        }
    }
}
