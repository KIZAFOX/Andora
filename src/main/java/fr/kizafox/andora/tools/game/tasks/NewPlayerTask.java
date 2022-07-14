package fr.kizafox.andora.tools.game.tasks;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.tools.Locations;
import fr.kizafox.andora.tools.database.Account;
import fr.kizafox.andora.tools.game.GameManager;
import fr.kizafox.andora.tools.scoreboard.FastBoard;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class NewPlayerTask extends BukkitRunnable {

    protected final Andora instance;
    private final Player player;
    private final Account account;
    private final FastBoard fastBoard;

    public static int TIMER = 30;

    public NewPlayerTask(Andora instance, Player player) {
        this.instance = instance;
        this.player = player;
        this.account = new Account(player);
        this.fastBoard = new FastBoard(player);

        this.instance.gameManager = new GameManager(this.instance, this.player);
        this.instance.getGameManager().hide();

        this.player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30 * 20, 9999));
    }

    @Override
    public void run() {
        this.player.teleport(Locations.BOAT_SPAWN.getLocation());
        switch (TIMER){
            case 30:
                this.player.sendTitle(Andora.PREFIX.replace("[", "").replace("]", ""), ChatColor.GREEN + "Les origines d'Andora", 20, 20, 20);
                break;
            case 29:
                this.player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Une poignée d’esclaves échappa à de redoutables tyrans et s’enfuit en quête d’un pays libre.");
                this.player.sendMessage(" ");
                break;
            case 25:
                this.player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Ces courageux fugitifs durent alors franchir les Montagnes Grises où un terrible Dragon manqua de les réduire en cendres.");
                this.player.sendMessage(" ");
                break;
            case 20:
                this.player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Mais leur chef Brandur l’affronta avec une telle volonté, une telle détermination, que le Dragon, pourtant immortel, commença à craindre pour sa vie.");
                this.player.sendMessage(" ");
                break;
            case 15:
                this.player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Il permit alors à Brandur et à ses amis de passer, mais depuis ce jour, il envoyait de terribles Créatures pour se venger et tuer Brandur.");
                this.player.sendMessage(" ");
                break;
            case 10:
                this.player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Les Légendes d’Andor commencent environ quatre-vingts ans après ces événements. Entre temps, Brandur est devenu Roi de la contrée et le pays a été baptisé Andor…");
                this.player.sendMessage(" ");
                break;
            case 0:
                this.instance.getServer().getScheduler().runTaskAsynchronously(this.instance, this.account::setup);

                player.setGameMode(GameMode.ADVENTURE);
                player.setFoodLevel(20);
                player.setWalkSpeed(0.20F);
                player.setFlySpeed(0.15F);
                player.setAllowFlight(false);
                player.setExp(0);
                player.setLevel(0);
                player.setMaxHealth(this.instance.getHeroes().getHealth(this.account.getHeroe()));
                player.setHealth(player.getMaxHealth());

                this.fastBoard.updateTitle(Andora.PREFIX.replace("[", "").replace("]", ""));
                Andora.BOARD.put(player.getUniqueId(), fastBoard);

                this.player.teleport(Locations.MAIN_TOWN.getLocation());
                this.player.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "/angora pack - Pour être plus immergé dans le jeu !");
                this.player.sendMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + "Le début de votre aventure commence ici...");

                this.instance.getGameManager().show();

                this.cancel();
                break;
            default:break;
        }

        if(TIMER == 5 || TIMER == 4 || TIMER == 3 || TIMER == 2 || TIMER == 1){
            this.player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Début de votre aventure dans " + TIMER + "s !");
        }

        TIMER--;
    }
}
