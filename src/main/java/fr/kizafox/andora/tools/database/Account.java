package fr.kizafox.andora.tools.database;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.tools.game.heroes.Heroes;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class Account {

    private final Player player;
    private final String uuid;

    private static final String TABLE = "accounts";

    public Account(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId().toString();
    }

    public Player getPlayer() {
        return player;
    }

    public static Account getAccount(Player player){
        return Andora.get().getAccounts().stream().filter(a -> a.getPlayer() == player).findFirst().get();
    }

    public void setup(){
        Andora.get().getAccounts().add(this);
        Andora.get().getMySQL().query("SELECT * FROM " + TABLE + " WHERE uuid='" + uuid + "'", rs -> {
            try {
                if(!rs.next()){
                    Andora.get().getMySQL().update("INSERT INTO " + TABLE + " (uuid, name, heroe, gold) VALUES ('" + uuid + "', '" + player.getName() + "', '" + Heroes.FARMER.getName() + "', '0')");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        this.player.setDisplayName(Andora.get().getHeroes().getPrefix(this.getHeroe()) + ChatColor.GRAY + " " + player.getName());
        this.player.setPlayerListName(Andora.get().getHeroes().getPrefix(this.getHeroe()) + ChatColor.GRAY + " " + player.getName());

        this.player.setPlayerListHeaderFooter(ChatColor.GOLD + "" + ChatColor.BOLD + "PLAY.ANDORA.FR", ChatColor.RED + "Serveur actuellement en développement !");
    }

    public void delete(){
        Andora.get().getAccounts().remove(this);
    }

    public boolean hasAccount(){
        return (boolean) Andora.get().getMySQL().query("SELECT uuid FROM " + TABLE + " WHERE uuid='" + uuid + "'", rs -> {
            try {
                if(rs.next()) return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        });
    }

    public Heroes getHeroe(){
        return (Heroes) Andora.get().getMySQL().query("SELECT * FROM " + TABLE + " WHERE uuid='" + uuid + "'", rs -> {
            try {
                if(rs.next()){
                    return Heroes.getByName(rs.getString("heroe"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return Heroes.FARMER;
        });
    }
}
