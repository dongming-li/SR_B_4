package com.mygdx.game;

import java.util.ArrayList;



/**
 * Created by Kenneth on 9/11/2017.
 */

public class Game {
    public ArrayList<TileGrid> tileGrids;
    public ArrayList<Character> characters;
    public ArrayList<Item> items;

    public Game(ArrayList<TileGrid> tileGrids, ArrayList<Character> characters, ArrayList<Item> items){
        this.tileGrids = tileGrids;
        this.characters = characters;
        this.items = items;
    }

    public Game(){}
}
