package com.andi.DungeonExplorer.actor.Character;

import com.andi.DungeonExplorer.battle.moves.Attack;
import com.andi.DungeonExplorer.actor.Actor;

import java.util.HashMap;

/**
 * Created by Kenneth on 10/4/2017.
 * Superclass for enemy creatures the player can combat.
 */

public class Monster extends Character {
    /**
     * the attacks a monster has available
     */
    public HashMap<String, Attack> innateAttacks;
    /**
     * ai owned by this monster for use in selecting moves
     */
    public CombatAI combatAI;

    /**
     * Constructor for standard use with monsters that don't have innate properties, or to override those properties.
     * Useful when you want to assign specific values to all parameters.
     */
    public Monster(int[] stats, HashMap<Attack.damageType, Double> resistances, int level, float growthFactor, int expValue, Actor owner, String name){
        super(stats, level, growthFactor, expValue, owner, name);
        this.resistances = resistances;
        innateAttacks = new HashMap<String, Attack>();
        combatAI = new CombatAI(this);
        this.owner = owner;
    }

    /**
     * Constructor for random generation of stats on a monster.
     * For use mainly in subclass constructors.
     */
    public Monster(int level, float growthFactor, int expValue, Actor owner){
        this.growthFactor = growthFactor;
        this.expValue = expValue;
        resistances = new HashMap<Attack.damageType, Double>();
        this.owner = owner;
        this.level = 0;
        this.growthFactor = growthFactor;
        innateAttacks = new HashMap<String, Attack>();
        combatAI = new CombatAI(this);
        for(int i=0; i< level; i++){
            this.levelUp();
        }
    }

    /**
     * Represents the data in this class as a string
     */
    public String toString(){
        String output = "";
        output += statAtk+":"+statDef+":"+statRes+":"+statAcc+":"+statEva+":"+statLuk+":"+statMag+":"+statSpd+":"+maxMP+":"+maxHealth+":";
        output += resistances.toString()+":";
        output += level+":"+expValue+":"+growthFactor+":";
        output += owner+":";
        output += name + ":";
        output += innateAttacks.toString();
        return output;
    }

}
