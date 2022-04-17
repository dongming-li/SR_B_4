package com.mygdx.game;

/**
 * Created by Kenneth on 10/1/2017.
 * Contains information on an attack carried out in combat, such as its base damage.
 */

public class Attack {
    public String name;
    public int baseDam;
    public int baseAcc;
    public int baseCrit;
    public int levelReq = 0;
    public enum damageType{
        PIERCE, SLASH, IMPACT
    }
    public damageType type;
    boolean magic;

    /**
     * Full constructor
     * @param dam base damage
     * @param acc base accuracy percent
     * @param crit base crit chance percent
     * @param type damage type of the attack - NULL is acceptable here
     * @param magic if the attack should use Mag/Res instead of Atk/Def
     * @param levelReq minimum level of the player required to use this attack
     */
    public Attack (String name, int dam, int acc, int crit, damageType type, boolean magic, int levelReq){
        this.name = name;
        baseDam = dam;
        baseAcc = acc;
        baseCrit = crit;
        this.type = type;
        this.magic = magic;
        this.levelReq = levelReq;
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
    }
}
