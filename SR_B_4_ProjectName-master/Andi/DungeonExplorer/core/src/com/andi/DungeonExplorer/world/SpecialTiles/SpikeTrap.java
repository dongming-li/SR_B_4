package com.andi.DungeonExplorer.world.SpecialTiles;


import com.andi.DungeonExplorer.map.TERRAIN;
import com.andi.DungeonExplorer.map.Tile;
import com.andi.DungeonExplorer.actor.Actor;
import com.andi.DungeonExplorer.actor.PlayerActor;
import com.andi.DungeonExplorer.world.InteractableItems.SpikeTrapObject;
import com.andi.DungeonExplorer.world.events.CutsceneEventQueuer;
import com.andi.DungeonExplorer.world.events.CutscenePlayer;
import com.andi.DungeonExplorer.world.events.SpikeEvent;


/**
 * Created by Kenneth on 9/10/2017.
 */


public class SpikeTrap extends Tile {

    private CutscenePlayer player;
    private CutsceneEventQueuer broadcaster;
    private int x,y;
    /* destination */





    public SpikeTrap(TERRAIN terrain, CutscenePlayer player, CutsceneEventQueuer broadcaster, int x, int y) {
        super("spikeTrap",terrain);
        this.x = x;
        this.y = y;
        this.player = player;
        this.broadcaster = broadcaster;
    }
    @Override
    public void actorStep(Actor a) {
        if (a instanceof PlayerActor) {
            if (this.getObject()!= null) {
                if (this.getObject() instanceof SpikeTrapObject) {
                    SpikeTrapObject trap = (SpikeTrapObject)this.getObject();
                    broadcaster.queueEvent(new SpikeEvent(trap,true));
                    a.character.inflictDamage(200);
                    a.move(a.getFacing());
                    broadcaster.queueEvent(new SpikeEvent(trap,false));
                }
            }
        }
    }



}
