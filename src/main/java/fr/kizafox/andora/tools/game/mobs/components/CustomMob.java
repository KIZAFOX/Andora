package fr.kizafox.andora.tools.game.mobs.components;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static fr.kizafox.andora.tools.game.mobs.MobsUtils.*;

public enum CustomMob {

    DESERT_RISEN("&6Desert Risen", 15, 60, EntityType.HUSK, null, null, new LootItem(createItem(Material.ROTTEN_FLESH, 1, false, false, false, "&fPreserved Flesh", "&7A preserved flesh from a rotting corpse", "&7Not sure what you'd want this for, though", "&7", "&9Foodstuff"), 1, 3, 100)),
    SKELETAL_MAGE("&dSkeletal Mage", 20, 15, EntityType.SKELETON, createItem(Material.BONE, 1, true, false, false, null), makeArmorSet(new ItemStack(Material.IRON_HELMET), null, null, null), new LootItem(createItem(Material.BONE, 1, true, false, false, "&dBone Wand", "&7A wand made from skeletal bones"), 30), new LootItem(new ItemStack(Material.BONE), 1, 3, 100)),
    ZOMBIE_SQUIRE("&bZombie Squire", 20, 12, EntityType.ZOMBIE, new ItemStack(Material.IRON_SWORD), makeArmorSet(new ItemStack(Material.CHAINMAIL_HELMET), new ItemStack(Material.CHAINMAIL_CHESTPLATE), new ItemStack(Material.CHAINMAIL_LEGGINGS), new ItemStack(Material.IRON_BOOTS)), new LootItem(new ItemStack(Material.CHAINMAIL_CHESTPLATE), 35), new LootItem(new ItemStack(Material.CHAINMAIL_LEGGINGS), 35), new LootItem(new ItemStack(Material.CHAINMAIL_HELMET), 35), new LootItem(new ItemStack(Material.IRON_BOOTS), 25), new LootItem(new ItemStack(Material.IRON_SWORD), 40)),
    CHARRED_ARCHER("&8Charred Archer", 50, 3, EntityType.WITHER_SKELETON, enchantItem(new ItemStack(Material.BOW), Enchantment.ARROW_KNOCKBACK, 2), null, new LootItem(enchantItem(enchantItem(createItem(Material.BOW, 1, false, false, false, "&cBurnt Bow", "&7This bow is burnt to a crisp but remains intact", "&8due to special enchantments"), Enchantment.ARROW_FIRE, 1), Enchantment.ARROW_KNOCKBACK, 2), 100), new LootItem(new ItemStack(Material.BONE), 1, 5, 100)),;

    private final String name;
    private final double maxHealth, spawnChance;
    private final EntityType type;
    private final ItemStack mainItem;
    private final ItemStack[] armor;
    private final List<LootItem> lootTable;

    CustomMob(String name, double maxHealth, double spawnChance, EntityType type, ItemStack mainItem, ItemStack[] armor, LootItem... lootItems) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.spawnChance = spawnChance;
        this.type = type;
        this.mainItem = mainItem;
        this.armor = armor;
        lootTable = Arrays.asList(lootItems);
    }

    public LivingEntity spawn(Location location) {
        LivingEntity entity = (LivingEntity) Objects.requireNonNull(location.getWorld()).spawnEntity(location, type);
        entity.setCustomNameVisible(true);
        entity.setCustomName(color(name + " &r&c" + (int) maxHealth + "/" + (int) maxHealth + "❤"));
        Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(maxHealth);
        entity.setHealth(maxHealth);
        EntityEquipment inv = entity.getEquipment();
        if (armor != null) Objects.requireNonNull(inv).setArmorContents(armor);
        Objects.requireNonNull(inv).setHelmetDropChance(0f);
        inv.setChestplateDropChance(0f);
        inv.setLeggingsDropChance(0f);
        inv.setBootsDropChance(0f);
        inv.setItemInMainHand(mainItem);
        inv.setItemInMainHandDropChance(0f);
        return entity;
    }

    public void drop(Location location) {
        for (LootItem item : lootTable) {
            item.drop(location);
        }
    }

    public String getName() {
        return name;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public double getSpawnChance() {
        return spawnChance;
    }

}
