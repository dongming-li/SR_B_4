package com.andi.DungeonExplorer.actor;

import java.util.Random;

import com.andi.DungeonExplorer.map.DIRECTION;
import com.badlogic.gdx.math.GridPoint2;

/**
 * Behavior that will make an Actor walk around it's initial position randomly.
 */
public class LimitedWalkingBehavior extends ActorBehavior {

    protected float moveIntervalMinimum;
    protected float moveIntervalMaximum;
	protected Random random;
	
	protected float timer;
	protected float currentWaitTime;
	
	protected GridPoint2 moveDelta;
    protected int limNorth, limSouth, limEast, limWest;

	public LimitedWalkingBehavior(com.andi.DungeonExplorer.actor.Actor actor, int limNorth, int limSouth, int limEast, int limWest, float moveIntervalMinimum, float moveIntervalMaximum, Random random) {
		super(actor);
		this.limNorth = limNorth;
		this.limSouth = limSouth;
		this.limEast = limEast;
		this.limWest = limWest;
		this.moveIntervalMinimum = moveIntervalMinimum;
		this.moveIntervalMaximum = moveIntervalMaximum;
		this.random = random;
		this.timer = 0f;
		this.currentWaitTime = calculateWaitTime();
		this.moveDelta = new GridPoint2();
	}

	@Override
	public void update(float delta) {

		if (getActor().getState() != com.andi.DungeonExplorer.actor.Actor.ACTOR_STATE.STANDING) {
			return;
		}
		timer += delta;
		if (timer >= currentWaitTime) {
			int directionIndex = random.nextInt(DIRECTION.values().length);
			DIRECTION moveDirection = DIRECTION.values()[directionIndex];
			if (this.moveDelta.x+moveDirection.getDX() > limEast || -(this.moveDelta.x+moveDirection.getDX()) > limWest || this.moveDelta.y+moveDirection.getDY() > limNorth || -(this.moveDelta.y+moveDirection.getDY()) > limSouth) {
				getActor().reface(moveDirection);
				currentWaitTime = calculateWaitTime();
				timer = 0f;
				return;
			}
			boolean moved = getActor().move(moveDirection);
			if (moved) {
				this.moveDelta.x += moveDirection.getDX();
				this.moveDelta.y += moveDirection.getDY();
			}
			
			currentWaitTime = calculateWaitTime();
			timer = 0f;
		}
	}
	
	protected float calculateWaitTime() {
		return random.nextFloat() * (moveIntervalMaximum - moveIntervalMinimum) + moveIntervalMinimum;
	}
}
