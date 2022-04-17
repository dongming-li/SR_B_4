package com.andi.DungeonExplorer.world.SpecialTiles;

import com.andi.DungeonExplorer.map.DIRECTION;
import com.andi.DungeonExplorer.map.TERRAIN;
import com.andi.DungeonExplorer.map.Tile;
import com.andi.DungeonExplorer.actor.Actor;
import com.andi.DungeonExplorer.actor.PlayerActor;
import com.andi.DungeonExplorer.world.InteractableItems.Portal;
import com.andi.DungeonExplorer.world.events.ActorVisibilityEvent;
import com.andi.DungeonExplorer.world.events.ChangeWorldEvent;
import com.andi.DungeonExplorer.world.events.CutsceneEventQueuer;
import com.andi.DungeonExplorer.world.events.CutscenePlayer;
import com.andi.DungeonExplorer.world.events.WaitEvent;
import com.badlogic.gdx.graphics.Color;

/**
 * Created by Andi Li on 10/15/2017.
 */

public class TeleportTilePortal extends Tile {
    private CutscenePlayer player;
    private CutsceneEventQueuer broadcaster;

    /* destination */
    private String worldName;
    private int x, y;
    private DIRECTION facing;

    /* transition color */
    private Color color;


    public TeleportTilePortal(TERRAIN terrain, CutscenePlayer player, CutsceneEventQueuer broadcaster, String worldName, int x, int y, DIRECTION facing, Color color) {
        super("teleportTilePortal",terrain);
        this.worldName = worldName;
        this.x= x;
        this.y=y;
        this.facing=facing;
        this.color=color;
        this.player = player;
        this.broadcaster = broadcaster;

    }
    @Override
    public void actorStep(Actor a) {
        if (a instanceof PlayerActor) {
            com.andi.DungeonExplorer.world.World targetWorld = player.getWorld(worldName);
            if (targetWorld.getMap().getTile(x, y).getObject() != null) {
                if (targetWorld.getMap().getTile(x, y).getObject() instanceof Portal) {
                    broadcaster.queueEvent(new ActorVisibilityEvent(a, true));
                    broadcaster.queueEvent(new ChangeWorldEvent(worldName, x, y, facing, color));
                    broadcaster.queueEvent(new WaitEvent(0.2f));
                    broadcaster.queueEvent(new ActorVisibilityEvent(a, false));
                    broadcaster.queueEvent(new WaitEvent(0.2f));


                }
            } else {
                broadcaster.queueEvent(new ChangeWorldEvent(worldName, x, y, facing, color));
            }
        }
    }

    @Override
    public boolean actorBeforeStep(Actor a) {
        if (a instanceof PlayerActor) {
            if (this.getObject() != null) {
                if (this.getObject() instanceof Portal) {
                    broadcaster.queueEvent(new ActorVisibilityEvent(a, true));
                    broadcaster.queueEvent(new ChangeWorldEvent(worldName, x, y, facing, color));
                    broadcaster.queueEvent(new ActorVisibilityEvent(a, false));



                    return false;
                }
            }
        }
        return true;
    }
}
