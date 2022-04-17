package com.andi.DungeonExplorer.actor.Character;

import com.andi.DungeonExplorer.battle.moves.Attack;
import com.andi.DungeonExplorer.actor.Actor;

import java.util.HashMap;

import static com.andi.DungeonExplorer.battle.moves.Attack.damageType.IMPACT;
import static com.andi.DungeonExplorer.battle.moves.Attack.damageType.SLASH;


/**
 * Created by Kenneth on 10/4/2017.
 * Example monster - use this as an example for setting default attacks and resistances
 */

public class Skeleton extends Monster {

    public Skeleton (int[] stats, HashMap<Attack.damageType, Double> resistances, int level, float growthFactor, int expValue, Actor owner){
        super(stats, resistances, level, growthFactor, expValue, owner, "Skeleton");
        populateAttacks();
    }

    /**
     * Skeleton with randomly generated stats based on level and growth factor.
     * Default resistances and stat adjustment.
     */
    public Skeleton(int level, float growthFactor, int expValue, Actor owner){
        super(level, growthFactor, expValue, owner);
        resistances.put(Attack.damageType.PIERCE, .5);
        resistances.put(IMPACT, 2.0);
        maxMP = 0;
        MP = 0;
        statAcc = (int)(statAcc * .9);
        statLuk = (int)(statLuk * .5);
        statEva = (int)(statEva * .8);
        innateAttacks = new HashMap<String, Attack>();
        populateAttacks();
        combatAI = new com.andi.DungeonExplorer.actor.Character.CombatAI(this);
    }

    /**
     * fills the attack map with some default attacks for this monster
     */
    public void populateAttacks(){
        innateAttacks.put("Skeleton Slash", new Attack("Skeleton Slash", 5, 90, 5, SLASH));
        innateAttacks.put("Bone Charge", new Attack("Bone Charge", 8, 65, 5, IMPACT));
        innateAttacks.put("Bone Toss", new Attack("Bone Toss", 4, 70, 5, IMPACT, false, 0, null, 1, 3));
    }

}
