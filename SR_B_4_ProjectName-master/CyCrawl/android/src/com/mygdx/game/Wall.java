package com.mygdx.game;

/**
 * Created by Kenneth on 9/5/2017.
 * Wall encompasses all basic impassable tiles.
 */

public class Wall extends Tile {
    /**
     *
     * @param x x position, match array
     * @param y y position, match array
     */
    public Wall(int x, int y){
        this.x = x;
        this.y = y;
        passable = false;
    }
}
