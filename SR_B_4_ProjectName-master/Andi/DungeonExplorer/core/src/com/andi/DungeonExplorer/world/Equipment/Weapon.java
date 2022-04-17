package com.andi.DungeonExplorer.world.Equipment;

import com.andi.DungeonExplorer.battle.moves.Attack;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kenneth on 10/2/2017.
 * All weapons are members of this class, each with a list of usable attacks
 */

public class Weapon extends Equipment {
    /**
     * attacks assigned to the weapon, usable by a character when it's equipped
     */
    public HashMap<String, Attack> attacks;


    public Weapon(String type, int x, int y, TextureRegion texture, float sizeX, float sizeY, GridPoint2 tile, boolean grabbable, String slot, int[] statArr, String name) {
        super(x, y, false,texture, sizeX, sizeY, tile, grabbable, slot, statArr, name);
    }

    /**
     * finds the usable attacks based on the distance of the target, then selects one randomly based on its weight
     * @param distance number of tiles to target
     * @return
     */
    public Attack chooseAttack(int distance){
        HashMap<Float[], Attack> weights = new HashMap<Float[], Attack>();//buckets
        float total = 0;
        for(Map.Entry entry : attacks.entrySet()){
            Attack attack = (Attack)entry.getValue();
            if(attack.range >= distance){//only if the attack can reach the target
                weights.put(new Float[]{total, attack.weight+total}, attack);
                total += attack.weight;
            }
        }
        float random = (float)Math.random()*total;
        for(Map.Entry<Float[], Attack> entry : weights.entrySet()){
            if(random >= entry.getKey()[0] && random <= entry.getKey()[1]){
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public String toString(){
        String string;
        string = super.toString() + ':' + attacks.toString();
        return string;
    }
}
