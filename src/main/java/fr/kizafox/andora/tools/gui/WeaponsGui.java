package fr.kizafox.andora.tools.gui;

import com.sun.org.apache.xpath.internal.operations.And;
import fr.kizafox.andora.Andora;
import fr.kizafox.andora.tools.abstracts.AbstractGui;
import fr.kizafox.andora.tools.game.heroes.Weapons;
import fr.kizafox.andora.tools.game.heroes.archer.Archer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class WeaponsGui extends AbstractGui implements Listener {

    public WeaponsGui(Plugin plugin, Player player) {
        super(plugin, player);

        Bukkit.getPluginManager().registerEvents(this, this.getPlugin());
    }

    @Override
    public void display() {
        Inventory inventory = Bukkit.createInventory(null, 9, "Weapons");

        for(Weapons weapons : Weapons.values()){
            inventory.addItem(weapons.getItemStack());
        }

        this.getPlugin().getServer().getScheduler().runTask(this.getPlugin(), () -> this.getPlayer().openInventory(inventory));
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        final ItemStack itemStack = event.getCurrentItem();

        if(itemStack == null || itemStack.getItemMeta() == null) return;

        if(event.getView().getTitle().equalsIgnoreCase("Weapons")){
            event.setCancelled(true);

            if(itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(Objects.requireNonNull(Weapons.DEFAULT_BOW.getItemStack().getItemMeta()).getDisplayName())){
                player.closeInventory();
                Andora.get().getHeroesManager().getArcher().give(player, Weapons.DEFAULT_BOW);
            }else if(itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(Objects.requireNonNull(Weapons.LAVA_BOW.getItemStack().getItemMeta()).getDisplayName())){
                player.closeInventory();
                Andora.get().getHeroesManager().getArcher().give(player, Weapons.LAVA_BOW);
            }
        }
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event){
        this.onInventoryClick(event);
    }
}
