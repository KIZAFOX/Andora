package fr.kizafox.andora;

import fr.kizafox.andora.managers.Managers;
import fr.kizafox.andora.tools.database.Account;
import fr.kizafox.andora.tools.database.MySQL;
import fr.kizafox.andora.tools.database.heroes.Heroes;
import fr.kizafox.andora.tools.game.GameManager;
import fr.kizafox.andora.tools.game.heroes.HeroesManager;
import fr.kizafox.andora.tools.game.heroes.archer.Archer;
import fr.kizafox.andora.tools.game.mobs.Mobs;
import fr.kizafox.andora.tools.scoreboard.FastBoard;
import fr.kizafox.andora.tools.slots.SlotsManager;
import fr.kizafox.andora.tools.webhook.DiscordManager;
import org.apache.commons.dbcp2.BasicDataSource;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.logging.Level;

public final class Andora extends JavaPlugin {

    private static Andora instance;

    private DiscordManager discordManager;
    private SlotsManager slotsManager;
    private World world;

    public GameManager gameManager;

    private MySQL mysql;
    private Heroes heroes;

    private List<Account> accounts;

    private HeroesManager heroesManager;

    public static final Map<UUID, FastBoard> BOARD = new HashMap<>();
    public static final String PREFIX = ChatColor.GRAY + "[" + ChatColor.GOLD + ChatColor.BOLD + "Andora" + ChatColor.RESET + ChatColor.GRAY + "] ";

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();
        instance = this;

        this.discordManager = new DiscordManager(this);

        //new StarterPlugin(this).check();

        this.slotsManager = new SlotsManager(this);
        try {
            this.slotsManager.changeSlots(1);
            this.getLogger().log(Level.INFO, "Slots updated by " + this.getServer().getMaxPlayers() + " !");
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
        this.world = this.getServer().getWorlds().get(0);
        this.world.setGameRuleValue("randomTickSpeed", "0");
        this.world.setGameRuleValue("doDaylightCycle", "false");
        this.world.setGameRuleValue("announceAdvancements", "false");
        this.world.setWeatherDuration(0);
        this.world.setTime(6000L);

        this.init();

        this.accounts = new ArrayList<>();

        this.heroesManager = new HeroesManager(this);

        this.getServer().getScheduler().runTaskTimer(this, () -> {
            for (FastBoard board : BOARD.values()) {
                updateBoard(board);
            }
        }, 0, 20);

        new Managers(this);

        new Mobs(this);

        this.getServer().getConsoleSender().sendMessage(PREFIX + ChatColor.YELLOW + "Plugin loaded in " + ChatColor.GOLD + ChatColor.BOLD + (System.currentTimeMillis() - start) + ChatColor.RESET + ChatColor.YELLOW + "ms !");
    }

    @Override
    public void onDisable() {
        this.slotsManager.updateServerProperties();
        this.getServer().getConsoleSender().sendMessage(PREFIX + ChatColor.DARK_RED + "Plugin disabled !");
    }

    private void init(){
        BasicDataSource connectionPool = new BasicDataSource();
        connectionPool.setDriverClassName("com.mysql.jdbc.Driver");
        connectionPool.setUsername("admin");
        connectionPool.setPassword("admin");
        connectionPool.setUrl("jdbc:mysql://localhost:3306/andora?autoReconnect=true");
        connectionPool.setInitialSize(1);
        connectionPool.setMaxTotal(10);
        this.mysql = new MySQL(connectionPool);
        this.heroes = new Heroes();
        this.mysql.createTables();
        this.heroes.loadHeroes();
        this.getLogger().log(Level.INFO, "Connected to database !");
    }

    private void updateBoard(FastBoard board) {
        Account account = Account.getAccount(board.getPlayer());
        board.updateLines(
                "",
                ChatColor.WHITE + "Compte: " + ChatColor.YELLOW + ChatColor.BOLD + board.getPlayer().getName(),
                ChatColor.WHITE + "Classe: " + ChatColor.YELLOW + ChatColor.BOLD + this.heroes.getPrefix(account.getHeroe()),
                "",
                ChatColor.GOLD + "" + ChatColor.BOLD + "PLAY.ANDORA.FR"
        );
    }

    public static Andora get() {
        return instance;
    }

    public DiscordManager getDiscordManager() {
        return discordManager;
    }

    public SlotsManager getSlotsManager() {
        return slotsManager;
    }

    public World getWorld() {
        return world;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public MySQL getMySQL() {
        return mysql;
    }

    public Heroes getHeroes() {
        return heroes;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public HeroesManager getHeroesManager() {
        return heroesManager;
    }
}
