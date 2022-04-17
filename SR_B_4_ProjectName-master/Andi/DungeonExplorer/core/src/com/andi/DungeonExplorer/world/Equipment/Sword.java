package com.andi.DungeonExplorer.world.Equipment;

import com.andi.DungeonExplorer.battle.moves.Attack;
import com.andi.DungeonExplorer.battle.moves.SpecialEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Kenneth on 10/2/2017.
 * Example weapon - use this as a reference for adding attacks to weapons
 */

public class Sword extends Weapon {

    public Sword(int x, int y, TextureRegion texture, float sizeX, float sizeY, GridPoint2 tile, boolean b, int[] statArr, String name){
        super("sword", x, y, texture, sizeX, sizeY, tile, true, "MainHand", statArr, name);//note manual assignment of slot name
        attacks = new HashMap<String, Attack>();
        Attack slash = new Attack("Slash", 10, 95, 10, Attack.damageType.SLASH);
        Attack thrust = new Attack("Thrust", 8, 90, 15, Attack.damageType.PIERCE);
        Attack overhead = new Attack("Overhead", 17, 70, 10, Attack.damageType.SLASH, false, 2, null, .5f, 1);
        Collection<SpecialEffect> effects = new ArrayList<SpecialEffect>();
        //create a Recoil effect, which will damage the attacker for 20% of the damage dealt
        effects.add(new SpecialEffect(SpecialEffect.type.RECOIL, .2, 0, false, false));
        Attack reckless = new Attack("Reckless Swing", 20, 80, 10, Attack.damageType.SLASH, false, 2, effects, .5f, 1);
        Attack beam = new Attack("Sword Beam", 6, 80, 10, Attack.damageType.PIERCE, true, 2, null, .0001f, 5);
        attacks.put("slash", slash);
        attacks.put("thrust", thrust);
        attacks.put("overhead", overhead);
        attacks.put("reckless swing", reckless);
        attacks.put("sword beam", beam);
    }

}
