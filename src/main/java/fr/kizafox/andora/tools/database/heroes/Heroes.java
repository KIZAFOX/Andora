package fr.kizafox.andora.tools.database.heroes;

import fr.kizafox.andora.Andora;

import java.sql.SQLException;

public class Heroes {

    private static final String TABLE = "heroes";

    public void loadHeroes(){
        for(fr.kizafox.andora.tools.game.heroes.Heroes heroes : fr.kizafox.andora.tools.game.heroes.Heroes.values()){
            Andora.get().getMySQL().query("SELECT * FROM " + TABLE + " WHERE `#`='" + heroes.getId() + "'", rs -> {
                try {
                    if(!rs.next()){
                        Andora.get().getMySQL().update("INSERT INTO " + TABLE + " (heroeName, name, prefix, health) VALUES ('" + heroes.getHeroeName() + "', '" + heroes.getName() + "', '" + heroes.getPrefix() + "', '" + heroes.getHealth() + "')");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public String getPrefix(fr.kizafox.andora.tools.game.heroes.Heroes heroes){
        return (String) Andora.get().getMySQL().query("SELECT * FROM " + TABLE + " WHERE `#`='" + heroes.getId() + "'", rs -> {
            try {
                if(rs.next()){
                    return rs.getString("prefix");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    public void setPrefix(fr.kizafox.andora.tools.game.heroes.Heroes heroes, String prefix){
        Andora.get().getMySQL().update("UPDATE " + TABLE + " SET prefix='" + prefix + "' WHERE `#`='" + heroes.getId() + "'");
    }

    public Double getHealth(fr.kizafox.andora.tools.game.heroes.Heroes heroes){
        return (Double) Andora.get().getMySQL().query("SELECT * FROM " + TABLE + " WHERE `#`='" + heroes.getId() + "'", rs -> {
            try {
                if(rs.next()){
                    return rs.getDouble("health");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return 0.0D;
        });
    }

    public void setHealth(fr.kizafox.andora.tools.game.heroes.Heroes heroes, Double health){
        Andora.get().getMySQL().update("UPDATE " + TABLE + " SET health='" + health + "' WHERE `#`='" + heroes.getId() + "'");
    }
}
