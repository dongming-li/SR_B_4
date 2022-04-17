package com.mygdx.game;

/**
 * Created by Kenneth on 9/5/2017.
 * Switch tiles remotely activate other tiles, such as doors or traps.
 */

public class Switch extends Tile {
    public boolean isPressed = false;
    public boolean stayPressed = false;
    public boolean toggleOnRelease = false;
    public int id = 1;
    public int delayActivation = 0;//no effect at 0
    public int delayReactivation = 0;//no effect at 0
    /**
     * remaining time before first activation, ticks down
     */
    public int delayActivationTimer = -1;
    /**
     * remaining time before second activation, ticks down
     */
    public int delayReactivationTimer = -1;

    /**
     * @param x x position, match array
     * @param y y position, match array
     * Default constructor
     */
    public Switch(int x, int y){

    }

    /**
     * Simple constructor
     * @param x x position, match array
     * @param y y position, match array
     * @param stayPressed whether the switch should remain pressed
     * @param toggleOnRelease whether the switch should activate again when it is released
     *                        (e.g. when the player moves off of it)
     * @param ID the ID associated with this switch. When activated, the switch activates
     *           all tiles that share the switch ID.
     */
    public Switch(int x, int y, boolean stayPressed, boolean toggleOnRelease, int ID){
        this.stayPressed = stayPressed;
        this.toggleOnRelease = toggleOnRelease;
        id = ID;
        this.x = x;
        this.y = y;
        //this.grid = grid;
    }

    /**
     * Full constructor
     * @param x x position, match array
     * @param y y position, match array
     * @param stayPressed whether the switch should remain pressed
     * @param toggleOnRelease whether the switch should activate again when it is released
     *                        (e.g. when the player moves off of it)
     * @param ID the ID associated with this switch. When activated, the switch activates
     *           all tiles that share the switch ID.
     * @param delayActivation units of time between press and activation. Value of 0
     *                        is default, no delay.
     * @param delayReactivation units of time between first and second activation. Value of 0
     *                          is default, no reactivation at all. Setting this above 0 will
     *                          add a secondary delayed activation.
     */
    public Switch(int x, int y, boolean stayPressed, boolean toggleOnRelease, int ID, int delayActivation, int delayReactivation){
        this.stayPressed = stayPressed;
        this.toggleOnRelease = toggleOnRelease;
        id = ID;
        this.delayActivation = delayActivation;
        this.delayReactivation = delayReactivation;
        this.x = x;
        this.y = y;
        //this.grid = grid;
    }

    public void Press(boolean allGrids){
        if(isPressed){
            return;
        }
        isPressed = true;
        Activate();
    }
/*
    public void Activate() {
        //iterate through grid/grids and activate matching tiles
        Tile[][] tileArray = grid.getTileArray();
        for(int i=0; i<tileArray.length; i++){
            for(int j=0; j<tileArray[0].length; j++){
                if(id == tileArray[i][j].switchID){
                    tileArray[i][j].Activate();
                }
            }
        }
    }*/

    /**
     * Called regularly each tick.
     */
    public void Update(){
        if(delayActivationTimer > 0){
            delayActivationTimer--;
            if (delayActivationTimer == 0){
                delayActivationTimer--;
                Activate();
            }
        }
        if(delayReactivationTimer > 0){
            delayReactivationTimer--;
            if (delayReactivationTimer == 0){
                delayReactivationTimer--;
                Activate();
            }
        }
    }
}
