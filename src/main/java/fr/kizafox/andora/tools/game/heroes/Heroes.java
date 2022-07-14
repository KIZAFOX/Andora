package fr.kizafox.andora.tools.game.heroes;

import java.util.Arrays;

public enum Heroes {

    FARMER(1, null, "FARMER", "§f§l[Paysan]", 20.0D / 2),
    ARCHER(2, "Chada", "ARCHER", "§f§l[Archère]", 20.0D * 1.5),
    MAGE(3, "Eara", "MAGE", "§f§l[Magicienne]", 20.0D * 2),
    DWARF(4, "Kram", "DWARF", "§f§l[Nain]", 20.0D * 4),
    WARRIOR(5, "Thorn", "WARRIOR", "§f§l[Guerrier]", 20.0D * 3);

    private final int id;
    private final String heroeName, name, prefix;
    private final Double health;

    public static Heroes getByName(String name){
        return Arrays.stream(values()).filter(r -> r.getName().equalsIgnoreCase(name)).findAny().orElse(Heroes.FARMER);
    }

    Heroes(int id, String heroeName, String name, String prefix, Double health) {
        this.id = id;
        this.heroeName = heroeName;
        this.name = name;
        this.prefix = prefix;
        this.health = health;
    }

    public int getId() {
        return id;
    }

    public String getHeroeName() {
        return heroeName;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public Double getHealth() {
        return health;
    }
}
