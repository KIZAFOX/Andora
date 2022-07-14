package fr.kizafox.andora.tools.abstracts;

import fr.kizafox.andora.Andora;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class AbstractCommand implements CommandExecutor {

    protected final Andora instance;

    public AbstractCommand(Andora instance) {
        this.instance = instance;
    }

    /**
     * Executes the given command, returning its success.
     * <br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(Andora.PREFIX + ChatColor.RED + "Vous devez être un joueur en jeu pour pouvoir utiliser cette commande.");
            return true;
        }

        if(!this.isOp((Player) sender)){
            sender.sendMessage(Andora.PREFIX + ChatColor.RED + "Vous n'avez pas la permission d'utiliser cette commande.");
            return true;
        }

        return this.doAction((Player) sender, command, label, args);
    }

    public abstract boolean doAction(Player player, Command command, String label, String[] args);

    public boolean isOp(Player player) {
        return player.isOp();
    }
}
