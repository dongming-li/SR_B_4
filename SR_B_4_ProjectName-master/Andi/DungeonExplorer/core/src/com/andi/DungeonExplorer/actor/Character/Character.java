package com.andi.DungeonExplorer.actor.Character;

import com.andi.DungeonExplorer.battle.moves.Attack;
import com.andi.DungeonExplorer.actor.Actor;
import com.andi.DungeonExplorer.world.Equipment.Equipment;
import com.andi.DungeonExplorer.world.World;
import com.badlogic.gdx.Game;

import java.util.HashMap;
import java.util.Random;

import static com.badlogic.gdx.scenes.scene2d.ui.Table.Debug.actor;

/**
 * Created by bryton on 9/10/17.
 * WAS: Abstract class providing a backbone for character instances
 * IS NOW: data object to be assigned to all character-actors, containing the RPG-related stats and methods
 */

public class Character {

    /*
    RPG stats
     */
    /**
     * Influences physical damage.
     * Increases base damage by one for every 2 points
     * Increases attack multiplier by 1 percent for each point
     */
    public int statAtk;
    /**
     * Influences physical resistance.
     * Decreases damage taken by one for each two points
     */
    public int statDef;
    /**
     * Influences magic resistance.
     * Decreases damage taken by one for each two points
     */
    public int statRes;
    /**
     * Influences hit rate.
     * Increases hit rate by 1 percent per point
     */
    public int statAcc;
    /**
     * Influences dodge rate.
     * Increases dodge rate by 1 percent per point
     */
    public int statEva;
    /**
     * Influences crit chance, crit avoid, loot quality and amount
     * Increases crit chance and avoid by 1 percent per point (i.e. crits are a luck contest)
     */
    public int statLuk;
    /**
     * Influences magic power
     * Increases base damage by one for every 2 points
     * Increases attack multiplier by 1 percent for each point
     */
    public int statMag;
    /**
     * Influences attack order
     */
    public int statSpd;
    /**
     * resource pool for magic use
     */
    public int maxMP;
    public int MP;
    /**
     * resistances to certain damage types.
     * 1 is neutral, < 1 reduces damage from that type, > 1 increases damage from that type
     */
    public HashMap<Attack.damageType, Double> resistances;
    /**
     * for knowing when to level up. Probably only for player.
     * resets to 0 every level up, need level*1000 to level up again.
     */
    public int exp;
    /**
     * level of the character, for display and calculation purposes
     */
    public int level;

    /**
     * influences the amount of stats gained on level up. See levelUp() for details
     */
    public float growthFactor;

    /**
     * amount of exp to give the player when this character dies
     */
    public int expValue;


    /**
     * Gear currently equipped by the player. Has Head, Body, Legs, Arms, MainHand, OffHand keys.
     */
    public HashMap<String, Equipment> equipment;
    /**
     * Row in grid corresponding to character's position
     */
    public int xPos;

    /**
     * Column in grid corresponding to character's position
     */
    public int yPos;

    /**
     * The amount of health this character has left. Dies when this becomes 0.
     */
    public int health;

    /**
     * Smaller division of health for use in realtime effects like poison.
     * ratio is 100:1
     */
    public int healthFrac;

    /**
     * The maximum amount of health this character can have
     */
    public int maxHealth;

    /**
     * Actor instance that owns this character
     */
    public Actor owner;

    /**
     * Whether this character is still alive or not
     */
    public Boolean alive;

    /**
     * True if this character can occupy a gap tile, false otherwise
     */
    public Boolean passesGaps;

    /**
     * The direction the player is currently facing
     */
    public dir facingDir;

    /**
     * The game to which the character belongs
     */
    public Game game;

    public String name;

    /**
     * Map of status effects on the player.
     */
    public HashMap <StatusEffect.type, StatusEffect> statusEffects;

    /**
     * enumerator type for directions
     */
    public enum dir{
        UP, DOWN, LEFT, RIGHT
    }

    /**
     * Full constructor
     * @param owner actor instance owning this object
     * @param stats 10 element array, attack through max MP.
     * @param level starting level. This constructor doesn't autolevel from 0 to this, so set stats considering that.
     * @param growthFactor default 1, modifies stat gains on levelup.
     * @param expValue how much exp to give the player when this character dies.
     */
    public Character(int[] stats, int level, float growthFactor, int expValue, Actor owner, String name){
        setStatsFromArray(stats);
        this.level = level;
        this.growthFactor = growthFactor;
        this.expValue = expValue;
        resistances = new HashMap<Attack.damageType, Double>();
        statusEffects = new HashMap<StatusEffect.type, StatusEffect>();
        equipment = new HashMap<String, Equipment>();
        equipment.put("Head", null);
        equipment.put("Body", null);
        equipment.put("Legs", null);
        equipment.put("Arms", null);
        equipment.put("MainHand", null);
        equipment.put("OffHand", null);
        this.owner = owner;
        alive = true;
        this.name = name;
    }


    /**
     * default constructor - owner needs to be set manually if you use this
     */
    public Character(){
        setStatsFromArray(new int[10]);
        this.level=1;
        this.growthFactor=1;
        this.expValue = 100;
        resistances = new HashMap<Attack.damageType, Double>();
        statusEffects = new HashMap<StatusEffect.type, StatusEffect>();
        equipment = new HashMap<String, Equipment>();
        equipment.put("Head", null);
        equipment.put("Body", null);
        equipment.put("Legs", null);
        equipment.put("Arms", null);
        equipment.put("MainHand", null);
        equipment.put("OffHand", null);
        alive = true;
        name = "Unnamed";
    }


    /**
     * Runs each render cycle. Carry out any real-time tasks here.
     */
    public void Update(){
        //deal with status effects
        for (StatusEffect.type type : StatusEffect.type.values()) {
            if(statusEffects.get(type) != null){
                StatusEffect status = statusEffects.get(type);
                if(status.timeLeft > 0){
                    status.timeLeft--;
                    if(status.timeLeft==0){
                        RemoveStatus(status.name);
                    }
                }
                //run actual effects for realtime effects
                if(type == StatusEffect.type.POISON){
                    healthFrac -= (int)status.magnitude;// 1/100th of a health point per magnitude
                    if(status.timeLeft%300==0){
                        System.out.println("Took poison damage, current hp is " + health);
                    }
                }
            }
        }
        
        //deal with fractional health
        health += (healthFrac/100);
        healthFrac = healthFrac%100;
        if(health <= 0){
            kill();
        }
    }

    /*
        Basic methods
     */

    /**
     * Get the tile occupying the same TileGrid space as this character
     * @return Tile this character is standing on
     */
    /*
    public Tile getTile(){
        return grid.getTile(xPos, yPos);
    }
*/
    /**
     * Get the row in grid corresponding to this characters position
     * @return row corresponding to this characters position
     */
    public int getPosX() { return xPos; }

    /**
     * Get the column in grid corresponding to this characters position
     * @return column corresponding to this characters position
     */
    public int getPosY() { return yPos; }

    /**
     * Get this character's amount of health
     * @return health of this character
     */
    public int getHealth(){
        return this.health;
    }

    /**
     * Find out if this character is still alive
     * @return true if alive, false if dead
     */
    public Boolean isAlive(){
        return this.alive;
    }

    /**
     * Find out if this character can pass gaps
     * @return True if this character can occupy gap tiles, false otherwise
     */
    public Boolean canPassGaps() { return this.passesGaps; }

    /**
     * Get the direction that this character is currently facing
     * @return Direction this character is facing
     */
    public dir getFacingDir(){
        return facingDir;
    }

    /*
        Health related methods
     */

    /**
     * Inflict damage on this character, subtracting it from their health
     * @param amountDamage amount of damage to be inflicted
     * @return true if successful, false otherwise
     */
    public Boolean inflictDamage(int amountDamage){
        if(!alive){
            return false;
        }
        else{
            health = health - amountDamage;
            if(health <= 0){
                this.kill();
            }
        }
        return true;
    }

    /**
     * Inflict damage on this character, subtracting it from their health
     * Takes defense into account; for use in traps, etc.
     * @param amountDamage amount of damage to be inflicted
     * @return true if successful, false otherwise
     */
    public Boolean inflictDamageMitigated(int amountDamage){
        amountDamage = Math.max(1, amountDamage-statDef/2);
        return inflictDamage(amountDamage);
    }

    /**
     * Restore a specified amount of health to the player
     * @param amountHealth amount of health to be restored
     * @return amount actually healed
     */
    public int heal(int amountHealth){
        int missing = maxHealth - health;
        health += Math.min(missing, amountHealth);
        return missing;
    }

    /**
     * Immediately kills the character and sets its health to 0
     * @return true if successful, false otherwise
     */
    public Boolean kill(){
        if(!alive){
            return false;
        }
        else{
            health = 0;
            alive = false;
            onDeath();

            owner.setState(Actor.ACTOR_STATE.DEAD);
            //owner.getWorld().removeActor(owner);
        }
        return true;
    }


    /*
    Status Effect Methods
    */

    /**
     * Add the specified status effect to the character.
     * @param name status type; determines effect
     * @param time time in render cycles the effect will last (-1 for indefinite)
     * @param magnitude multiplier for effect magnitude, when applicable
     * @param overwrite whether a buff of the same type should be overwritten
     * @return true if the effect was applied, false otherwise
     */
    public boolean AddStatus(StatusEffect.type name, int time, double magnitude, boolean overwrite){
        if(!overwrite && statusEffects.containsKey(name)){
            return false;
        }
        statusEffects.put(name, new StatusEffect(name, time, magnitude));

        //initialize effects of parameter-changing effects
        if(name == StatusEffect.type.MAXHP){
            maxHealth += (int)magnitude;
            heal((int)magnitude);
        }
        return true;
    }

    /**
     * Remove the specified status effect from the character
     * @param name status type to remove
     * @return true if the effect was removed, false if there was nothing to remove
     */
    public boolean RemoveStatus(StatusEffect.type name){
        if(statusEffects.get(name) == null){
            return false;
        }
        //end effects of parameter-changing effects
        if(name == StatusEffect.type.MAXHP){
            double magnitude = statusEffects.get(name).magnitude;
            maxHealth -= (int)magnitude;
        }
        statusEffects.remove(name);
        return true;
    }

    /*
    Stat change methods (permanent)
     */
    public void changeAtk(int amt){
        statAtk += amt;
    }
    public void changeDef(int amt){
        statDef += amt;
    }
    public void changeRes(int amt){
        statRes += amt;
    }
    public void changeEva(int amt){
        statEva += amt;
    }
    public void changeAcc(int amt){
        statAcc += amt;
    }
    public void changeLuk(int amt){
        statLuk += amt;
    }
    public void changeMag(int amt) { statMag += amt; }
    public void changeSpd(int amt) { statSpd += amt; }
    public void changeMaxHP(int amt) { maxHealth += amt; }
    public void changeMaxMP(int amt) { maxMP += amt; }

    /**
     *
     * @param amt exp to be given
     * @return true if the character levels up, false otherwise.
     */
    public boolean gainEXP(int amt) {
        exp += amt;
        if(level*1000 < exp){
            levelUp();
            exp = exp - (level*1000);
            return true;
        }
        return false;
    }

    /**
     * increases the level of the character and semirandomly increases their stats.
     * primary stats are guaranteed at least one point each; HP 15, MP 10.
     * primary stats have 8+ points distributed, chance-based have 4+ (based on growth factor)
     */
    public void levelUp(){
        //TODO check for player
        level++;
        System.out.println("Leveled up to " + level);
        int[] statIncreases = new int[]{1,1,1,1,0,0,0,0,0,0};
        Random rand = new Random();
        //primary stats
        for(int i=0; i<Math.ceil(8*growthFactor); i++){
            statIncreases[rand.nextInt(4)]++;
        }
        //chance-based stats
        for(int i=0; i<Math.ceil(5*growthFactor); i++){
            statIncreases[4 + rand.nextInt(4)]++;
        }
        //resource pools
        statIncreases[8] = (int)(15 + 10 * rand.nextDouble() * growthFactor);
        statIncreases[9] = (int)(10 + 8 * rand.nextDouble() * growthFactor);
        changeAllStats(statIncreases, owner.isPlayer);
    }

    /**
     * modifies all stats based on an array of ints.
     * for use in levelUp().
     * @param amts array of stat increases
     */
    public void changeAllStats(int[] amts, boolean displayGains){
        changeAtk(amts[0]);
        changeDef(amts[1]);
        changeRes(amts[2]);
        changeMag(amts[3]);
        changeEva(amts[4]);
        changeAcc(amts[5]);
        changeLuk(amts[6]);
        changeSpd(amts[7]);
        changeMaxHP(amts[8]);
        heal(amts[8]);
        changeMaxMP(amts[9]);
        if(displayGains){
            System.out.println("Atk increased by "+amts[0]);
            System.out.println("Def increased by "+amts[1]);
            System.out.println("Res increased by "+amts[2]);
            System.out.println("Mag increased by "+amts[3]);
            System.out.println("Eva increased by "+amts[4]);
            System.out.println("Acc increased by "+amts[5]);
            System.out.println("Luk increased by "+amts[6]);
            System.out.println("Spd increased by "+amts[7]);
            System.out.println("HP increased by "+amts[8]);
            System.out.println("MP increased by "+amts[9]);
        }
    }

    /*
    Combat calculations
     */

    /**
     * Generalized attack method for combat.
     * Takes these parameters and decides whether the attack hits,
     * as well as how much damage it should deal to the target.
     * @param attacker initiator of the attack
     * @param defender target of the attack
     * @param attack data of the attack (accuracy, damage, etc.)
     * @param counterAttack whether the attack is a counter
     * @return true if the attack lands, false otherwise
     */
    public static boolean attack (Character attacker, Character defender, Attack attack, boolean counterAttack) {
        if(!attacker.alive || !defender.alive){
            return false;
        }
        if (!checkHit(attacker, defender, attack.baseAcc)) {
            String output = attacker.name + " missed " + defender.owner.name + " with " + attack.name + "!";
            System.out.println(output);
            return false;
        }
        double finalDam = ((attack.baseDam + (attack.magic ? attacker.statMag : attacker.statAtk) / 2));//base damage
        finalDam *= (1 + (attack.magic ? attacker.statMag : attacker.statAtk) / 100.0);//apply atk stat multiplier
        if (attack.type != null) {
            double mult;
            if (defender.resistances.get(attack.type) == null) {//if no resistance is specified
                mult = 1;
            } else mult = defender.resistances.get(attack.type);
            finalDam *= mult;//apply damage type multiplier
        }
        Random rand = new Random();
        boolean crit = attacker.statLuk + attack.baseCrit - defender.statLuk > rand.nextInt(100);
        if (crit) {
            finalDam *= 2;
        }//apply crit modifier
        if (attacker.statusEffects.containsKey(StatusEffect.type.POWER)) {
            finalDam *= (1 + attacker.statusEffects.get(StatusEffect.type.POWER).magnitude / 100.0);//apply status effect multiplier
        }
        int finalDef = (attack.magic ? defender.statRes : defender.statDef) / 2;//base reduction
        finalDam -= finalDef;
        if (defender.statusEffects.containsKey(StatusEffect.type.DEFENSE)) {
            finalDam *= (1 - defender.statusEffects.get(StatusEffect.type.DEFENSE).magnitude / 100.0);//apply status effect multiplier
        }
        //Apply facing multiplier - deal more damage from sides and back
        if(!counterAttack){
            float facingMult = GetFacingMult(attacker, defender);
            finalDam *= facingMult;
        }
        //and deal less damage with your counter if you're attacked that way
        else{
            float facingMult = GetFacingMult(attacker, defender);
            finalDam /= facingMult;
        }
        //Apply random multiplier .8 to 1.2x
        finalDam *= (.8 + Math.random() * .4);
        finalDam = Math.max(1, finalDam);//no negative damage
        //Debug stuff
        String output = attacker.name + " hit " + defender.owner.name + " with " + attack.name + ", dealing " + (int) finalDam + " damage!" + (crit?"!!!":"");
        System.out.println(output);
        //actually do the things
        defender.inflictDamage((int) finalDam);
        onHit(attacker, defender, attack, (int) finalDam, crit);
        return true;
    }

    private static float GetFacingMult (Character attacker, Character defender){
        switch (attacker.owner.facing){
            case NORTH:
                switch (defender.owner.facing){
                    case NORTH:return 2;
                    case EAST:return 1.5f;
                    case WEST:return 1.5f;
                    case SOUTH:return 1;
                }
            case SOUTH:
                switch (defender.owner.facing){
                    case NORTH:return 1;
                    case EAST:return 1.5f;
                    case WEST:return 1.5f;
                    case SOUTH:return 2;
                }
            case EAST:
                switch (defender.owner.facing){
                    case NORTH:return 1.5f;
                    case EAST:return 2;
                    case WEST:return 1;
                    case SOUTH:return 1.5f;
                }
            case WEST:
                switch (defender.owner.facing){
                    case NORTH:return 1.5f;
                    case EAST:return 1;
                    case WEST:return 2;
                    case SOUTH:return 1.5f;
                }
        }
        return 1;
    }

    /**
     * Determines whether an attack hits based on stats and a randomly generated value.
     * @param attacker initiator of the attack
     * @param defender target of the attack
     * @param attackAcc base accuracy of the attack
     * @return true if the attack lands
     */
    public static boolean checkHit (Character attacker, Character defender, int attackAcc){
        int finalAcc = attackAcc + attacker.statAcc - defender.statEva;
        Random rand = new Random();
        return(finalAcc > rand.nextInt(100));
    }

    /**
     * Hook for adding special effects to attacks, like lifesteal or status effect infliction.
     * Called when an attack lands, after damage is applied to the target.
     * @param attacker initiator of the attack
     * @param defender target of the attack
     * @param attack attack data
     * @param damage damage dealt to the target
     * @param crit whether the attack was a critical strike
     */
    public static void onHit (Character attacker, Character defender, Attack attack, int damage, boolean crit){
        attack.applyEffects(attacker, defender, attack, damage, crit);
        //the following will always fail for the player, probably a better way to do this but the code only needs to execute for monsters anyway
        try{
            if(((com.andi.DungeonExplorer.actor.Character.Monster) attacker).combatAI != null){
                ((com.andi.DungeonExplorer.actor.Character.Monster) attacker).combatAI.hit(attack.name, damage/defender.maxHealth, 1);
            }
        }
        catch(Exception exception){
            //System.out.println("couldn't talk to AI, " + exception.toString());
        }
    }

    /**
     * things to do when the character dies
     */
    public void onDeath (){
        if(owner.isPlayer){
            System.out.println("You Died");
            return;
        }
        //find player and add exp to them based on expValue
        System.out.println(name + " died");
        World world = owner.getWorld();
        Character player = world.getPlayer();
        alive = false;
        owner.dropObject();

        player.gainEXP(expValue);
    }

    /**
     * sets stats to the values in an array
     * @param stats
     */
    public void setStatsFromArray(int[] stats){
        statAtk = stats[0];
        statDef = stats[1];
        statRes = stats[2];
        statAcc = stats[3];
        statEva = stats[4];
        statLuk = stats[5];
        statMag = stats[6];
        statSpd = stats[7];
        maxHealth = stats[8];
        health = maxHealth;
        healthFrac = 0;
        maxMP = stats[9];
        MP = maxMP;
    }

    /**
     * Equips a piece of equipment, removing the thing occupying the relevant slot if necessary.
     * Also adds whatever stat bonuses and penalties the equipment provides.
     * @param toEquip the piece of equipment to equip
     */
    public void equip(Equipment toEquip){
        String slot = toEquip.slot;
        if(equipment.get(slot) != null){
            unequip(slot);
        }
        changeAllStats(toEquip.getArrayFromStats(), false);
        equipment.put(slot, toEquip);
    }

    /**
     * removes the equipment from the specified slot, and removes any stat bonuses/penalties
     * that equipment gave.
     * @param slot slot type to remove equipment from
     */
    public void unequip(String slot){
        if(equipment.get(slot) == null){
            return;
        }
        Equipment toRemove = equipment.get(slot);
        int[] negStats = new int[10];
        int[] stats = toRemove.getArrayFromStats();
        for(int i=0; i<10; i++){
            negStats[i] = -stats[i];
        }
        changeAllStats(negStats, false);
        equipment.put(slot, null);
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
        output += name;
        return output;
    }
}
