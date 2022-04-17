package com.mygdx.game;

import android.icu.lang.UProperty;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bryton on 9/10/17.
 * Class containing the player object
 */

public class Player extends Character {

    /*
        Constructors
     */

    /**
     * Default Constructor
     */
    public Player(){
        inventory = new Inventory(10);
        xPos = 0;
        yPos = 0;
        maxHealth = 100;
        health = 100;
        facingDir = dir.RIGHT;
        statusEffects = new HashMap<StatusEffect.type, StatusEffect>();
    }

    /**
     * Constructor method
     * @param facing initial direction the player is facing
     * @param maxLife maximum amount of health this player can have
     * @param life initial amount of health the player has
     * @param row row in grid corresponding to the player's starting position
     * @param col column in grid corresponding to the player's starting position
     * @param game the game to which the player belongs
     */
    public Player(dir facing, int maxLife, int life, int row, int col, Inventory inv, Game game){
        inventory = new Inventory(10);
        facingDir = facing;
        maxHealth = maxLife;
        health = life;
        xPos = row;
        yPos = col;
        this.game = game;
        //this.grid = grid;
        statusEffects = new HashMap<StatusEffect.type, StatusEffect>();
    }

    /*
        Inventory management methods
     */

    /**
     * Pick up an item and place it in this players inventory
     * @param item item to be picked up
     * @return True if successful, false otherwise
     */
    public Boolean pickUpItem(com.mygdx.game.Item item){
        if(inventory.isFull()){
            return false;
        }
        else{
            this.inventory.add(item);
        }
        return true;
    }

    /**
     * Drop an item from this players inventory
     * @param item item to be dropped
     * @return true if successful, false otherwise
     */
    public Boolean dropItem(Item item){
        if(inventory.isEmpty()){
            return false;
        }
        else {
            this.inventory.remove(item);
        }
        return true;
    }

    /**
     * Drop an item to the current tile from this players inventory
     * @param item item to be dropped
     * @return true if successful, false otherwise
     */
    /*
    public Boolean dropItemToTile(Item item){
        if(inventory.isEmpty() || grid.getTileArray()[yPos][xPos].item != null ){
            return false;
        }
        else {
            this.inventory.remove(item);
            grid.getTileArray()[yPos][xPos].item = item;
        }
        return true;
    }
*/
    /**
     * Replace an item
     * @param oldItem Item to be replaced
     * @param newItem Item to be placed into inventory
     * @return True if successful, false otherwise
     */
    public Boolean replaceItem(Item oldItem, Item newItem){
        if(inventory.isEmpty() || !inventory.contains(oldItem)){
            return false;
        }
        else{
            inventory.replace(oldItem, newItem);
        }
        return true;
    }

    /*
        Action methods
     */

    /**
     * Use an item in your inventory
     * @param item potion to be used
     * @return True if successful, false otherwise
     */
    public Boolean useItem(Item item){
        if(this.inventory.contains(item)){
            //item.use();
            return true;
        }
        return false;
    }
    

















}
