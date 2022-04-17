package com.andi.DungeonExplorer.world.Equipment;

import com.andi.DungeonExplorer.battle.moves.Attack;
import com.andi.DungeonExplorer.battle.moves.SpecialEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Kenneth on 11/27/2017.
 * Example ranged weapon, including a poison attack
 */

public class Bow extends Weapon {
    public Bow(int x, int y, TextureRegion texture, float sizeX, float sizeY, GridPoint2 tile, boolean b, int[] statArr, String name){
        super("Bow",x, y, texture, sizeX, sizeY, tile, true, "MainHand", statArr, name);//note manual assignment of slot name
        attacks = new HashMap<String, Attack>();
        Attack shot = new Attack("Shot", 8, 95, 10, Attack.damageType.PIERCE, false, 1, null, 1f, 8);
        Attack pShot = new Attack("Power Shot", 12, 85, 18, Attack.damageType.PIERCE, false, 1, null, .5f, 5);
        Attack fireArrow = new Attack("Fire Arrow", 16, 80, 10, Attack.damageType.FIRE, true, 2, null, .5f, 5);
        Collection<SpecialEffect> effects = new ArrayList<SpecialEffect>();
        //create a poison effect at .1 damage per update cycle
        effects.add(new SpecialEffect(SpecialEffect.type.POISON, 2, 1000, false, false));
        Attack poison = new Attack("Poisoned Arrow", 5, 90, 10, Attack.damageType.PIERCE, false, 2, effects, .5f, 5);
        attacks.put("pshot", pShot);
        attacks.put("shot", shot);
        attacks.put("fire arrow", fireArrow);
        attacks.put("poison", poison);
    }
}
