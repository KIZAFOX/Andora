package fr.kizafox.andora.tools.game.heroes;

import fr.kizafox.andora.Andora;
import fr.kizafox.andora.tools.game.heroes.archer.Archer;

public class HeroesManager {

    protected final Andora instance;
    private final Archer archer;

    public HeroesManager(Andora instance){
        this.instance = instance;

        this.archer = new Archer(this.instance);
    }

    public Archer getArcher() {
        return archer;
    }
}
