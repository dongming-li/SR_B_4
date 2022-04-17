package com.mygdx.game;

import com.badlogic.gdx.utils.Array;

/**
 * Created by Kenneth on 10/1/2017.
 */

public class Slot {

    /**
     * Item contained in the slot
     */
    private Item item;

    private Array<SlotChanger> slotChangers = new Array<SlotChanger>();

    public Slot(Item item){
        this.item = item;
    }

    public boolean add(Item item){
        if(this.item == null){
            this.item = item;
            talkToChangers();
            return true;
        }
        return false;
    }

    public void remove(){
        item = null;
        talkToChangers();
    }

    /*
    changer methods
     */
    public void addChanger (SlotChanger slotChanger){
        slotChangers.add(slotChanger);
    }

    public void removeChanger (SlotChanger slotChanger){
        slotChangers.removeValue(slotChanger, true);
    }

    public void talkToChangers(){
        for(SlotChanger changer : slotChangers){
            changer.changed(this);
        }
    }

    public Item getItem(){
        return item;
    }
}
