package com.mygdx.game;


/**
 * Created by Kenneth on 9/10/2017.
 */

public class SpikeTrap extends Tile {
    public boolean spikesUp;
    public int damage = 1;
    public int autoDelay1 = 0;
    public int autoDelay2 = 0;
    public int delayCount = -1;

    /**
     * Simple constructor
     * @param x x position, match array
     * @param y y position, match array
     * @param up whether the spikes should be up or down to begin with
     * @param dam damage value if a character is harmed by spikes
     * @param switchID id of switches that will activate this tile
     */
    public SpikeTrap (int x, int y, boolean up, int dam, int switchID, TileGrid grid){
        this.x = x;
        this.y = y;
        spikesUp = up;
        damage = dam;
        this.switchID = switchID;
        this.grid = grid;
    }

    /**
     * Simple constructor
     * @param x x position, match array
     * @param y y position, match array
     * @param up whether the spikes should be up or down to begin with
     * @param dam damage value if a character is harmed by spikes
     * @param switchID id of switches that will activate this tile
     * @param autoDelay1 if 0, no effect. Otherwise, number of ticks between automatic activations
     * @param autoDelay2 if 0, or autoDelay1 is 0, no effect. Otherwise, number of ticks between activations alternates between this and autoDelay1.
     */
    public SpikeTrap (int x, int y, boolean up, int dam, int switchID, int autoDelay1, int autoDelay2, TileGrid grid){
        this.x = x;
        this.y = y;
        spikesUp = up;
        damage = dam;
        this.switchID = switchID;
        this.autoDelay1 = autoDelay1;
        this.autoDelay2 = autoDelay2;
        this.delayCount = (autoDelay1 > 0) ? autoDelay1 : -1;
        this.grid = grid;
    }

    @Override
    public void Activate() {
        if(spikesUp){
            spikesUp = false;
        }
        else{
            spikesUp = true;
            Character c = WhoIsOnMe();
            if(c!=null){
                //damage character
            }
        }
    }

    /**
     * count down, activate at 0, and reset count
     */
    public void Update(){
        if(delayCount > 0){
            delayCount--;
        }
        if(delayCount==0){
            Activate();
            //use second delay instead
            if(spikesUp && autoDelay2 > 0){
                delayCount = autoDelay2;
            }
            else{
                delayCount = autoDelay1;
            }
        }
    }
}