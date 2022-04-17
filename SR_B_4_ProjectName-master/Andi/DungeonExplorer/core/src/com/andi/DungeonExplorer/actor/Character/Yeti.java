package com.andi.DungeonExplorer.actor.Character;

import com.andi.DungeonExplorer.actor.Actor;
import com.andi.DungeonExplorer.battle.moves.Attack;
import com.andi.DungeonExplorer.battle.moves.SpecialEffect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static com.andi.DungeonExplorer.battle.moves.Attack.damageType.DARK;
import static com.andi.DungeonExplorer.battle.moves.Attack.damageType.FIRE;
import static com.andi.DungeonExplorer.battle.moves.Attack.damageType.IMPACT;

/**
 * Created by Kenneth on 11/30/2017.
 * Second boss.
 */

public class Yeti extends Monster {
    public Yeti (int[] stats, HashMap<Attack.damageType, Double> resistances, int level, float growthFactor, int expValue, Actor owner){
        super(stats, resistances, level, growthFactor, expValue, owner, "Yeti");
        populateAttacks();
    }

    /**
     * Skeleton with randomly generated stats based on level and growth factor.
     * Default resistances and stat adjustment.
     */
    public Yeti(int level, float growthFactor, int expValue, Actor owner){
        super(level, growthFactor, expValue, owner);
        resistances.put(FIRE, 2.0);
        maxMP = 0;
        MP = 0;
        health+=200;
        statAcc = (int)(statAcc * .9);
        statLuk = (int)(statLuk * .5);
        statEva = (int)(statEva * .8);
        statAtk = (int)(statAtk * 1.4);
        innateAttacks = new HashMap<String, Attack>();
        populateAttacks();
        combatAI = new com.andi.DungeonExplorer.actor.Character.CombatAI(this);
    }

    /**
     * fills the attack map with some default attacks for this monster
     */
    public void populateAttacks(){
        Collection<SpecialEffect> effects = new ArrayList<SpecialEffect>();
        effects.add(new SpecialEffect(SpecialEffect.type.RECOIL, .1, 0, false, false));
        innateAttacks.put("Reckless Punch", new Attack("Reckless Punch", 16, 60, 5, IMPACT, true, 0, effects, 1, 1));
        innateAttacks.put("Snowball", new Attack("Snowball", 8, 65, 5, IMPACT, true, 0, null, .3f, 5));
        innateAttacks.put("Crushing Grasp", new Attack("Crushing Grasp", 12, 70, 5, IMPACT, false, 0, null, 2, 1));
    }
}
