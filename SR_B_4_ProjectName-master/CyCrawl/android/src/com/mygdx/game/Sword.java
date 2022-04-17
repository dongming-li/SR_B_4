package com.mygdx.game;

import java.util.HashMap;

/**
 * Created by Kenneth on 10/2/2017.
 */

public class Sword extends Weapon {

    public Sword (HashMap<String, Integer> stats){
        attacks = new HashMap<String, Attack>();
        Attack slash = new Attack("Slash", 10, 95, 10, Attack.damageType.SLASH);
        Attack thrust = new Attack("Thrust", 8, 90, 15, Attack.damageType.PIERCE);
        Attack overhead = new Attack("Overhead", 14, 70, 10, Attack.damageType.SLASH, false, 2);
        this.stats = stats;
    }

}
