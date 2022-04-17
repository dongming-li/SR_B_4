package com.andi.DungeonExplorer.world.SpecialTiles;

import com.andi.DungeonExplorer.map.TERRAIN;
import com.andi.DungeonExplorer.map.Tile;
import com.andi.DungeonExplorer.actor.Actor;
import com.andi.DungeonExplorer.actor.PlayerActor;

/**
 * Created by Andi Li on 11/9/2017.
 */

public class Teleporter extends Tile{
    private int newX;
    private int newY;
    public Teleporter(TERRAIN terrain,int newX, int newY) {
        super("teleporter",terrain);
        this.newX = newX;
        this.newY = newY;
    }

    @Override
    public void actorStep(Actor a) {
        if (a instanceof PlayerActor) {
            a.teleport(newX,newY);
            a.getWorld().getMap().getTile(newX,newY).setActor(a);
            this.setActor(null);
        }
    }

}
