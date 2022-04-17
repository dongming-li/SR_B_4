package com.andi.DungeonExplorer.world.InteractableItems;

import com.andi.DungeonExplorer.world.WorldObject;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

/**
 * Created by Andi Li on 11/21/2017.
 */

public class SnowStairs extends WorldObject{
    private int x;
    private int y;
    public com.andi.DungeonExplorer.world.World world;
    public SnowStairs(int x, int y, TextureRegion texture, int actId, com.andi.DungeonExplorer.world.World world) {
        super("snowStairs",x, y, true, texture, 4f, 3f, new GridPoint2(0,0), true, actId,false);
        this.x = x;
        this.y = y;
        this.world = world;

    }

    /**
     * displays the stairs
     */
    @Override
    public void activate() {
        this.setVisible(true);
        for(int i = x;i<x+4;i++){
            for(int j = y;j<y+3;j++){
                world.getMap().getTile(i,j).setWalkable(true);
            }
        }

    }

}
