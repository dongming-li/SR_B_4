package com.andi.DungeonExplorer.world.SpecialTiles;

import com.andi.DungeonExplorer.map.DIRECTION;
import com.andi.DungeonExplorer.map.TERRAIN;
import com.andi.DungeonExplorer.map.Tile;
import com.andi.DungeonExplorer.actor.PlayerActor;
import com.andi.DungeonExplorer.world.InteractableItems.Door;
import com.andi.DungeonExplorer.world.events.ActorVisibilityEvent;
import com.andi.DungeonExplorer.world.events.ActorWalkEvent;
import com.andi.DungeonExplorer.world.events.ChangeWorldEvent;
import com.andi.DungeonExplorer.world.events.CutsceneEventQueuer;
import com.andi.DungeonExplorer.world.events.CutscenePlayer;
import com.andi.DungeonExplorer.world.events.DoorEvent;
import com.andi.DungeonExplorer.world.events.WaitEvent;
import com.badlogic.gdx.graphics.Color;
import com.andi.DungeonExplorer.actor.Actor;


public class TeleportTileDoor extends Tile {
	
	private CutscenePlayer player;
	private CutsceneEventQueuer broadcaster;
	
	/* destination */
	private String worldName;
	private int x, y;
	private DIRECTION facing;
	
	/* transition color */
	private Color color;


	public TeleportTileDoor(TERRAIN terrain, CutscenePlayer player, CutsceneEventQueuer broadcaster, String worldName, int x, int y, DIRECTION facing, Color color) {
		super("teleportTileDoor",terrain);
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
				if (targetWorld.getMap().getTile(x, y).getObject() instanceof Door) {
					Door door = (Door)targetWorld.getMap().getTile(x, y).getObject();
					broadcaster.queueEvent(new ActorVisibilityEvent(a, true));
					broadcaster.queueEvent(new ChangeWorldEvent(worldName, x, y, facing, color));
					broadcaster.queueEvent(new DoorEvent(door, true));
					broadcaster.queueEvent(new WaitEvent(0.2f));
					broadcaster.queueEvent(new ActorVisibilityEvent(a, false));
					broadcaster.queueEvent(new WaitEvent(0.2f));
					broadcaster.queueEvent(new ActorWalkEvent(a, DIRECTION.SOUTH));
					broadcaster.queueEvent(new DoorEvent(door, false));
					//door.setWalkable(false);//maybe remove
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
				if (this.getObject() instanceof Door) {
					Door door = (Door)this.getObject();
					broadcaster.queueEvent(new DoorEvent(door, true));
					broadcaster.queueEvent(new ActorWalkEvent(a,DIRECTION.NORTH));
					broadcaster.queueEvent(new ActorVisibilityEvent(a, true));
					broadcaster.queueEvent(new DoorEvent(door, false));
					broadcaster.queueEvent(new ChangeWorldEvent(worldName, x, y, facing, color));
					broadcaster.queueEvent(new ActorVisibilityEvent(a, false));
					broadcaster.queueEvent(new ActorWalkEvent(a,DIRECTION.NORTH));

					return false;
				}
			}
		}
		return true;
	}
}
