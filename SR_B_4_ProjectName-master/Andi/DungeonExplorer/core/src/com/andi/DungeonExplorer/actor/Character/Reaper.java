package com.andi.DungeonExplorer.actor.Character;

import com.andi.DungeonExplorer.actor.Actor;
import com.andi.DungeonExplorer.battle.moves.Attack;
import com.andi.DungeonExplorer.battle.moves.SpecialEffect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static com.andi.DungeonExplorer.battle.moves.Attack.damageType.DARK;
import static com.andi.DungeonExplorer.battle.moves.Attack.damageType.FIRE;
import static com.andi.DungeonExplorer.battle.moves.Attack.damageType.IMPACT;
import static com.andi.DungeonExplorer.battle.moves.Attack.damageType.SLASH;

/**
 * Created by Kenneth on 11/30/2017.
 * First boss.
 */

public class Reaper extends Monster {

    public Reaper (int[] stats, HashMap<Attack.damageType, Double> resistances, int level, float growthFactor, int expValue, Actor owner){
        super(stats, resistances, level, growthFactor, expValue, owner, "Reaper");
        populateAttacks();
    }

    /**
     * Skeleton with randomly generated stats based on level and growth factor.
     * Default resistances and stat adjustment.
     */
    public Reaper(int level, float growthFactor, int expValue, Actor owner){
        super(level, growthFactor, expValue, owner);
        resistances.put(Attack.damageType.PIERCE, .5);
        resistances.put(IMPACT, 2.0);
        resistances.put(FIRE, 2.0);
        maxMP = 0;
        MP = 0;
        health+=100;
        statAcc = (int)(statAcc * .9);
        statLuk = (int)(statLuk * .5);
        statEva = (int)(statEva * .8);
        statMag = (int)(statMag * 1.2);
        innateAttacks = new HashMap<String, Attack>();
        populateAttacks();
        combatAI = new com.andi.DungeonExplorer.actor.Character.CombatAI(this);
        name = "Reaper";
    }

    /**
     * fills the attack map with some default attacks for this monster
     */
    public void populateAttacks(){
        Collection<SpecialEffect> effects = new ArrayList<SpecialEffect>();
        effects.add(new SpecialEffect(SpecialEffect.type.LIFESTEAL, 1, 0, false, false));
        innateAttacks.put("Death Touch", new Attack("Death Touch", 10, 90, 5, DARK, true, 0, effects, 1, 1));
        innateAttacks.put("Curse", new Attack("Curse", 8, 65, 5, DARK, true, 0, null, .3f, 5));
        innateAttacks.put("Bony Grasp", new Attack("Bony Grasp", 12, 70, 5, IMPACT, false, 0, null, 2, 1));
    }
}
