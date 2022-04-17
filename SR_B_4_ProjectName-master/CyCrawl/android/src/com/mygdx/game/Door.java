package com.mygdx.game;



/**
 * Created by Kenneth on 9/5/2017.
 * Door tiles are impassable when closed, but passable when open.
 * Doors can be opened with keys or toggled by activating a connected switch.
 */

public class Door extends Tile {

    public boolean isOpen;
    public int keyID = -1;

    /**
     *
     * @param open true if the door should be open initially
     * @param switchType if a switch of the same id is activated, the door is toggled
     * @param keyType if the player has a key of the same type,
     *                the door will be opened when the player
     *                attempts to move into its tile.
     */
    public Door(boolean open, int switchType, int keyType, TileGrid grid){
        isOpen = open;
        passable = open;
        switchID = switchType;
        keyID = keyType;
        this.grid = grid;
    }

    /**
     * Default constructor
     */
    public Door(){
        isOpen = false;
        passable = false;
        keyID = 1;
    }

    /**
     * Sets the door to open state, allowing passage
     */
    public void Open(){
        isOpen = true;
        passable = true;
        //set texture to open ver
    }

    /**
     * Sets the door to closed state, barring passage
     */
    public void Close(){
        isOpen = false;
        passable = false;
        //set texture to closed ver
    }

    /**
     * Close the door if it's open, or vice versa
     */
    public void toggle(){
        if(isOpen){
            isOpen = false;
            passable = false;
            //set texture to closed ver
        }
        else {
            isOpen = true;
            passable = true;
            //set texture to open ver
        }
    }
    @Override
    public void Activate(){
        toggle();
    }
}
