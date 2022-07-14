package fr.kizafox.andora.tools.game.heroes;

import fr.kizafox.andora.tools.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import xyz.xenondevs.particle.ParticleEffect;

public enum Weapons {

    DEFAULT_BOW(new ItemBuilder(Material.BOW).setName(ChatColor.GRAY + "" + ChatColor.BOLD + "Arc Basique").addEnchant(Enchantment.ARROW_INFINITE, 1).toItemStack(), ParticleEffect.SWEEP_ATTACK, "DEFAULT_BOW"),
    LAVA_BOW(new ItemBuilder(Material.BOW).setName(ChatColor.RED + "" + ChatColor.BOLD + "Arc de lave").addEnchant(Enchantment.ARROW_INFINITE, 1).addEnchant(Enchantment.ARROW_FIRE, 1).toItemStack(), ParticleEffect.DRIP_LAVA, "LAVA_BOW");

    private final ItemStack itemStack;
    private final ParticleEffect particleEffect;
    private final String name;

    Weapons(ItemStack itemStack, ParticleEffect particleEffect, String name) {
        this.itemStack = itemStack;
        this.particleEffect = particleEffect;
        this.name = name;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public ParticleEffect getParticleEffect() {
        return particleEffect;
    }

    public String getName() {
        return name;
    }
}
