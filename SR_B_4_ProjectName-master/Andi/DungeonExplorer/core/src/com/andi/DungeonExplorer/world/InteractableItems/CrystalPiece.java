package com.andi.DungeonExplorer.world.InteractableItems;

import com.andi.DungeonExplorer.world.WorldObject;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

/**
 * Created by Andi Li on 11/8/2017.
 */

public class CrystalPiece extends WorldObject {
    private int crystalId;

    public CrystalPiece(int x, int y, TextureRegion texture, float sizeX, float sizeY, GridPoint2 tile, boolean grabbable, int id) {
        super("crystal_piece",x, y,true,texture, sizeX, sizeY, tile, true);
        crystalId = id;
    }
    public int getCrystalId() {
        return crystalId;
    }
    @Override
    public String toString() {
        return "CrystalPiece";
    }

}
