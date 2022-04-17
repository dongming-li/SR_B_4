package com.andi.DungeonExplorer.world.InteractableItems;

import com.andi.DungeonExplorer.world.WorldObject;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

/**
 * Created by Andi Li on 10/15/2017.
 */

public class Portal extends WorldObject {

    /**
     * creates a portal
     * @param x
     * @param y
     * @param walkable
     * @param texture
     * @param sizeX
     * @param sizeY
     * @param tile
     * @param grabbable
     */
    public Portal(int x, int y, boolean walkable, TextureRegion texture, float sizeX, float sizeY, GridPoint2 tile, boolean grabbable) {
        super("portal",x, y, walkable, texture, sizeX, sizeY, tile, grabbable);
    }

}
