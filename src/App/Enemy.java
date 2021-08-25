package App;

import App.Tools;

/**
 * Created by Aleksandr Gladkov [Anticisco]
 * Date: 05.08.2021
 */

public class Enemy {

    private int powerAttack;

    private int damageMin = 30;
    private int damageMax = 50;

    public Enemy() {
        this.powerAttack = Tools.randomIntRange(damageMin, damageMax);
    }

    public int getPowerAttack() {
        return powerAttack;
    }

}
