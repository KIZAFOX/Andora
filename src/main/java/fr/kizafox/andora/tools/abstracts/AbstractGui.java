package fr.kizafox.andora.tools.abstracts;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;

public abstract class AbstractGui{

    protected final Plugin plugin;
    protected final Player player;

    public AbstractGui(Plugin plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    /**
     * This method call to display/open the inventory
     */
    public abstract void display();

    /*
     * This method is call for interaction in the menu
     */
    public abstract void onInventoryClick(InventoryClickEvent event);

    public Plugin getPlugin() {
        return plugin;
    }

    public Player getPlayer() {
        return player;
    }
}