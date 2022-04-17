package com.mygdx.game;

import java.util.HashMap;

/**
 * Created by Kenneth on 10/4/2017.
 * Superclass for enemy creatures the player can combat.
 */

public class Monster extends Character {
    public HashMap<String, Attack> innateAttacks;
    public String texture;
    public CombatAI combatAI;
    /**
     * Constructor for standard use with monsters that don't have innate properties, or to override those properties.
     * Useful when you want to assign specific values to all parameters.
     * @param stats Atk through Spd, an 8 length array
     * @param resistances damage multipliers for receiving certain damage types
     * @param expVal amount of exp to give the player on death
     * @param xpos x position on world
     * @param ypos y position on world
     * @param hp max health
     * @param mp max mp
     */
    public Monster(int[] stats, HashMap<Attack.damageType, Double> resistances, int expVal, int level, int xpos, int ypos, int hp, int mp){
        statAtk = stats[0];
        statDef = stats[1];
        statRes = stats[2];
        statAcc = stats[3];
        statEva = stats[4];
        statLuk = stats[5];
        statMag = stats[6];
        statSpd = stats[7];
        this.resistances = resistances;
        expValue = expVal;
        xPos = xpos;
        yPos = ypos;
        maxHealth = hp;
        health = hp;
        maxMP = mp;
        MP = mp;
        this.level = level;
    }

    /**
     * Constructor for random generation of stats on a monster.
     * For use mainly in subclass constructors.
     * @param level level of the monster, higher means better stats
     * @param growthFactor higher values will result in higher stats
     * @param expVal amount of exp to give the player on death
     * @param xpos x position in world
     * @param ypos y position in world
     */
    public Monster(int level, float growthFactor, int expVal, int xpos, int ypos){
        expValue = expVal;
        xPos = xpos;
        yPos = ypos;
        this.level = 0;
        this.growthFactor = growthFactor;
        for(int i=0; i< level; i++){
            this.levelUp();
        }
    }

}
