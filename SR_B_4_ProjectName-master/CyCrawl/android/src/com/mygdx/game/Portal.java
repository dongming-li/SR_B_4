package com.mygdx.game;

/**
 * Created by Kenneth on 9/10/2017.
 */

public class Portal extends Tile {
    public boolean active;
    int destX;
    int destY;

    /**
     * Full constructor
     * @param x x position, match array
     * @param y y position, match array
     * @param destX x position to send player
     * @param destY y position to send player
     * @param switchID id of switches that will activate this tile
     * @param active whether the portal is active, i.e. will move the player
     */
    public Portal (int x, int y, int destX, int destY, int switchID, boolean active, TileGrid grid){
        this.x = x;
        this.y = y;
        this.destX = destX;
        this.destY = destY;
        this.switchID = switchID;
        this.active = active;
        this.grid = grid;
    }

    @Override
    public void Activate() {
        active = !active;
    }

    /*
    public void Teleport(Player player){

    }
    */
}
