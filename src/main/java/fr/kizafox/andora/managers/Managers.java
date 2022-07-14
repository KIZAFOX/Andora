package fr.kizafox.andora.managers;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.managers.commands.CommandAndora;
import fr.kizafox.andora.managers.listeners.CancelListeners;
import fr.kizafox.andora.managers.listeners.PlayerListeners;
import fr.kizafox.andora.managers.listeners.ServerListeners;
import org.bukkit.plugin.PluginManager;

import java.util.Objects;

public class Managers {

    public Managers(Andora instance){
        /*
          Listeners
         */
        PluginManager pluginManager = instance.getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerListeners(instance), instance);
        pluginManager.registerEvents(new CancelListeners(instance), instance);
        pluginManager.registerEvents(new ServerListeners(instance), instance);

        /*
          Commands
         */
        Objects.requireNonNull(instance.getCommand("andora")).setExecutor(new CommandAndora(instance));
        Objects.requireNonNull(instance.getCommand("andora")).setTabCompleter(new CommandAndora(instance));
    }
}
