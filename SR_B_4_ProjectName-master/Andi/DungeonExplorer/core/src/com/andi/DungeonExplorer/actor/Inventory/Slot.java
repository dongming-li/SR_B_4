package com.andi.DungeonExplorer.actor.Inventory;

import com.andi.DungeonExplorer.world.WorldObject;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Andi Li on 10/4/2017.
 */

public class Slot {

    private WorldObject object;

    private int amount;

    private Array<SlotListener> slotListeners = new Array<SlotListener>();

    public Slot(WorldObject object, int amount) {
        this.object = object;
        this.amount = amount;
    }

    /**
     * adds object to the inventory slot
     * @param item
     * @param amount
     * @return
     */
    public boolean add(WorldObject item, int amount) {
        if (this.object == item || this.object == null) {
            this.object = item;
            this.amount += amount;
            notifyListeners();
            return true;
        }

        return false;
    }

    /**
     * takes an intem out of the inventory
     * @param amount
     * @return
     */
    public boolean take(int amount) {
        if (this.amount >= amount) {
            this.amount -= amount;
            if (this.amount == 0) {
                object = null;
            }
            notifyListeners();
            return true;
        }

        return false;
    }


    private void notifyListeners() {
        for (SlotListener slotListener : slotListeners) {
            slotListener.hasChanged(this);
        }
    }

    /**
     * returns the object in the slot
     * @return
     */
    public WorldObject getObject() {
        return object;
    }

    /**
     * Gets the amount of that object in the slot
     * @return
     */
    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        if(object == null){
            return "";
        }
        else if(amount == 1){
            return "" + object.type;
        }
        return "" + object.type + ":" + amount;
    }
}
