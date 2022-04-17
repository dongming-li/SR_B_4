package com.andi.DungeonExplorer.world.InteractableItems;

import com.andi.DungeonExplorer.dialogue.Dialogue;
import com.andi.DungeonExplorer.dialogue.LinearDialogueNode;
import com.andi.DungeonExplorer.map.DIRECTION;
import com.andi.DungeonExplorer.actor.Actor;
import com.andi.DungeonExplorer.actor.PlayerActor;
import com.andi.DungeonExplorer.world.Equipment.Key;
import com.andi.DungeonExplorer.world.WorldObject;
import com.andi.DungeonExplorer.world.events.ActorVisibilityEvent;
import com.andi.DungeonExplorer.world.events.ActorWalkEvent;
import com.andi.DungeonExplorer.world.events.ChangeWorldEvent;
import com.andi.DungeonExplorer.world.events.DoorEvent;
import com.andi.DungeonExplorer.world.editor.Index;
import com.andi.DungeonExplorer.world.events.CutsceneEventQueuer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Timer;


/**
 * Created by Andi Li on 9/18/2017.
 */
public class Door extends WorldObject {

	private int doorID;
	private int oldDoorId;
	private Animation openAnimation;
	private Animation closeAnimation;
	private boolean doorChecker;
	private float animationTimer = 0f;
	private float animationTime = 0.5f;

	private STATE state = STATE.CLOSED;
	private Dialogue dialogue;
	private CutsceneEventQueuer broadcaster;
	private boolean activable;
	private int actId;
	private int newX;
	private int newY;
	private String location;



	public enum STATE {
		OPEN,
		CLOSED,
		OPENING,
		CLOSING,
		;
	}




	public Door(int x, int y, Animation openAnimation, Animation closeAnimation, int doorId, int actId, CutsceneEventQueuer broadcaster, int newX, int newY, String name) {
		super("door",x, y, false, (TextureRegion) openAnimation.getKeyFrames()[0], 1f, 1.5f, new GridPoint2(0,0),true,actId,true);
		this.actId=actId;
		this.openAnimation = openAnimation;
		this.closeAnimation = closeAnimation;
		this.doorID = doorId;
		oldDoorId = doorID;
		this.broadcaster = broadcaster;
		this.newX = newX;
		this.newY = newY;
		this.location = name;

	}

	public void open() {
		state = STATE.OPENING;
	}

	public void close() {
		state = STATE.CLOSING;
	}

	@Override
	public void update(float delta) {
		super.update(delta);

		if (state == STATE.CLOSING || state == STATE.OPENING) {
			animationTimer += delta;
			if (animationTimer >= animationTime) {
				if (state == STATE.CLOSING) {
					state = STATE.CLOSED;
				} else if (state == STATE.OPENING) {
					state = STATE.OPEN;
				}
				animationTimer = 0f;
			}
		}
	}

	public STATE getState() {
		return state;
	}
	public void setState(STATE state) {
		this.state = state;
	}

	/**
	 * grabs the sprite associated with the door
	 * @return
	 */
	public TextureRegion getSprite() {
		if (state == STATE.OPEN) {
			return (TextureRegion) closeAnimation.getKeyFrames()[3];
		} else if (state == STATE.CLOSED) {
			return (TextureRegion)  closeAnimation.getKeyFrames()[0];
		} else if (state == STATE.OPENING) {
			return (TextureRegion)  openAnimation.getKeyFrame(animationTimer);
		} else if (state == STATE.CLOSING) {
			return (TextureRegion)  closeAnimation.getKeyFrame(animationTimer);
		}
		return null;
	}
	public void setDialogue(Dialogue dialogue) {
		this.dialogue = dialogue;
	}

	public Dialogue getDialogue() {
		return dialogue;
	}

	public void unlockDoor(){
		this.doorID = 0;
	}
	public void lockDoor(){
		this.doorID = oldDoorId;
	}
	public boolean isActivable() {
		return activable;
	}

	public void setActivable(boolean activable) {
		this.activable = activable;
	}

	public int getActId() {
		return actId;
	}

	public void setActId(int actId) {
		this.actId = actId;
	}

	/**
	 * this method executes when then actor interacts with the object
	 * @param a
	 */
	@Override
	public void actorInteract(Actor a) {
		if(a instanceof PlayerActor){
			interactDoor(this ,a);
			if(doorChecker == true ){
				doorChecker= false;
				broadcaster.queueEvent(new DoorEvent(this, true));
				this.setWalkable(true);
				broadcaster.queueEvent(new ActorVisibilityEvent(a, true));
				broadcaster.queueEvent(new DoorEvent(this, false));
				broadcaster.queueEvent(new ChangeWorldEvent(location, newX, newY, DIRECTION.NORTH, Color.BLACK));
				broadcaster.queueEvent(new ActorVisibilityEvent(a, false));
				broadcaster.queueEvent(new ActorWalkEvent(a,DIRECTION.NORTH));
			}


		}

	}

	public void indexInteract(Index i) {
		interactDoor(this ,i);
		this.setWalkable(true);
		broadcaster.queueEvent(new ChangeWorldEvent(location, newX, newY, DIRECTION.NORTH, Color.BLACK));//add world}

	}


	/**
	 * Checks the conditions for the door to open
	 * @param door
	 * @param a
	 * @return
	 */
	private Door interactDoor(Door door, Actor a) {
		int checkId = door.getDoorID();
		Key key = a.getInventory().getKey(checkId);
		if(checkId == 0){
			setDialogueStarter(false);
			doorChecker = true;
			return this;
		}
		else if(key == null){
			setDialogueStarter(true);
			doorChecker = false;
			Dialogue closed = new Dialogue();
			LinearDialogueNode node1 = new LinearDialogueNode("You need a key to open this door\n or you have the wrong key.", 0,false);
			closed.addNode(node1);
			this.setDialogue(closed);
			return this;

		}
		else if(key.getKeyId() == checkId){
			setDialogueStarter(false);
			doorChecker = true;
			this.setDoorID(0);
			a.getInventory().drop(key);
			return this;
		}
		return this;
	}

	private Door interactDoor(Door door, Index i) {

			doorChecker = true;
			return this;
	}

	public  int getDoorID() {
		return doorID;
	}


	@Override
	public void activate() {
		final Door door = this;
		door.unlockDoor();
		broadcaster.queueEvent(new DoorEvent(door, true));
		float delay = 5; //In seconds this time
		Timer.schedule(new Timer.Task(){
			@Override
			public void run() {
				door.lockDoor();
				broadcaster.queueEvent(new DoorEvent(door, false));
			}
		}, delay);

	}
	@Override
	public void boulderActivate(){
		final Door door = this;
		if(door.getState() == STATE.CLOSED){
			door.unlockDoor();
			broadcaster.queueEvent(new DoorEvent(door, true));
		}

	}
	@Override
	public void boulderDeactivate(){
		final Door door = this;
		if(door.getState() == STATE.OPEN){
			door.lockDoor();
			broadcaster.queueEvent(new DoorEvent(door, false));
		}
	}
	public void setDoorID(int doorID) {
		this.doorID = doorID;
	}

}
