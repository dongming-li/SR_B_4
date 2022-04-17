package com.mygdx.game;

import java.util.HashMap;

import static com.mygdx.game.Attack.damageType.IMPACT;
import static com.mygdx.game.Attack.damageType.SLASH;

/**
 * Created by Kenneth on 10/4/2017.
 */

public class Skeleton extends Monster {

    public Skeleton (int[] stats, HashMap<Attack.damageType, Double> resistances, int expVal, int level, int xpos, int ypos, int hp, int mp){
        super(stats, resistances, expVal, level, xpos, ypos, hp, mp);
        populateAttacks();
    }

    /**
     * Skeleton with randomly generated stats based on level and growth factor.
     * Default resistances and stat adjustment.
     */
    public Skeleton(int level, float growthFactor, int expVal, int xpos, int ypos){
        super(level, growthFactor, expVal, xpos, ypos);
        resistances.put(Attack.damageType.PIERCE, .5);
        resistances.put(Attack.damageType.IMPACT, 2.0);
        statSpd = 5;
        maxMP = 0;
        MP = 0;
        statAcc = (int)(statAcc * .9);
        statLuk = (int)(statLuk * .5);
        statEva = (int)(statEva * .8);
        populateAttacks();
    }

    public void populateAttacks(){
        innateAttacks.put("Skeleton Slash", new Attack("Skeleton Slash", 5, 90, 5, SLASH));
        innateAttacks.put("Bone Charge", new Attack("Bone Charge", 8, 65, 5, IMPACT));
    }

}
