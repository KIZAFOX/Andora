package fr.kizafox.andora.tools.game.mobs.components;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.Random;

public class LootItem {

    private final ItemStack itemStack;
    private int min = 1, max = 1;
    private final double dropRate;
    private final Random random = new Random();

    public LootItem(ItemStack itemStack, double dropRate) {
        this.itemStack = itemStack;
        this.dropRate = dropRate;
    }

    public LootItem(ItemStack itemStack, int min, int max, double dropRate) {
        this.itemStack = itemStack;
        this.min = min;
        this.max = max;
        this.dropRate = dropRate;
    }

    public void drop(Location location){
        if(Math.random() * 101 > this.dropRate) return;

        int amount = this.random.nextInt(max - min + 1) + min;
        if(amount == 0) return;

        ItemStack itemStack = this.itemStack.clone();
        itemStack.setAmount(amount);
        Objects.requireNonNull(location.getWorld()).dropItemNaturally(location, itemStack);
    }
}
