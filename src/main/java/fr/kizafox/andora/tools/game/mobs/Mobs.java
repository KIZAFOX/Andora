package fr.kizafox.andora.tools.game.mobs;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.tools.game.mobs.components.CustomMob;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.*;

import static fr.kizafox.andora.tools.game.mobs.MobsUtils.*;

public class Mobs implements Listener {

    protected final Andora instance;

    private final World world;
    private final Map<Entity, Integer> indicators;
    private final Map<Entity, CustomMob> entities;
    private final DecimalFormat formatter;

    public Mobs(Andora instance) {
        this.instance = instance;

        this.world = Bukkit.getWorld("world");
        this.indicators = new HashMap<>();
        this.entities = new HashMap<>();
        this.formatter = new DecimalFormat("#.##");

        Bukkit.getPluginManager().registerEvents(this, this.instance);


        spawn(9, 10, 5 * 20);

        new BukkitRunnable(){
            final Set<Entity> stands = indicators.keySet();
            final List<Entity> removal = new ArrayList<>();

            @Override
            public void run() {
                for(Entity stand : stands){
                    int ticksLeft = indicators.get(stand);

                    if(ticksLeft == 0) {
                        stand.remove();
                        removal.add(stand);
                        continue;
                    }

                    ticksLeft--;
                    indicators.put(stand, ticksLeft);
                }
                removal.forEach(stands::remove);
            }
        }.runTaskTimer(this.instance, 0L, 1L);
    }

    public void spawn(int size, int mobCap, int spawnTime){
        CustomMob[] mobTypes = CustomMob.values();
        new BukkitRunnable(){
            final Set<Entity> spawned = entities.keySet();
            final List<Entity> removal = new ArrayList<>();

            @Override
            public void run() {
                for(Entity entity : spawned){
                    if(!entity.isValid() || entity.isDead()) removal.add(entity);
                }
                removal.forEach(spawned::remove);

                int diff = mobCap - entities.size();
                if(diff <= 0) return;

                int spawnCount = (int) (Math.random() * (diff + 1)), count = 0;
                while (count <= spawnCount){
                    count++;

                    int ranX = getRandomWithNeg(size), ranZ = getRandomWithNeg(size);
                    Block block = world.getHighestBlockAt(ranX, ranZ);
                    double xOffset = getRandomOffset(), zOffset = getRandomOffset();
                    Location location = block.getLocation().clone().add(xOffset, 1, zOffset);

                    if(!isSpawnable(location)) continue;

                    double random = Math.random() * 101, previous = 0;
                    CustomMob typeToSpawn = mobTypes[0];

                    for(CustomMob type : mobTypes){
                        previous += type.getSpawnChance();

                        if(random <= previous){
                            typeToSpawn = type;
                            break;
                        }
                    }
                    entities.put(typeToSpawn.spawn(location), typeToSpawn);
                }
            }
        }.runTaskTimer(this.instance, 0L, spawnTime);
    }

    private boolean isSpawnable(Location loc) {
        Block feetBlock = loc.getBlock(), headBlock = loc.clone().add(0, 1, 0).getBlock(), upperBlock = loc.clone().add(0, 2, 0).getBlock();
        return feetBlock.isPassable() && !feetBlock.isLiquid() && headBlock.isPassable() && !headBlock.isLiquid() && upperBlock.isPassable() && !upperBlock.isLiquid();
    }

    private double getRandomOffset() {
        double random = Math.random();
        if (Math.random() > 0.5) random *= -1;
        return random;
    }

    private int getRandomWithNeg(int size) {
        int random = (int) (Math.random() * (size + 1));
        if (Math.random() > 0.5) random *= -1;
        return random;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity rawEntity = event.getEntity();
        if (!entities.containsKey(rawEntity)) return;
        CustomMob mob = entities.get(rawEntity);
        LivingEntity entity = (LivingEntity) rawEntity;
        double damage = event.getFinalDamage(), health = entity.getHealth() + entity.getAbsorptionAmount();
        if (health > damage) {
            // If the entity survived the hit
            health -= damage;
            entity.setCustomName(color(mob.getName() + " &r&c" + (int) health + "/" + (int) mob.getMaxHealth() + "❤"));
        }
        Location loc = entity.getLocation().clone().add(getRandomOffset(), 1, getRandomOffset());
        world.spawn(loc, ArmorStand.class, armorStand -> {
            armorStand.setMarker(true);
            armorStand.setVisible(false);
            armorStand.setGravity(false);
            armorStand.setSmall(true);
            armorStand.setCustomNameVisible(true);
            armorStand.setCustomName(color("&c" + formatter.format(damage)));
            indicators.put(armorStand, 30);
        });
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!entities.containsKey(event.getEntity())) return;
        event.setDroppedExp(0);
        event.getDrops().clear();
        entities.remove(event.getEntity()).drop(event.getEntity().getLocation());
    }

}
