package com.andi.DungeonExplorer.world.Equipment;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

/**
 * Created by Kenneth on 10/25/2017.
 * Example leg equipment - use this as a reference for equips with default stats
 */

public class LeatherBoots extends Equipment {

    int[] defaultStats = new int[]{0,8,0,0,0,0,0,5,0,0};//def 8, spd 5

    /**
     * constructor granting full control
     * @param x
     * @param y
     * @param walkable
     * @param texture
     * @param sizeX
     * @param sizeY
     * @param tile
     * @param grabbable
     * @param slot what equipment slot this should use, probably legs
     * @param statArr custom stats for this equipment
     */
    public LeatherBoots(int x, int y, boolean walkable, TextureRegion texture, float sizeX, float sizeY, GridPoint2 tile, boolean grabbable, String slot, int[] statArr, String name){
        super(x, y,  walkable, texture, sizeX, sizeY, tile, grabbable, slot, statArr, name);
    }

    /**
     * standard constructor with default slot and stats
     * @param x
     * @param y
     * @param walkable
     * @param texture
     * @param sizeX
     * @param sizeY
     * @param tile
     * @param grabbable
     */
    public LeatherBoots(int x, int y, boolean walkable, TextureRegion texture, float sizeX, float sizeY, GridPoint2 tile, boolean grabbable){
        super(x, y, walkable, texture, sizeX, sizeY, tile, grabbable, "Legs", new int[10], "Leather Boots");
        setStatsFromArray(defaultStats);
    }
}
