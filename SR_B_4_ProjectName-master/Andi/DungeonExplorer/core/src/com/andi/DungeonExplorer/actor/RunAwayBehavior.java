package com.andi.DungeonExplorer.actor;

import com.andi.DungeonExplorer.map.DIRECTION;

import java.util.Random;

/**
 * Created by Kenneth on 11/30/2017.
 * Runs away from the player semi-randomly when they get close enough. Does not attack of its own volition.
 */

public class RunAwayBehavior extends LimitedWalkingBehavior {

    private int noticeDist;

    public RunAwayBehavior(com.andi.DungeonExplorer.actor.Actor actor, int limNorth, int limSouth, int limEast, int limWest, float moveIntervalMinimum, float moveIntervalMaximum, Random random, int aggroDistance) {
        super(actor, limNorth, limSouth, limEast, limWest, moveIntervalMinimum, moveIntervalMaximum, random);
        noticeDist = aggroDistance;
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
        boolean flag =false;
        if(distx<=noticeDist && disty<=noticeDist){
            inRange = true;
            distanceMin = Math.min(distx, disty);
            distanceMax = Math.max(distx, disty);
            inLine = (distx == 0 || disty == 0);
            dirY = player.getY()- getActor().getY() < 0 ? DIRECTION.NORTH : DIRECTION.SOUTH;
            dirX = player.getX()- getActor().getX() < 0 ? DIRECTION.EAST : DIRECTION.WEST;
            flag = true;
        }
        //if there is a target
        if(flag){
            //set facing
            if(distx == 0 && getActor().getState() == com.andi.DungeonExplorer.actor.Actor.ACTOR_STATE.STANDING){
                getActor().facing = dirY;
            }
            if(disty == 0 && getActor().getState() == com.andi.DungeonExplorer.actor.Actor.ACTOR_STATE.STANDING){
                getActor().facing = dirX;
            }
            //move right/left first, but only if not inline
            boolean moved = false;
            if(distx > 0 && !inLine){
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
            DIRECTION dir = random.nextBoolean() ? DIRECTION.EAST : DIRECTION.WEST;
            //try left/right if the other options don't work
            if((distx >= 0 || inLine)&& !moved){
                moved = getActor().move(dir);
                if(moved){
                    this.moveDelta.x += dir.getDX();
                    this.moveDelta.y += dir.getDY();
                }
            }
            dir = random.nextBoolean() ? DIRECTION.NORTH : DIRECTION.SOUTH;
            //try up/down if the other options don't work
            if((disty >= 0 || inLine)&& !moved){
                moved = getActor().move(dir);
                if(moved){
                    this.moveDelta.x += dir.getDX();
                    this.moveDelta.y += dir.getDY();
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
