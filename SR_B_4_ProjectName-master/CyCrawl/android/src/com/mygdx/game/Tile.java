package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

import java.util.ArrayList;
import java.util.Map;


/**
 * Created by Kenneth on 9/5/2017.
 * Abstract class to serve as a foundation for more specific tile types.
 */

public abstract class Tile {

    /**
     * If a switch of the same id is activated, the tile is activated
     */
    public int switchID = -1;
    /**
     * The grid to which this tile belongs
     */
    //public TileGrid grid;
    /**
     * Whether the player should be able to move into this tile.
     */
    public boolean passable;
    /**
     * Filepath for the texture this tile is currently using. You can get it from the map in Data
     */
    public String texturePath = "";

    /**
     * Whether this tile should be visible to the player
     * For use in fog-of-war situations
     */
    public boolean isVisible = true;

    /**
     * The item on this tile, if there is one
     */
    public Item item;

    /**
     * x and y positions. Matches array position in TileGrid
     */
    public int x;
    public int y;

    /*
    public Tile(TextureRegion textureRegion) {
        super(textureRegion);
    }
    */
    /**
     * Placeholder method for activation via switches. Anything using this method should
     * also have a way to set a switchID
     */
    public void Activate(){}

    /**
     * @return the character who is on this tile, or null if no character is on the tile
     */
    /*
    public Character WhoIsOnMe(){
        ArrayList<Character> characters = grid.getGame().characters;
        for (Character c :characters) {
            if(c.getPosX() == x && c.getPosY() == y){
                return c;
            }
        }
        return null;
    }*/

    /*
    Implementing TiledMapTile methods
     */
    /*
    public MapObjects getObjects(){
        MapObjects objects = new MapObjects();
        //add item, character, any other objects

        return objects;
    }
    */
}
