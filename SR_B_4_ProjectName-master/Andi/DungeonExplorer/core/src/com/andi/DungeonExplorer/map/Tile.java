package com.andi.DungeonExplorer.map;

import com.andi.DungeonExplorer.actor.Actor;
import com.andi.DungeonExplorer.world.InteractableItems.Boulder;
import com.andi.DungeonExplorer.world.WorldObject;
import com.andi.DungeonExplorer.world.World;
import com.andi.DungeonExplorer.world.editor.Index;

/**
 * Tile for the map
 */
public class Tile {

	private String type;
	private TERRAIN terrain;
	private WorldObject object;
	private Boolean hasObject;
	private Actor actor;
	private Index index;	//index used to edit this tile
	public int x;
	public int y;
	
	private boolean walkable = true;

	@Override
	public String toString() {
		return type;
	}

	public String oldtoString() {
		return "Tile{" +
				"terrain=" + terrain +
				", object=" + object +
				", actor=" + actor +
				", walkable=" + walkable +
				'}';
	}

	public Tile(){}
	public Tile(String type, TERRAIN terrain) {
		this.type = type;
		this.terrain = terrain;
		this.object = null;
	}
	
	public Tile(String type, TERRAIN terrain, boolean walkable) {
		this.type = type;
		this.terrain = terrain;
		this.walkable = walkable;
		this.object = null;
	}

	public String getType() { return type; }

	public TERRAIN getTerrain() {
		return terrain;
	}

	public Actor getActor() {
		return actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}

	public Index getIndex() {
		return index;
	}

	public void setIndex(Index index) {
		this.index = index;
	}

	public WorldObject getObject() {
		return object;
	}

	public Boolean hasObject() {
		return hasObject;
	}

	public void setObject(WorldObject object) {

		this.object = object;
		if(object != null){
			hasObject = true;
		}
		else{
			hasObject = false;
		}
	}


	public boolean walkable() {
		return walkable;
	}
	
	/**
	 * Fires when an Actor steps on the Tile. Called when the Actor is just about finished with his/her step.
	 */
	public void actorStep(Actor a) {
		
	}


	public boolean actorBeforeStep(Actor a) {
		return true;
	}

	public void setWalkable(boolean walkable) {
		this.walkable = walkable;
	}

	public void boulderActivate(Boulder a){

	}

	/**
	 * activate the tile with a boulder if its special
	 * @param a
	 */
	public void boulderDeactivate(Boulder a){
		World targetWorld = a.getWorld();
		for(int i = 0;i<targetWorld.getMap().getWidth();i++){
			for(int j = 0;j<targetWorld.getMap().getHeight();j++){
				WorldObject obj = targetWorld.getMap().getTile(i,j).getObject();

				obj.boulderDeactivate();

			}
		}
	}

	/**
	 * sets terrain of the object
	 * @param terrain
	 */
	public void setTerrain(TERRAIN terrain){
		this.terrain = terrain;
	}

}
