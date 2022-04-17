package com.andi.DungeonExplorer.actor.Character;

import com.andi.DungeonExplorer.battle.moves.Attack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kenneth on 10/4/2017.
 * Used to decide what attack a monster should use - it learns what works best
 */

public class CombatAI {
    public HashMap<String, Float> attackWeights;
    public Monster monster;

    /**
     * Constructor with full control over default attack weight
     * defaultWeights needs to have an entry for every attack in innateAttacks
     * @param defaultWeights default weights for the specified moves.
     * @param monster monster advised by this AI
     */
    public CombatAI(HashMap<String, Float> defaultWeights, Monster monster){
        this.monster = monster;
        this.attackWeights = defaultWeights;
    }

    /**
     * Constructor with default weights (equal between all moves)
     * @param monster monster advised by this AI
     */
    public CombatAI(Monster monster){
        this.monster = monster;
        this.attackWeights = new HashMap<String, Float>();
        for (Map.Entry<String, Attack> entry : monster.innateAttacks.entrySet()) {
            attackWeights.put(entry.getKey(), 1f);
        }
    }

    /**
     * Adjust weight for an attack based on damage dealt
     * @param name of the attack.
     * @param damageRatio the proportion of the player's health the attack dealt.
     * @param resistance target's resistance to the attack
     */
    public void hit(String name, float damageRatio, double resistance){
        float mult = 1.1f + 2*damageRatio + ((float)resistance - 1) * 2;
        attackWeights.put(name, attackWeights.get(name)*mult);
    }

    /**
     * Adjust weight for an attack when it misses
     * @param name of the attack
     */
    public void miss(String name){
        float mult = .7f;
        attackWeights.put(name, attackWeights.get(name)*mult);
    }

    /**
     * Chooses randomly between all available attacks (weighted)
     * @return the name of the attack (the mapping in the attacks map)
     */
    public String chooseAttack(int distance){
        float allWeight = 0;
        HashMap<String, float[]> buckets = new  HashMap<String, float[]>();
        for(Map.Entry<String, Float> entry : attackWeights.entrySet()){
            Attack attack = monster.innateAttacks.get(entry.getKey());
            if(attack.range >= distance){
                buckets.put(entry.getKey(), new float[]{allWeight, allWeight+ entry.getValue()});
                allWeight += entry.getValue();
            }
        }
        float random = (float)Math.random() * allWeight;//random somewhere between 0 and max
        for(Map.Entry<String, float[]> bucket : buckets.entrySet()){
            Attack attack = monster.innateAttacks.get(bucket.getKey());
            if(attack.range >= distance) {
                if (random >= bucket.getValue()[0] && random <= bucket.getValue()[1]) {
                    return bucket.getKey();
                }
            }
        }
        return null;
    }
}
