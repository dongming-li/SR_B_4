package com.andi.DungeonExplorer.actor;

import com.andi.DungeonExplorer.map.DIRECTION;

import java.util.Random;

/**
 * Created by Kenny on 11/29/2017.
 * Chases after the player when they come close enough, attacking if possible.
 */

public class SeekBehavior extends LimitedAggressiveBehavior {

    public SeekBehavior(Actor actor, int limNorth, int limSouth, int limEast, int limWest, float moveIntervalMinimum, float moveIntervalMaximum, Random random, int aggroDistance, int maintainDist){
        super(actor, limNorth, limSouth, limEast, limWest, moveIntervalMinimum, moveIntervalMaximum, random, aggroDistance, maintainDist);
    }

    @Override
    public void update(float delta) {
        //check if player is in range
        boolean inRange = false;
        boolean inLine = false;
        Actor player = getActor().getWorld().getPlayer().owner;
        int distanceMin = 0;
        int distanceMax = 0;
        int distx = Math.abs(player.getX()- getActor().getX());
        int disty = Math.abs(player.getY()- getActor().getY());
        DIRECTION dirX = DIRECTION.NORTH;//doesn't matter what default is
        DIRECTION dirY = DIRECTION.EAST;
        if(distx<=aggroDist && disty<=aggroDist){
            inRange = true;
            distanceMin = Math.min(distx, disty);
            distanceMax = Math.max(distx, disty);
            inLine = (distx == 0 || disty == 0);
            dirY = player.getY()- getActor().getY() > 0 ? DIRECTION.NORTH : DIRECTION.SOUTH;
            dirX = player.getX()- getActor().getX() > 0 ? DIRECTION.EAST : DIRECTION.WEST;
        }
        //if there is a target
        if(inRange){
            timer++;
            atkTimer++;
            //set facing
            if(distx == 0 && getActor().getState() == com.andi.DungeonExplorer.actor.Actor.ACTOR_STATE.STANDING){
                getActor().facing = dirY;
            }
            if(disty == 0 && getActor().getState() == com.andi.DungeonExplorer.actor.Actor.ACTOR_STATE.STANDING){
                getActor().facing = dirX;
            }
            //if it's not yet at the distance to maintain
            if((distanceMax >= maintainDist || !inLine) && timer > 60){
                timer = 0;
                //move right/left first
                boolean moved = false;
                if(distx > 0){
                    moved = getActor().move(dirX);
                    if(moved){
                        this.moveDelta.x += dirX.getDX();
                        this.moveDelta.y += dirX.getDY();
                    }
                }
                //try up/down if already aligned left/right, or if can't move left/right
                if(disty > 0 && !moved){
                    moved = getActor().move(dirY);
                    if(moved){
                        this.moveDelta.x += dirY.getDX();
                        this.moveDelta.y += dirY.getDY();
                    }
                }
            }
            if(atkTimer >= 150 && inLine){
                //only reset timer if attack works
                if(TryAttack(distanceMax, player)){
                    atkTimer = 0;
                }
            }
            return;
        }
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
}
