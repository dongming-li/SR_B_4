package com.andi.DungeonExplorer.world.Equipment;


import com.andi.DungeonExplorer.world.WorldObject;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

/**
 * Created by Andi Li on 10/3/2017.
 */

public class Key extends WorldObject {
    private int keyId;
    private String name;


    public Key(int x, int y,TextureRegion texture,int id,String name) {
        super("key",x, y,true,texture, 1f, 1f, new GridPoint2(0,0), true);
        keyId = id;
        this.name = name;
    }


    public int getKeyId() {
        return keyId;
    }


    @Override
    public String toString() {
        return name;
    }
}
