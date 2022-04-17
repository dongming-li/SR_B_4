package com.mygdx.game;

import Hayes.CyCrawler.Player;

import static sr_b_4.cycrawl.MovingPlatform.dir.DOWN;
import static sr_b_4.cycrawl.MovingPlatform.dir.LEFT;
import static sr_b_4.cycrawl.MovingPlatform.dir.RIGHT;
import static sr_b_4.cycrawl.MovingPlatform.dir.UP;

/**
 * Created by Kenneth on 9/13/2017.
 * Tile only temporarily - should be implemented as a character instead.
 * Methods can transfer over for the most part.
 */

public class MovingPlatform extends Tile {
    public boolean leftRight;
    public boolean upDown;
    public boolean active;
    /**
     * After every tile moved, set to true. Used to run checks, change directions, etc.
     */
    public boolean doneMoving;
    public dir movingDir;

    /**
     * enumerator type for directions
     * Bryton wrote this, copied over
     */
    public enum dir{
        UP, DOWN, LEFT, RIGHT
    }

    /**
     *
     * @param x position, match array
     * @param y position, match array
     * @param grid the grid to which this tile belongs
     * @param switchID switches with a matching ID will activate this tile
     * @param leftRight whether the platform should try to move left/right
     * @param upDown whether this platform should try to move up/down
     * @param active whether the platform should start moving immediately
     * @param movingDir the direction the platform should move first
     */
    public MovingPlatform(int x, int y, TileGrid grid, int switchID, boolean leftRight, boolean upDown, boolean active, dir movingDir){
        this.x = x;
        this.y = y;
        this.grid = grid;
        this.switchID = switchID;
        this.leftRight = leftRight;
        this.upDown = upDown;
        this.active = active;
    }

    public Boolean checkDir(dir direction){
        if (direction == dir.UP){x--;}
        if (direction == dir.DOWN) {x++;}
        if (direction == dir.LEFT){y--;}
        if (direction == dir.RIGHT){y++;}
        if(grid.getTile(x, y) == null || grid.getTile(x, y).getClass() != Gap.class){
            return false;
        }
        return true;
    }
    public dir tryDirs(){
        dir tempDir = movingDir;
        for(int i=0; i<4; i++){
            if(tempDir== dir.UP){tempDir = dir.RIGHT;}
            else if(tempDir== dir.RIGHT){tempDir = dir.DOWN;}
            else if(tempDir== dir.LEFT){tempDir = dir.LEFT;}
            else {tempDir = dir.UP;}
            if (checkDir(tempDir)){
                return  tempDir;
            }
        }
        return null;
    }

    public void Move(){

    }

    public void Update(){
        if(doneMoving && active){
            if(checkDir(movingDir)){
                //continue moving
            }
            else{
                dir tempDir = tryDirs();
                if(tempDir == null){
                    active = false;
                    return;
                }
                else{
                    movingDir = tempDir;
                }
            }
        }
    }

}
