package fr.kizafox.andora.tools.game;

import fr.kizafox.andora.Andora;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GameManager {

    protected final Andora instance;
    private final Player player;

    public GameManager(Andora instance, Player player) {
        this.instance = instance;
        this.player = player;
    }

    public void hide(){
        for (Player players : Bukkit.getOnlinePlayers()) {
            players.hidePlayer(player);
            player.hidePlayer(players);
        }
    }

    public void show(){
        for (Player players : Bukkit.getOnlinePlayers()) {
            players.showPlayer(player);
            player.showPlayer(players);
        }
    }
}
