package com.mygdx.game;

/**
 * Created by Kenneth on 9/13/2017.
 * Gaps are essentially walls, but have different interactions with
 * a variety of other objects, e.g. projectiles can pass over them.
 */

public class Gap extends Tile {

    /**
     *
     * @param x position, match array
     * @param y position, match array
     * @param grid The grid to which the tile belongs
     */
    public Gap(int x, int y, TileGrid grid){
        this.x = x;
        this.y = y;
        passable = false;
        this.grid = grid;
    }
}
