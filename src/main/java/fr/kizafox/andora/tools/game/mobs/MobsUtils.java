package fr.kizafox.andora.tools.game.mobs;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class MobsUtils {

    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static ItemStack createItem(Material type, int amount, boolean glow, boolean unbreakable, boolean hideUnb, String name, String... lines) {
        ItemStack item = new ItemStack(type, amount);
        ItemMeta meta = item.getItemMeta();
        if (glow) {
            Objects.requireNonNull(meta).addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
        }
        if (unbreakable) Objects.requireNonNull(meta).setUnbreakable(true);
        if (hideUnb) Objects.requireNonNull(meta).addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        if (name != null) Objects.requireNonNull(meta).setDisplayName(name);
        if (lines != null) {
            List<String> lore = new ArrayList<>(Arrays.asList(lines));
            Objects.requireNonNull(meta).setLore(lore);
        }
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack enchantItem(ItemStack item, Enchantment enchant, int level) {
        item.addUnsafeEnchantment(enchant, level);
        return item;
    }

    public static ItemStack[] makeArmorSet(ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots) {
        ItemStack[] armor = new ItemStack[4];
        armor[3] = helmet;
        armor[2] = chestplate;
        armor[1] = leggings;
        armor[0] = boots;
        return armor;
    }

}
