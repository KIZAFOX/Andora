package fr.kizafox.andora.managers.commands;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.tools.abstracts.AbstractCommand;
import fr.kizafox.andora.tools.game.heroes.Heroes;
import fr.kizafox.andora.tools.gui.WeaponsGui;
import fr.kizafox.andora.tools.starter.StarterPlugin;
import fr.kizafox.andora.tools.starter.StatusPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandAndora extends AbstractCommand implements TabCompleter {

    public CommandAndora(Andora instance) {
        super(instance);
    }

    @Override
    public boolean doAction(Player player, Command command, String label, String[] args) {

        if(args.length == 0){
            player.sendMessage(Andora.PREFIX + ChatColor.RED + "/andora pack - Pour être plus immergé dans le jeu !");
            return true;
        }

        if(args[0].equalsIgnoreCase("admin")) {
            if(args[1].equalsIgnoreCase("code")){
                if(!player.getName().equalsIgnoreCase("KIZAFOX")) return true;

                if (StatusPlugin.getStatus().equals(StatusPlugin.IN_USE)) {
                    player.sendMessage(Andora.PREFIX + ChatColor.GREEN + ChatColor.ITALIC + "Le serveur est en cours de fonctionnement...");
                    return true;
                }
                player.sendMessage(Andora.PREFIX + ChatColor.GREEN + "Le code secret est " + ChatColor.BOLD + StarterPlugin.RANDOM_ID + ChatColor.RESET + ChatColor.GREEN + ".");
            }else if(args[1].equalsIgnoreCase("heroes")){
                Heroes heroes = Heroes.valueOf(args[2]);

                if(args[3].equalsIgnoreCase("update")){
                    if(args[4].equalsIgnoreCase("health")){
                        double health = Double.parseDouble(args[5]);

                        this.instance.getHeroes().setHealth(heroes, health);
                        player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Mise à jour de " + heroes.getName() + " -> Nouveau HP = " + health);
                    }else if(args[4].equalsIgnoreCase("prefix")){
                        String prefix = args[5].replaceAll("&", "§");

                        this.instance.getHeroes().setPrefix(heroes,  prefix);
                        player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Mise à jour de " + heroes.getName() + " -> Nouveau PREFIX = " + prefix);
                    }
                }
            }else if(args[1].equalsIgnoreCase("weapons")){
                new WeaponsGui(this.instance, player).display();
            }
        }else if(args[0].equalsIgnoreCase("pack")){
            Bukkit.getScheduler().scheduleSyncDelayedTask(this.instance, () -> player.setTexturePack("http://hardback.fr/texturepack/pack.zip"), 20L);
            player.sendMessage(ChatColor.LIGHT_PURPLE + "Chargement du texture pack...");
        }else {
            player.sendMessage(Andora.PREFIX + ChatColor.RED + "/andora pack - Pour être plus immergé dans le jeu !");
        }

        return true;
    }

    private final List<String> argument0 = new ArrayList<>();
    private final List<String> argument1 = new ArrayList<>();
    private final List<String> argument2 = new ArrayList<>();
    private final List<String> argument3 = new ArrayList<>();
    private final List<String> argument4 = new ArrayList<>();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.isOp()) return null;

        if(argument0.isEmpty()){
            argument0.add("admin");
            argument0.add("pack");
        }

        if(argument1.isEmpty()){
            argument1.add("heroes");
            argument1.add("weapons");
        }

        if(argument2.isEmpty()){
            for(Heroes heroes : Heroes.values()){
                argument2.add(heroes.getName());
            }
        }

        if(argument3.isEmpty()){
            argument3.add("update");
        }

        if(argument4.isEmpty()){
            argument4.add("health");
            argument4.add("prefix");
        }

        List<String> result = new ArrayList<>();

        if(args.length == 1){
            for(String str : argument0){
                if(str.toLowerCase().startsWith(args[0].toLowerCase())){
                    result.add(str);
                }
            }
            return result;
        }else if(args.length == 2) {
            for (String str : argument1) {
                if (str.toLowerCase().startsWith(args[1].toLowerCase())) {
                    result.add(str);
                }
            }
            return result;
        }else if(args.length == 3) {
            for (String str : argument2) {
                if (str.toLowerCase().startsWith(args[2].toLowerCase())) {
                    result.add(str);
                }
            }
            return result;
        }else if(args.length == 4) {
            for (String str : argument3) {
                if (str.toLowerCase().startsWith(args[3].toLowerCase())) {
                    result.add(str);
                }
            }
            return result;
        }
        else if(args.length == 5) {
            for (String str : argument4) {
                if (str.toLowerCase().startsWith(args[4].toLowerCase())) {
                    result.add(str);
                }
            }
            return result;
        }

        return null;
    }
}
