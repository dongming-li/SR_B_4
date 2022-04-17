package com.andi.DungeonExplorer.actor;

import com.andi.DungeonExplorer.controller.DialogueController;
import com.andi.DungeonExplorer.dialogue.Dialogue;
import com.andi.DungeonExplorer.map.DIRECTION;
import com.andi.DungeonExplorer.map.YSortable;

import com.andi.DungeonExplorer.world.World;
import com.andi.DungeonExplorer.world.WorldObject;
import com.andi.DungeonExplorer.multiplayer.MultiplayerHandler;
import com.andi.DungeonExplorer.util.AnimationSet;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Array;

import java.util.List;

import static com.andi.DungeonExplorer.map.DIRECTION.EAST;
import static com.andi.DungeonExplorer.map.DIRECTION.NORTH;
import static com.andi.DungeonExplorer.map.DIRECTION.SOUTH;
import static com.andi.DungeonExplorer.map.DIRECTION.WEST;


/**
 * Created by Andi Li on 10/10/2017.
 * This class is for all NPCS and the PLAYER itself
 */
public class Actor implements YSortable {
	public float sizeX = 1;
	public float sizeY = 1.5f;
	public World world;
	public int x;
	public int y;
	public DIRECTION facing;
	public boolean visible = true;

	public com.andi.DungeonExplorer.actor.Inventory.Inventory inventory;
	public float worldX, worldY;

	/* state specific */
	public int srcX, srcY;
	public int destX, destY;
	public float animTimer;
	public float WALK_TIME_PER_TILE = 0.15f;
	public float REFACE_TIME = 0.1f;
	public boolean noMoveNotifications = false;
	public DialogueController dialogueController;
	public float walkTimer;
	public boolean moveRequestThisFrame;

	public ACTOR_STATE state;

	public AnimationSet animations;
	public TextureRegion sprite;
	public TextureRegion texture;

	public Dialogue dialogue;


    public com.andi.DungeonExplorer.actor.Character.Character character;

	public int inventoryIndex;



	public MultiplayerHandler multiplayerHandler;

    public String name;

    public Boolean isPlayer;



	public Actor(World world, int x, int y, AnimationSet animations) {
		inventory = new com.andi.DungeonExplorer.actor.Inventory.Inventory();
		this.world = world;
		this.x = x;
		this.y = y;
		this.worldX = x;
		this.worldY = y;
		this.animations = animations;
		this.state = ACTOR_STATE.STANDING;
		this.facing = DIRECTION.SOUTH;
		inventoryIndex = 0;
		multiplayerHandler = new MultiplayerHandler();
		isPlayer = false;
	}

	public Actor(World world, int x, int y, TextureRegion texture) {
		inventory = new com.andi.DungeonExplorer.actor.Inventory.Inventory();
		this.world = world;
		this.x = x;
		this.y = y;
		this.worldX = x;
		this.worldY = y;
		this.texture = texture;
		this.state = ACTOR_STATE.STANDING;
		this.facing = DIRECTION.SOUTH;


		inventoryIndex = 0;


		multiplayerHandler = new MultiplayerHandler();


	}

    /**
     * Full constructor for actors that need character objects
     * @param world
     * @param x
     * @param y
     * @param animations
     * @param stats starting statistics for the character, atk through max MP
     * @param level starting level
     * @param growthFactor 1 default, influences stat gains on levelup.
     * @param expValue How much exp to give the player upon death.
     */
    public Actor(World world, int x, int y, AnimationSet animations, int[] stats, int level, float growthFactor, int expValue, String name) {
        inventory = new com.andi.DungeonExplorer.actor.Inventory.Inventory();
        this.world = world;
        this.x = x;
        this.y = y;
        this.worldX = x;
        this.worldY = y;
        this.animations = animations;
        this.state = ACTOR_STATE.STANDING;
        this.facing = DIRECTION.SOUTH;

        this.name = name;
        inventoryIndex = 0;


        multiplayerHandler = new MultiplayerHandler();

        character = new com.andi.DungeonExplorer.actor.Character.Character(stats, level, growthFactor, expValue, this, name);
    }

    /**
     * You also need to get character.toString or (Monster)character.toString for full data
     * @return string representation of this class
     */
    @Override
    public String toString(){
		String string = name + ':' + x + ',' + y + ":" + isPlayer.toString();
		return string;
	}

	public enum ACTOR_STATE {
		WALKING,
		STANDING,
		REFACING,
		DEAD,
		;
	}

	/**
	 * updates the characters position
	 * @param delta
	 */
	public void update(float delta) {
		if (state != ACTOR_STATE.DEAD) {
			if (state == ACTOR_STATE.WALKING) {
				animTimer += delta;
				walkTimer += delta;
				worldX = Interpolation.linear.apply(srcX, destX, animTimer / WALK_TIME_PER_TILE);
				worldY = Interpolation.linear.apply(srcY, destY, animTimer / WALK_TIME_PER_TILE);
				if (animTimer > WALK_TIME_PER_TILE) {
					float leftOverTime = animTimer - WALK_TIME_PER_TILE;
					finishMove();
					if (moveRequestThisFrame) { // keep walking using the same animation time
						if (move(facing)) {
							animTimer += leftOverTime;
							worldX = Interpolation.linear.apply(srcX, destX, animTimer / WALK_TIME_PER_TILE);
							worldY = Interpolation.linear.apply(srcY, destY, animTimer / WALK_TIME_PER_TILE);
						}
					} else {
						walkTimer = 0f;
					}
				}
			} else if (state == ACTOR_STATE.REFACING) {
				animTimer += delta;
				if (animTimer > REFACE_TIME) {
					state = ACTOR_STATE.STANDING;
				}
			}


			moveRequestThisFrame = false;
		}
	}

	/**
	 * reface the character
	 * @param dir
	 * @return
	 */
	public boolean reface(DIRECTION dir) {
		if (state != ACTOR_STATE.STANDING) { // can only reface when standing
			return false;
		}
		if (facing == dir) { // can't reface if we already face a direction
			return true;
		}
		facing = dir;
		state = ACTOR_STATE.REFACING;
		animTimer = 0f;
		return true;
	}

	/**
	 * Changes the Players facing direction, without the walk-frame animation.
	 * This is used when loading maps, and in dialogue.
	 */
	public boolean refaceWithoutAnimation(DIRECTION dir) {
		if (state != ACTOR_STATE.STANDING) { // can only reface when standing
			return false;
		}
		this.facing = dir;
		return true;
	}

	/**
	 * Initializes a move. If you want to move an Actor, use this method.
	 * @param dir	Direction to move
	 * @return		If the move can be performed
	 */
	public boolean move(DIRECTION dir) {
		if (state != ACTOR_STATE.DEAD) {
			multiplayerHandler.postPlayerPosition(x, y);
			if (state == ACTOR_STATE.WALKING) {
				if (facing == dir) {
					moveRequestThisFrame = true;
				}
				return false;
			}

			// edge of world test
			if (x + dir.getDX() >= world.getMap().getWidth() || x + dir.getDX() < 0 || y + dir.getDY() >= world.getMap().getHeight() || y + dir.getDY() < 0) {
				reface(dir);
				return false;
			}
			// unwalkable tile test
			if (!world.getMap().getTile(x + dir.getDX(), y + dir.getDY()).walkable()) {
				reface(dir);
				return false;
			}
			if(world.getMap().getTile(x + dir.getDX(), y + dir.getDY()).getActor() != null &&
					world.getMap().getTile(x + dir.getDX(), y + dir.getDY()).getActor().getState() == ACTOR_STATE.DEAD){
				initializeMove(dir);
				world.getMap().getTile(x, y).setActor(null);
				x += dir.getDX();
				y += dir.getDY();
				world.getMap().getTile(x, y).setActor(this);
				return true;

			}
			// actor test
			if (world.getMap().getTile(x + dir.getDX(), y + dir.getDY()).getActor() != null) {
				reface(dir);
				return false;
			}
			// object test
			if (world.getMap().getTile(x + dir.getDX(), y + dir.getDY()).getObject() != null) {
				WorldObject o = world.getMap().getTile(x + dir.getDX(), y + dir.getDY()).getObject();
				if (!o.isWalkable()) {
					reface(dir);
					return false;
				}
			}
			if (world.getMap().getTile(x + dir.getDX(), y + dir.getDY()).actorBeforeStep(this) == true) {

					initializeMove(dir);
					world.getMap().getTile(x, y).setActor(null);
					x += dir.getDX();
					y += dir.getDY();
					world.getMap().getTile(x, y).setActor(this);
					return true;





			}
			return false;
		}
		return false;
	}

	/**
	 * Same as move() but doesn't notify receiving Tile.
	 */
	public boolean moveWithoutNotifications(DIRECTION dir) {
		noMoveNotifications = true;
		if (state == ACTOR_STATE.WALKING) {
			if (facing == dir) {
				moveRequestThisFrame = true;
			}
			return false;
		}
		// edge of world test
		if (x+dir.getDX() >= world.getMap().getWidth() || x+dir.getDX() < 0 || y+dir.getDY() >= world.getMap().getHeight() || y+dir.getDY() < 0) {
			reface(dir);
			return false;
		}
		// unwalkable tile test
		if (!world.getMap().getTile(x+dir.getDX(), y+dir.getDY()).walkable()) {
			reface(dir);
			return false;
		}
		// actor test
		if (world.getMap().getTile(x+dir.getDX(), y+dir.getDY()).getActor() != null) {
			reface(dir);
			return false;
		}
		// object test
		if (world.getMap().getTile(x+dir.getDX(), y+dir.getDY()).getObject() != null) {
			WorldObject o = world.getMap().getTile(x+dir.getDX(), y+dir.getDY()).getObject();
			if (!o.isWalkable()) {
				reface(dir);
				return false;
			}
		}
		initializeMove(dir);
		world.getMap().getTile(x, y).setActor(null);
		x += dir.getDX();
		y += dir.getDY();
		world.getMap().getTile(x, y).setActor(this);
		return true;
	}

	/**
	 * Stars a move
	 * @param dir
	 */
	public void initializeMove(DIRECTION dir) {
		this.facing = dir;
		this.srcX = x;
		this.srcY = y;
		this.destX = x+dir.getDX();
		this.destY = y+dir.getDY();
		this.worldX = x;
		this.worldY = y;
		animTimer = 0f;
		state = ACTOR_STATE.WALKING;
	}

	/**
	 * checks the end of the step
	 */
	public void finishMove() {
		state = ACTOR_STATE.STANDING;
		this.worldX = destX;
		this.worldY = destY;
		this.srcX = 0;
		this.srcY = 0;
		this.destX = 0;
		this.destY = 0;
		if (!noMoveNotifications) {
			world.getMap().getTile(x, y).actorStep(this);
		} else {
			noMoveNotifications = false;
		}

	}

	/**
	 * Changes the Players position internally.
	 */
	public void teleport(int x, int y) {
		int oldx = this.x;
		int oldy = this.y;
		this.x = x;
		this.y = y;
		this.worldX = x;
		this.worldY = y;
		//world.getMap().getTile(oldx,oldy).setActor(null);
	}
	public void addObject(WorldObject obj){
		inventory.store(obj,1);
	}
	public void grabObject(){
		int x = getX();
		int y = getY();
		System.out.println("x: "+ x +"y: "+y);
		if(world.getMap().getTile(x,y).getObject() != null && world.getMap().getTile(x,y).getObject().isGrabbable() == true){
			System.out.println("insert object into inventory");
			inventory.store(world.getMap().getTile(x,y).getObject(),1);
			System.out.println(inventory.getSlots());
			world.getMap().getTile(x,y).setObject(null);
		}


	}

	/**
	 * drops the object in the selected inventory spot
	 */
	public void dropObject(){
		System.out.println(inventory.getSlots());
		if(inventory.isNotEmpty()){
			int x = this.getX();
			int y = this.getY();
			System.out.println("x: "+ x +"y: "+y);
			if(world.getMap().getTile(x,y).getObject() == null){
				Array<com.andi.DungeonExplorer.actor.Inventory.Slot> slots = inventory.getSlots();
					WorldObject dropObject = slots.get(inventoryIndex).getObject();
						System.out.print(dropObject);
						dropObject.setX(x);
						dropObject.setY(y);
						world.getMap().getTile(x,y).setObject(dropObject);
						inventory.drop(dropObject);
						for(int i =0;i<slots.size;i++){
							if(slots.get(i).getObject()!=null){
								inventoryIndex = i;
								break;
							}
							else{
								inventoryIndex = 0;
							}
						}


				}
		}

	}

	/**
	 * interact with the object
	 * @return
	 */
	public WorldObject interact(){
		int x = this.getX();
		int y = this.getY();
		if(facing == DIRECTION.EAST && x != world.getMap().getWidth()-1){
			WorldObject Object = world.getMap().getTile(x+1,y).getObject();
			if(Object != null){
				Object.actorInteract(this);
				return Object;
			}
		}
		else if(facing == DIRECTION.WEST && x != 0){
			WorldObject Object = world.getMap().getTile(x-1,y).getObject();
			if(Object != null){
				Object.actorInteract(this);
				return Object;
			}
		}
		else if(facing == DIRECTION.SOUTH && y != 0){
			WorldObject Object = world.getMap().getTile(x,y-1).getObject();
			if(Object != null){
				Object.actorInteract(this);
				return Object;
			}
		}
		else if(facing == DIRECTION.NORTH && y != world.getMap().getHeight()-1){
			WorldObject Object = world.getMap().getTile(x,y+1).getObject();
			if(Object != null){
				Object.actorInteract(this);
				return Object;
			}
		}
		return null;
	}


	/**
	 * returns the actors x position
	 * @return
	 */
	public int getX() {
		return x;
	}
	/**
	 * returns the actors y position
	 * @return
	 */
	public int getY() {
		return y;
	}
	/**
	 * returns the worlds x position
	 * @return
	 */
	public float getWorldX() {
		return worldX;
	}
	/**
	 * returns the worlds y position
	 * @return
	 */
	public float getWorldY() {
		return worldY;
	}

	/**
	 * returns the actors sprite
	 * @return
	 */
	public TextureRegion getSprite() {
		if(texture !=null){
			return texture;
		}
		if (state == ACTOR_STATE.WALKING) {
			return (TextureRegion)animations.getWalking(facing).getKeyFrame(walkTimer);
		} else if (state == ACTOR_STATE.STANDING) {
			return animations.getStanding(facing);
		} else if (state == ACTOR_STATE.REFACING) {
			return (TextureRegion)animations.getWalking(facing).getKeyFrames()[0];
		}	else if(state == ACTOR_STATE.DEAD){
			return animations.getDying(facing);
		}
		return animations.getStanding(DIRECTION.SOUTH);
	}

	/**
	 * sets the rendering height
	 * @return
	 */
	@Override
	public float getSizeX() {
		return sizeX;
	}
	/**
	 * sets the rendering height
	 * @return
	 */
	@Override
	public float getSizeY() {
		return sizeY;
	}

	/**
	 * change the actors world
	 * @param world
	 * @param newX
	 * @param newY
	 */
	public void changeWorld(World world, int newX, int newY) {
		this.world.removeActor(this);
		this.teleport(newX, newY);
		this.world = world;
		this.world.addActor(this);
	}

	/**
	 * returns the state of the actor
	 * @return
	 */
	public ACTOR_STATE getState() {
		return state;
	}

	/**
	 * return the facing direction of the actor
	 * @return
	 */
	public DIRECTION getFacing() {
		return facing;
	}

	/**
	 * return the opposite facing direction of the actor
	 * @return
	 */
	public DIRECTION getOppositeFacing(){
		if(facing == NORTH){
			return SOUTH;
		}
		if(facing == EAST){
			return WEST;
		}
		if(facing == SOUTH){
		return NORTH;
		}
		else{
			return EAST;
		}
	}

	/**
	 *
	 * @return the actor in front of this actor, or null if there is no actor there
	 */
	public Actor getFacingActor(){
		int xpos = x;
		int ypos = y;
		if(facing==NORTH){
			ypos++;
		}
		else if(facing==SOUTH){
			ypos--;
		}
		else if(facing==WEST){
			xpos--;
		}
		else if(facing==EAST){
			xpos++;
		}
		List<Actor> actors = world.getActors();
		for(Actor actor : actors){
			if(actor.x == xpos && actor.y == ypos){
				return actor;
			}
		}
		return null;
	}

    /**
     *
     * @param distance how far ahead to look
     * @return the closest actor in front of this actor, or null if there is no actor there
     */
    public Actor getFacingActor(int distance){
        int xpos = x;
        int ypos = y;
        if(facing==NORTH){
            ypos+=distance;
        }
        else if(facing==SOUTH){
            ypos-=distance;
        }
        else if(facing==WEST){
            xpos-=distance;
        }
        else if(facing==EAST){
            xpos+=distance;
        }
        List<Actor> actors = world.getActors();
        for(Actor actor : actors){
            if(actor.x == xpos && actor.y == ypos){
                return actor;
            }
        }
        return null;
    }

    /**
     *
     * @param distance how far ahead to look
     * @param offset for monsters larger than 1x1
     * @return the closest actor in front of this actor, or null if there is no actor there
     */
    public Actor getFacingActor(int distance, int offset){
        int xpos = x + offset;
        int ypos = y + offset;
        if(facing==NORTH){
            ypos+=distance;
        }
        else if(facing==SOUTH){
            ypos-=distance;
        }
        else if(facing==WEST){
            xpos-=distance;
        }
        else if(facing==EAST){
            xpos+=distance;
        }
        List<Actor> actors = world.getActors();
        for(Actor actor : actors){
            if(actor.x == xpos && actor.y == ypos){
                return actor;
            }
        }
        return null;
    }

	/**
	 * sets the actors dialogue
	 * @param dialogue
	 */
	public void setDialogue(Dialogue dialogue) {
		this.dialogue = dialogue;
	}

	/**
	 * returns the actors stored dialogue
	 * @return
	 */
	public Dialogue getDialogue() {
		return dialogue;
	}

	/**
	 * returns the world that the actor is in
	 * @return
	 */
	public World getWorld() {
		return world;
	}
	/**
	 * sets the actor to be visible
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * checks if the actor is visible or not
	 * @return
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * returns the inventory of the actor
	 * @return
	 */
	public com.andi.DungeonExplorer.actor.Inventory.Inventory getInventory() {
		return inventory;
	}

	/**
	 * sets the actors x position
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * sets the actors y position
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * sets the state of the actor
	 * @param state
	 */
	public void setState(ACTOR_STATE state) {
		this.state = state;
	}


	public void setSizeX(float sizeX) {
		this.sizeX = sizeX;
	}

	public void setSizeY(float sizeY) {
		this.sizeY = sizeY;
	}
}
