package com.mygdx.game;

/**
 * Created by Andi Li on 9/10/2017.
 */

public abstract class Item {
    /**
     * The grid to which this item belongs
     */
    //public TileGrid grid;

    /**
     * The character to which this item belongs. Null if not
     * picked up yet
     */
    public Character owner;

    /**
     * True if this item will behave as expected. False if
     * broken or already used
     */
    public Boolean usable;

    private String texture;

    public Item(){}
    public Item (String texture){
        this.texture = texture;
    }

    //public abstract void use();
    //public abstract int getItemId();
}
