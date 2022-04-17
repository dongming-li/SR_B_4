package com.mygdx.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by bryton on 9/10/17.
 * Abstract class providing a backbone for character instances
 */

public abstract class Character {

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
     * Inventory of items that this character owns
     */
    public Inventory inventory;

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
                }
            }
        }
        
        //deal with fractional health
        health += (healthFrac/100);
        healthFrac = healthFrac%100;
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
     * Get this characters inventory
     * @return The inventory object belonging to this character
     */
    public Inventory getInventory() { return this.inventory; }

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
            health = health - amountDamage;     //Add armor capabilities
            if(health <= 0){
                this.kill();
            }
        }
        return true;
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
        }
        return true;
    }

    /*
        Movement capabilities methods
     */

    /**
     * Determine the characters ability to move into the tile above it
     * @return true if this character can move upwards, false otherwise
     */
    /*
    public Boolean canMoveUp(){
        if((yPos == 0) || (!grid.getTile(xPos, yPos - 1).passable)){
            return false;
        }
        return true;
    }*/

    /**
     * Determine the characters ability to move into the tile below it
     * @return true if this character can move downwards, false otherwise
     */
    /*
    public Boolean canMoveDown(){
        if((yPos == grid.getTileArray().length - 1) || (!grid.getTile(xPos, yPos + 1).passable)){
            return false;
        }
        return true;
    }*/

    /**
     * Determine the characters ability to move into the tile to its left
     * @return true if this character can move left, false otherwise
     */
    /*
    public Boolean canMoveLeft(){
        if((xPos == 0) || (!grid.getTile(xPos - 1, yPos).passable)){
            return false;
        }
        return true;
    }*/

    /**
     * Determine the characters ability to move into the tile to its right
     * @return true if this character can move right, false otherwise
     */
    /*
    public Boolean canMoveRight(){
        if((xPos == grid.getTileArray()[0].length - 1) || (!grid.getTile(xPos + 1, yPos).passable)){
            return false;
        }
        return true;
    }
    */

    /**
     * Determine the characters ability to move up multiple tiles
     * @param numTiles desired number of tiles to move
     * @return True if the character can move numTiles upwards, false otherwise
     */
    /*
    public Boolean canMoveUpMult(int numTiles){
        int y = yPos;
        for(int i = 0; i < numTiles; i++){
            if((y == 0) || (!grid.getTile(xPos, y + 1).passable)){
                return false;
            }
            y++;
        }
        return true;
    }
*/
    /**
     * Determine the characters ability to move down multiple tiles
     * @param numTiles desired number of tiles to move
     * @return True if the character can move numTiles downwards, false otherwise
     */
    /*
    public Boolean canMoveDownMult(int numTiles){
        int y = yPos;
        for(int i = 0; i < numTiles; i++){
            if((y == grid.getTileArray().length - 1) || (!grid.getTile(xPos, y - 1).passable)){
                return false;
            }
            y--;
        }
        return true;
    }*/

    /**
     * Determine the characters ability to move left multiple tiles
     * @param numTiles desired number of tiles to move
     * @return True if the character can move numTiles to the left, false otherwise
     */
    /*
    public Boolean canMoveLeftMult(int numTiles){
        int x = xPos;
        for(int i = 0; i < numTiles; i++){
            if((x == 0) || (!grid.getTile(xPos, x - 1).passable)){
                return false;
            }
            x--;
        }
        return true;
    }*/

    /**
     * Determine the characters ability to move right multiple tiles
     * @param numTiles desired number of tiles to move
     * @return True if the character can move numTiles to the right, false otherwise
     */
    /*
    public Boolean canMoveRightMult(int numTiles){
        int x = xPos;
        for(int i = 0; i < numTiles; i++){
            if((x == grid.getTileArray()[0].length-1) || (!grid.getTile(xPos, x + 1).passable)){
                return false;
            }
            x++;
        }
        return true;
    }*/

    /**
     * Determine the characters ability to move to a specified tile
     * @param row row associated with desired tile position
     * @param col column associated with desired tile position
     * @return True if this character can move to the specified tile, false otherwise
     */
    /*
    public Boolean canMoveToPos(int row, int col){
        if(grid.getTile(row, col).passable){
            return true;
        }
        return false;
    }*/

    /*
        Movement methods
     */

    /**
     * Move this characters position up by 1 row
     * @return true if successful, false otherwise
     */
    /*
    public Boolean moveUp(){
        if(this.facingDir != dir.UP){
            this.facingDir = dir.UP;
            return true;
        }
        else if(this.canMoveUp()){
            yPos = yPos - 1;
            return true;
        }
        return false;
    }
    */

    /**
     * Move this characters position down by 1 row
     * @return true if successful, false otherwise
     */
    /*
    public Boolean moveDown(){
        if(this.facingDir != dir.DOWN){
            this.facingDir = dir.DOWN;
            return true;
        }
        else if(this.canMoveDown()){
            yPos = yPos - 1;
            return true;
        }
        return false;
    }*/

    /**
     * Move this characters position left by 1 column
     * @return true if successful, false otherwise
     */
    /*
    public Boolean moveLeft(){
        if(this.facingDir != dir.LEFT){
            this.facingDir = dir.LEFT;
            return true;
        }
        else if(this.canMoveLeft()){
            yPos = yPos - 1;
            return true;
        }
        return false;
    }*/

    /**
     * Move this characters position right by 1 column
     * @return true if successful, false otherwise
     */
    /*
    public Boolean moveRight(){
        if(this.facingDir != dir.RIGHT){
            this.facingDir = dir.RIGHT;
            return true;
        }
        else if(this.canMoveRight()){
            yPos = yPos - 1;
            return true;
        }
        return false;
    }
*/
    /**
     * Move the character upwards by multiple tiles
     * @param numTiles desired number of tiles to move
     * @return True if successful, false otherwise
     */
    /*
    public Boolean moveUpMult(int numTiles){
        if(this.facingDir != dir.UP){
            this.facingDir = dir.UP;
        }
        if(this.canMoveUpMult(numTiles)){
            yPos = yPos + numTiles;
            return true;
        }
        return false;
    }*/

    /**
     * Move the character downwards by multiple tiles
     * @param numTiles desired number of tiles to move
     * @return True if successful, false otherwise
     */
    /*
    public Boolean moveDownMult(int numTiles){
        if(this.facingDir != dir.DOWN){
            this.facingDir = dir.DOWN;
        }
        if(this.canMoveDownMult(numTiles)){
            yPos = yPos - numTiles;
            return true;
        }
        return false;
    }*/

    /**
     * Move the character to the left by multiple tiles
     * @param numTiles desired number of tiles to move
     * @return True if successful, false otherwise
     */
    /*
    public Boolean moveLeftMult(int numTiles){
        if(this.facingDir != dir.LEFT){
            this.facingDir = dir.LEFT;
        }
        if(this.canMoveLeftMult(numTiles)){
            xPos = xPos - numTiles;
            return true;
        }
        return false;
    }
*/
    /**
     * Move the character to the right by multiple tiles
     * @param numTiles desired number of tiles to move
     * @return True if successful, false otherwise
     */
    /*
    public Boolean moveRightMult(int numTiles){
        if(this.facingDir != dir.RIGHT){
            this.facingDir = dir.RIGHT;
        }
        if(this.canMoveRightMult(numTiles)){
            xPos = xPos + numTiles;
            return true;
        }
        return false;
    }*/

    /**
     * Move the character to a specified tile
     * @param row row of desired tile in grid
     * @param col column on desired tile in grid
     * @return true if successful, false otherwise
     */
    /*
    public Boolean moveToPos(int row, int col){
        if(this.canMoveToPos(row, col)){
            yPos = row;
            xPos = col;
            return true;
        }
        return false;
    }*/

    /**
     * Move the character upwards as far as possible
     * @return number of tiles transgressed
     */
    /*
    public int allTheWayUp(){
        int cnt = 0;
        if(this.facingDir != dir.UP){
            this.facingDir = dir.UP;
        }
        while(this.canMoveUp()){
            this.moveUp();
            cnt++;
        }
        return cnt;
    }*/

    /**
     * Move the character downwards as far as possible
     * @return number of tiles transgressed
     */
    /*
    public int allTheWayDown(){
        int cnt = 0;
        if(this.facingDir != dir.DOWN){
            this.facingDir = dir.DOWN;
        }
        while(this.canMoveDown()){
            this.moveDown();
            cnt++;
        }
        return cnt;
    }*/

    /**
     * Move the character to the left as far as possible
     * @return number of tiles transgressed
     */
    /*
    public int allTheWayLeft(){
        int cnt = 0;
        if(this.facingDir != dir.LEFT){
            this.facingDir = dir.LEFT;
        }
        while(this.canMoveLeft()){
            this.moveLeft();
            cnt++;
        }
        return cnt;
    }
*/
    /**
     * Move the character to the right as far as possible
     * @return number of tiles transgressed
     */
    /*
    public int allTheWayRight(){
        int cnt = 0;
        if(this.facingDir != dir.RIGHT){
            this.facingDir = dir.RIGHT;
        }
        while(this.canMoveRight()){
            this.moveRight();
            cnt++;
        }
        return cnt;
    }
*/
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
        level++;
        int[] statIncreases = new int[]{1,1,1,1,0,0,0,0,0};
        Random rand = new Random();
        //primary stats
        for(int i=0; i<Math.ceil(8*growthFactor); i++){
            statIncreases[rand.nextInt(4)]++;
        }
        //chance-based stats
        for(int i=0; i<Math.ceil(4*growthFactor); i++){
            statIncreases[4 + rand.nextInt(3)]++;
        }
        //resource pools
        statIncreases[7] = (int)(15 + 10 * rand.nextInt() * growthFactor);
        statIncreases[8] = (int)(10 + 8 * rand.nextInt() * growthFactor);
        changeAllStats(statIncreases);
    }

    /**
     * modifies all stats based on an array of ints.
     * for use in levelUp().
     * @param amts array of stat increases
     */
    public void changeAllStats(int[] amts){
        changeAtk(amts[0]);
        changeDef(amts[1]);
        changeRes(amts[2]);
        changeMag(amts[3]);
        changeEva(amts[4]);
        changeAcc(amts[5]);
        changeLuk(amts[6]);
        changeMaxHP(amts[7]);
        changeMaxMP(amts[8]);
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
     * @return true if the attack lands, false otherwise
     */
    public boolean attack (Character attacker, Character defender, Attack attack){
        if(!checkHit(attacker, defender, attack.baseAcc)){
            return false;
        }
        double finalDam = ((attack.baseDam + (attack.magic ? attacker.statMag : attacker.statAtk) / 2));//base damage
        //TODO apply equipment damage modifiers
        finalDam *= (1 + (attack.magic ? attacker.statMag : attacker.statAtk) / 100.0);//apply atk stat multiplier
        if(attack.type != null){
            double mult;
            if(resistances.get(attack.type) == null){//if no resistance is specified
                mult = 1;
            }
            else mult = resistances.get(attack.type);
            finalDam *= mult;//apply damage type multiplier
        }
        Random rand = new Random();
        boolean crit = attacker.statLuk + attack.baseCrit - defender.statLuk > rand.nextInt(100);
        if(crit){ finalDam *= 2; }//apply crit modifier
        if(attacker.statusEffects.containsKey(StatusEffect.type.POWER)){
            finalDam *= (1 + attacker.statusEffects.get(StatusEffect.type.POWER).magnitude / 100.0);//apply status effect multiplier
        }
        int finalDef = (attack.magic ? defender.statRes : defender.statDef) / 2;//base reduction
        //TODO apply equipment defense
        finalDam -= finalDef;
        if(defender.statusEffects.containsKey(StatusEffect.type.DEFENSE)){
            finalDam *= (1 - defender.statusEffects.get(StatusEffect.type.DEFENSE).magnitude / 100.0);//apply status effect multiplier
        }
        defender.inflictDamage((int)finalDam);
        onHit(attacker, defender, attack, (int)finalDam, crit);
        return true;
    }

    /**
     * Determines whether an attack hits based on stats and a randomly generated value.
     * @param attacker initiator of the attack
     * @param defender target of the attack
     * @param attackAcc base accuracy of the attack
     * @return true if the attack lands
     */
    public boolean checkHit (Character attacker, Character defender, int attackAcc){
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
    public void onHit (Character attacker, Character defender, Attack attack, int damage, boolean crit){

    }



}
