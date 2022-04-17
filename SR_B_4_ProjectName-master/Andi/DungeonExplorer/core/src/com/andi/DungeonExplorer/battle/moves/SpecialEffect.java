package com.andi.DungeonExplorer.battle.moves;

import com.andi.DungeonExplorer.actor.Character.StatusEffect;
import com.andi.DungeonExplorer.actor.Character.Character;

import static com.andi.DungeonExplorer.battle.moves.SpecialEffect.type.LIFESTEAL;
import static com.andi.DungeonExplorer.battle.moves.SpecialEffect.type.POISON;
import static com.andi.DungeonExplorer.battle.moves.SpecialEffect.type.RECOIL;

/**
 * Created by Kenneth on 10/25/2017.
 * Effects contained by attacks, projectiles, maybe other stuff too
 */

public class SpecialEffect {

    public type name;
    public double magnitude;
    public int applyTime;//for status effects only
    public boolean onCritOnly;
    public boolean overwrite;



    public enum type{
        LIFESTEAL,//gain hp equal to damage dealt * magnitude
        POISON,//apply poison status
        RECOIL//take damage equal to damage dealt * magnitude
    }


    /**
     * Constructor
     * @param name type, used to determine what the effect does
     * @param magnitude how strong the effect is
     * @param applyTime how long the effect should be applied for (when relevant)
     * @param onCritOnly if the effect should only apply on a critical strike
     * @param overwrite if the status effect should override an existing one of the same type
     */
    public SpecialEffect(type name, double magnitude, int applyTime, boolean onCritOnly, boolean overwrite){
        this.name = name;
        this.magnitude = magnitude;
        this.applyTime = applyTime;
        this.onCritOnly = onCritOnly;
    }

    public void doEffect(Character attacker, Character defender, Attack attack, int damage, boolean crit){
        if(name==LIFESTEAL){
            attacker.heal((int)(damage*magnitude));
        }
        else if(name==POISON){
            defender.AddStatus(StatusEffect.type.POISON, applyTime, magnitude, overwrite);
        }
        else if(name==RECOIL){
            attacker.inflictDamage((int)(damage*magnitude));
        }
    }

    /**
     *
     * @return data in this class as a string
     */
    public String ToString() {
        String output = name.toString()+ ":" +magnitude+ ":" +applyTime+ ":" +onCritOnly+ ":" +overwrite;
        return output;
    }
}
