package com.andi.DungeonExplorer.actor.Character;

/**
 * Created by Kenneth on 9/24/2017.
 * Status effects are stored by characters, and cause a variety of positive and negative effects.
 * You can use status effects to temporarily change a character's parameters, for example.
 * Actual effects will generally happen character-side.
 */

public class StatusEffect {

    public enum type{
        POISON,//deals damage over time to the character at a rate of magnitude/100 per tick
        BURN,
        MAXHP, //increases maxHealth by 1*magnitude, and heals by the same amount. Max health reverts on removal
        POWER, //applies multiplier to damage dealt of (1+magnitude/100.0), applies before defense
        DEFENSE //applies multiplier to damage received of (1-magnitude/100.0), applies after all other calculations
    }

    /**
     * the identifier of the status effect. Used by the character
     * to determine its effects.
     */
    public type name;

    /**
     * the remaining time, in render cycles, of the effect.
     * a value of -1 indicates an effect that will never expire,
     * at least not by time alone.
     */
    public int timeLeft;

    /**
     * A multiplier for the default effect of the status. A value of 2 would double the effect;
     * a value of -1 would invert it. Might not apply to all status effects.
     */
    public double magnitude = 1;

    /**
     *
     * @param name
     * @param time
     * @param magnitude
     */
    public StatusEffect(type name, int time, double magnitude){
        this.name = name;
        timeLeft = time;
        this.magnitude = magnitude;
    }
}
