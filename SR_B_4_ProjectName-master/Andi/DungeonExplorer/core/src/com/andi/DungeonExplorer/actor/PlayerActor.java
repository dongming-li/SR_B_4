package com.andi.DungeonExplorer.actor;

import com.andi.DungeonExplorer.world.World;
import com.andi.DungeonExplorer.util.AnimationSet;

/**
 * Created by Andi Li on 10/18/2017.
 * THE USER's ACTOR
 */

public class PlayerActor extends Actor {
	private com.andi.DungeonExplorer.actor.Inventory.Inventory inv;


	public PlayerActor(World world, int x, int y, AnimationSet animations) {
		super(world, x, y, animations);
		isPlayer = true;
	}

}
