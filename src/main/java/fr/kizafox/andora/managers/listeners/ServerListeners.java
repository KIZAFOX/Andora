package fr.kizafox.andora.managers.listeners;

import fr.kizafox.andora.Andora;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListeners implements Listener {

    protected final Andora instance;

    public ServerListeners(Andora instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onServerListPing(ServerListPingEvent event){
        event.setMotd(Andora.PREFIX.replace("[", "").replace("]", "") + ChatColor.GRAY + "» " + ChatColor.RED + ChatColor.BOLD + "Serveur en développement !");
    }
}
