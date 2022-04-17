package com.mygdx.game;

/**
 * Created by Kenneth on 9/5/2017.
 * Simple floor tile which can be walked on.
 */

public class Floor extends Tile {
    /**
     * full constructor
     * @param x x position, match array
     * @param y y position, match array
     */
    public Floor(int x, int y, TileGrid grid){
        this.x = x;
        this.y = y;
        passable = true;
        this.grid = grid;
    }
}
