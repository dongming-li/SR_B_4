package com.andi.DungeonExplorer.battle.moves;

import java.util.Collection;
import java.util.HashSet;

import com.andi.DungeonExplorer.actor.Character.Character;

/**
 * Created by Kenneth on 10/1/2017.
 * Contains information on an attack carried out in combat, such as its base damage.
 */

public class Attack {
    /**
     * name of the attack
     */
    public String name;
    /**
     * for use in damage calc
     */
    public int baseDam;
    /**
     * base percent hit chance
     */
    public int baseAcc;
    /**
     * base percent crit chance
     */
    public int baseCrit;
    /**
     * level requirement for using the attack
     */
    public int levelReq = 0;
    /**
     * weight for randomly selecting the attack - default is 1
     */
    public float weight;
    /**
     * how many tiles away the atack reaches
     */
    public int range;

    /**
     * check against resistances to modify damage
     */
    public enum damageType{
        PIERCE, SLASH, IMPACT, FIRE, DARK
    }

    public damageType type;
    /**
     * whether the attack deals magic damage (mag/res instead of atk/def)
     */
    public boolean magic;
    /**
     * effects applied by the attack
     */
    public Collection<SpecialEffect> specialEffects;

    /**
     * Full constructor
     * @param dam base damage
     * @param acc base accuracy percent
     * @param crit base crit chance percent
     * @param type damage type of the attack - NULL is acceptable here
     * @param magic if the attack should use Mag/Res instead of Atk/Def
     * @param levelReq minimum level of the player required to use this attack
     */
    public Attack (String name, int dam, int acc, int crit, damageType type, boolean magic, int levelReq, Collection<SpecialEffect> effects, float weight, int range){
        this.name = name;
        baseDam = dam;
        baseAcc = acc;
        baseCrit = crit;
        this.type = type;
        this.magic = magic;
        this.levelReq = levelReq;
        specialEffects = effects;
        this.weight = weight;
        this.range = range;
    }
    /**
     * Shorter constructor
     * @param dam base damage
     * @param acc base accuracy percent
     * @param crit base crit chance percent
     * @param type damage type of the attack - NULL is acceptable here
     */
    public Attack (String name, int dam, int acc, int crit, damageType type){
        this.name = name;
        baseDam = dam;
        baseAcc = acc;
        baseCrit = crit;
        this.type = type;
        this.weight = 1;
        this.range = 1;
        specialEffects = new HashSet<SpecialEffect>();
    }

    /**
     * applies the effects
     * @param attacker
     * @param defender
     * @param attack
     * @param damage
     * @param crit
     */
    public void applyEffects(Character attacker, Character defender, Attack attack, int damage, boolean crit){
        if(specialEffects == null){
            return;
        }
        for(SpecialEffect effect : specialEffects){
            effect.doEffect(attacker, defender, attack, damage, crit);
        }
    }

    /**
     * represents the data in this class as a string
     */
    public String toString(){
        String output = "";
        output += name + ":" + baseDam+ ":" +baseAcc+ ":" +baseCrit+ ":" +levelReq+ ":" +weight+ ":" +range+ ":" +type.toString()+ ":" +magic+ ":";
        if(specialEffects!=null){
            output += specialEffects.toString();
        }
        else{
            output += "null";
        }
        return output;
    }
}

