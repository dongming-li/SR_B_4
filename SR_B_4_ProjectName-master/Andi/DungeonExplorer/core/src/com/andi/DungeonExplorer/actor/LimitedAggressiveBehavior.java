package com.andi.DungeonExplorer.actor;

import com.andi.DungeonExplorer.actor.Character.Monster;
import com.andi.DungeonExplorer.battle.moves.Attack;
import com.andi.DungeonExplorer.map.DIRECTION;
import com.andi.DungeonExplorer.world.Equipment.Weapon;


import java.util.Map;
import java.util.Random;


/**
 * Created by Kenneth on 11/29/2017.
 * Runs at the player when they enter line of sight, attacking if possible.
 */


public class LimitedAggressiveBehavior extends LimitedWalkingBehavior {

    /**
     * how far the monster can see
     */
    protected int aggroDist;
    protected int atkTimer;
    /**
     * How far the monster should stop away from the player
     */
    protected int maintainDist;

    public LimitedAggressiveBehavior(com.andi.DungeonExplorer.actor.Actor actor, int limNorth, int limSouth, int limEast, int limWest, float moveIntervalMinimum, float moveIntervalMaximum, Random random, int aggroDistance, int maintainDist) {
        super(actor, limNorth, limSouth, limEast, limWest, moveIntervalMinimum, moveIntervalMaximum, random);
        aggroDist = aggroDistance;
        atkTimer = 0;
        this.maintainDist = maintainDist;
    }

    @Override
    public void update(float delta) {
        //check if player is in sight range
        boolean flag = false;
        com.andi.DungeonExplorer.actor.Actor actor = getActor();
        com.andi.DungeonExplorer.actor.Actor facingActor = null;
        int distance = 0;
        for(distance=1; distance<= aggroDist; distance++){
            flag = (actor.getFacingActor(distance)!=null);
            if(flag){
                facingActor = actor.getFacingActor(distance);
                break;
            }
        }
        //if there is a target
        if(flag){
            atkTimer++;
            //if it's not yet at the distance to maintain
            if(distance > maintainDist){
                boolean moved = getActor().move(actor.facing);
                if(moved){
                    this.moveDelta.x += actor.facing.getDX();
                    this.moveDelta.y += actor.facing.getDY();
                }
            }
            if(atkTimer >= 150){
                //only reset timer if attack works
                if(TryAttack(distance, facingActor)){
                    atkTimer = 0;
                }
            }
            return;
        }
        if (getActor().getState() != com.andi.DungeonExplorer.actor.Actor.ACTOR_STATE.STANDING) {
            return;
        }
        timer += delta*1.5f;
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

    /**
     * attack the player if possible
     * @return true iff successful
     */
    protected boolean TryAttack(int dist, Actor target){
        int maxRange=1;
        Monster monster = (Monster)getActor().character;
        for(Map.Entry entry : monster.innateAttacks.entrySet()){
            Attack attack = (Attack)entry.getValue();
            if(attack.range > maxRange){
                maxRange = attack.range;
            }
        }
        if(maxRange < dist){ return false;}
        Attack att = monster.innateAttacks.get(monster.combatAI.chooseAttack(dist));
        //System.out.println(target.name + " counterattacks!");
        com.andi.DungeonExplorer.actor.Character.Character.attack(monster, target.character, att, false);
        TryCounterAttack(dist, target);
        return true;
    }

    /**
     * have the player counterattack if possible
     * @param dist tile distance between actors
     * @param target true if successful
     * @return
     */
    protected boolean TryCounterAttack(int dist, Actor target){
        int maxRange=1;
        Monster monster = (Monster)getActor().character;
        Weapon weapon = (Weapon)target.character.equipment.get("MainHand");
        if(weapon==null){
            return false;
        }
        for(Map.Entry entry : weapon.attacks.entrySet()){
            Attack attack = (Attack)entry.getValue();
            if(attack.range > maxRange){
                maxRange = attack.range;
            }
        }
        if(maxRange < dist){ return false;}
        Attack att = weapon.chooseAttack(dist);
        //System.out.println(target.name + " counterattacks!");
        com.andi.DungeonExplorer.actor.Character.Character.attack(target.character, monster, att, true);
        return true;
    }
}
