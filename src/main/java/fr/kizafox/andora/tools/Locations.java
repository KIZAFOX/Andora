package fr.kizafox.andora.tools;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public enum Locations {

    BOAT_SPAWN(new Location(Bukkit.getWorlds().get(0), 1494.452, 65.0, -319.463, 90.5f, 1.4f)),
    MAIN_TOWN(new Location(Bukkit.getWorlds().get(0), 1302.523, 65.0, -327.350, 164.3f, -3.0f)),
    NORTH_VILLAGE(new Location(Bukkit.getWorlds().get(0), 1302.523, 65.0, -327.350, 164.3f, -3.0f)),
    BIG_VILLAGE(new Location(Bukkit.getWorlds().get(0), 1302.523, 65.0, -327.350, 164.3f, -3.0f)),
    EAST_VILLAGE(new Location(Bukkit.getWorlds().get(0), 1302.523, 65.0, -327.350, 164.3f, -3.0f));

    private final Location location;

    Locations(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
