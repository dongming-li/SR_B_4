package com.andi.DungeonExplorer.util;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.andi.DungeonExplorer.map.DIRECTION;

/**
 * A set of TextureRegions and Animations that can be applied to an Actor.
 */
public class AnimationSet {
	
	private Map<DIRECTION, Animation> walking;
	private Map<DIRECTION, TextureRegion> standing;
	private Map<DIRECTION, TextureRegion> dying;
	
	public AnimationSet(Animation walkNorth, 
			Animation walkSouth, 
			Animation walkEast, 
			Animation walkWest, 
			TextureRegion standNorth, 
			TextureRegion standSouth, 
			TextureRegion standEast, 
			TextureRegion standWest,
						TextureRegion dieNorth,
						TextureRegion dieSouth,
						TextureRegion dieEast,
						TextureRegion dieWest) {
		walking = new HashMap<DIRECTION, Animation>();
		walking.put(DIRECTION.NORTH, walkNorth);
		walking.put(DIRECTION.SOUTH, walkSouth);
		walking.put(DIRECTION.EAST, walkEast);
		walking.put(DIRECTION.WEST, walkWest);
		standing = new HashMap<DIRECTION, TextureRegion>();
		standing.put(DIRECTION.NORTH, standNorth);
		standing.put(DIRECTION.SOUTH, standSouth);
		standing.put(DIRECTION.EAST, standEast);
		standing.put(DIRECTION.WEST, standWest);
		dying = new HashMap<DIRECTION, TextureRegion>();
		dying.put(DIRECTION.NORTH, dieNorth);
		dying.put(DIRECTION.SOUTH, dieSouth);
		dying.put(DIRECTION.EAST, dieEast);
		dying.put(DIRECTION.WEST, dieWest);
	}
	
	public Animation getWalking(DIRECTION dir) {
		return walking.get(dir);
	}
	
	public TextureRegion getStanding(DIRECTION dir) {
		return standing.get(dir);
	}
	public TextureRegion getDying(DIRECTION dir){
		return dying.get(dir);
	}

}
