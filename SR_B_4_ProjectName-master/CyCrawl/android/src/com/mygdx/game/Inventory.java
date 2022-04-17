package com.mygdx.game;

import com.badlogic.gdx.utils.Array;

/**
 * Created by brytonhayes on 9/10/17.
 * Class for the inventory object containing a character's items
 */

public class Inventory {

    /*
    NEW PARAMS
     */
    private Array<Slot> slots;


    /*
    END NEW PARAMS
     */

    /**
     * Array containing the item objects in this inventory
     */
    public Item[] items;

    /**
     * number of Items this inventory can hold
     */
    public int size;

    /**
     * number of items in this inventory
     */
    public int numItems;

    /**
     * True if the inventory is full, false otherwise
     */
    public Boolean full;

    /**
     * True if the inventory is empty, false otherwise
     */
    public Boolean empty;

    /**
     * Default Constructor
     */
    public Inventory(){
        slots = new Array<Slot>(10);
        for (int i=0; i<10; i++){
            slots.add(new Slot(null));
        }
        /*
        size = 10;
        for(int i = 0; i < size; i++){
            items[i] = null;
        }
        empty = true;
        full = false;
        */
    }

    /**
     * Constructor method
     * @param maxItems maximum number of items this inventory can hold
     */
    public Inventory(int maxItems){
        size = numItems;
        for(int i = 0; i < size; i++){
            items[i] = null;
        }
        empty = true;
        full = false;
    }

    /**
     * Determine if this inventory is full
     * @return true if inventory is full, false otherwise
     */
    public Boolean isFull(){
        return (this.full);
    }

    /**
     * Determine if this inventory is empty
     * @return true if inventory is empty, false otherwise
     */
    public Boolean isEmpty(){
        return (this.empty);
    }

    /**
     * Search this inventory for an item
     * @param item Item to check for within this inventory
     * @return true if this inventory contains the item, false otherwise
     */
    public Boolean contains(Item item){
        for(Slot slot : slots){
            if(slot.getItem() == item){
                return true;
            }
        }
        /*
        for(int i = 0; i < numItems; i++){
            if(items[i] == item){
                return true;
            }
        }
        */
        return false;
    }

    /**
     * Find the index in this inventory where this item is located
     * @param item Item whose index is being requested
     * @return the index in the inventory array where the item is located
     */
    public int itemIndex(Item item){
        if(!this.contains(item)){
            return -1;
        }
        else{
            int index = -1;
            for(int i = 0; i < numItems; i++){
                if(items[i] == item){
                    index = i;
                }
            }
            return index;
        }
    }

    /**
     * Get the item located at a specified index in the inventory
     * @param index location in the array containing the requested item
     * @return the Item object located at the specified index (possibly null)
     */
    public Item getItemAt(int index){
        return items[index];
    }

    /**
     * Add an item to this inventory
     * @param item item object to be added to the inventory
     * @return true if successful, false otherwise
     */
    public Boolean add(Item item){
        if(this.full){
            return false;
        }
        for(Slot slot : slots){
            if(slot.getItem() == null){
                slot.add(item);
                return true;
            }
        }
        /*
        else{
            items[numItems] = item;
            numItems++;
            if(this.empty){
                this.empty = false;
            }
            if(this.numItems == this.size){
                full = true;
            }
        }
        */
        return false;
    }

    /**
     * Remove an item(if it exists) from the inventory. Shifts
     * all other items down the array
     * @param item item to be removed
     * @return true if successful, false otherwise
     */
    public Boolean remove(Item item){
        if(this.empty || (!this.contains(item))){
            return false;
        }
        else{
            for(int i = 0; i < numItems; i++){
                if(items[i] == item){
                    items[i] = null;
                    for(int j = i; j < numItems - 1; j++){
                        items[j] = items[j + 1];
                    }
                }
            }
        }
        return true;
    }

    /**
     * Replace an item in your inventory with a new item
     * @param oldItem item to be replaced
     * @param newItem item to place in inventory
     * @return true if successful, false otherwise
     */
    public Boolean replace(Item oldItem, Item newItem){
        if(this.empty || !this.contains(oldItem)){
            return false;
        }
        else{
            for(int i = 0; i < numItems; i++){
                if(items[i] == oldItem){
                    items[i] = newItem;
                }
            }
        }
        return true;
    }

    /*
    NEW METHODS
     */
    public Array<Slot> getSlots(){
        return slots;
    }


}
