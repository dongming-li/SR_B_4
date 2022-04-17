package com.andi.DungeonExplorer.world.events;

import com.andi.DungeonExplorer.map.DIRECTION;
import com.andi.DungeonExplorer.world.World;
import com.badlogic.gdx.graphics.Color;


/**
 * Created by Andi Li on 10/6/2017.
 */
public interface CutscenePlayer {
	
	/**
	 * Smooth transition to another world.
	 * @param newWorld
	 * @param x
	 * @param y
	 * @param facing
	 * @param color
	 */
	public void changeLocation(World newWorld, int x, int y, DIRECTION facing, Color color);
	
	/**
	 * Get a loaded World from name
	 * @param worldName
	 * @return
	 */
	public World getWorld(String worldName);
}
