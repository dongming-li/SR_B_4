package com.andi.DungeonExplorer.controller;

import com.andi.DungeonExplorer.map.DIRECTION;
import com.andi.DungeonExplorer.world.editor.Index;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

/**
 * Created by brytonhayes on 11/5/17.
 *
 * Controls movement of an index within a map builder
 */
public class IndexMovementController extends InputAdapter {
    private Index index;    //index being moved

    private boolean[] buttonPress;  //true for each direction whos button is pressed
    private float[] buttonTimer;    //timer for length of presses

    public IndexMovementController(Index i) {
        this.index = i;     //index being moved

        //no buttons initially pressed
        buttonPress = new boolean[DIRECTION.values().length];
        buttonPress[DIRECTION.NORTH.ordinal()] = false;
        buttonPress[DIRECTION.SOUTH.ordinal()] = false;
        buttonPress[DIRECTION.EAST.ordinal()] = false;
        buttonPress[DIRECTION.WEST.ordinal()] = false;

        //initialize timer values
        buttonTimer = new float[DIRECTION.values().length];
        buttonTimer[DIRECTION.NORTH.ordinal()] = 0f;
        buttonTimer[DIRECTION.SOUTH.ordinal()] = 0f;
        buttonTimer[DIRECTION.EAST.ordinal()] = 0f;
        buttonTimer[DIRECTION.WEST.ordinal()] = 0f;
    }

    /**
     * Runs when a key is pressed
     * @param keycode key pressed
     * @return false if no recognized buttons pressed
     */
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.UP) {
            buttonPress[DIRECTION.NORTH.ordinal()] = true;
        }
        if (keycode == Input.Keys.DOWN) {
            buttonPress[DIRECTION.SOUTH.ordinal()] = true;
        }
        if (keycode == Input.Keys.LEFT) {
            buttonPress[DIRECTION.WEST.ordinal()] = true;
        }
        if (keycode == Input.Keys.RIGHT) {
            buttonPress[DIRECTION.EAST.ordinal()] = true;
        }
        return false;
    }

    /**
     * runs when a key is released
     * @param keycode key released
     * @return false if no keys recognized
     */
    @Override
    public boolean keyUp(int keycode) {
        //check movement buttons
        if (keycode == Input.Keys.UP) {
            releaseDirection(DIRECTION.NORTH);
        }
        if (keycode == Input.Keys.DOWN) {
            releaseDirection(DIRECTION.SOUTH);
        }
        if (keycode == Input.Keys.LEFT) {
            releaseDirection(DIRECTION.WEST);
        }
        if (keycode == Input.Keys.RIGHT) {
            releaseDirection(DIRECTION.EAST);
        }
        if((keycode == Input.Keys.C)){
            System.out.println(index.getX() + ":" + index.getY());
        }

        //add selected tile or object
        if(keycode == Input.Keys.A){
            index.addTileSelection();
        }

        //add selected tile or object
        if(keycode == Input.Keys.Z){
            index.addObjectSelection();
        }

        //save the map
        if(keycode == Input.Keys.S){
            index.saveMap();
        }

        //load the map
        if(keycode == Input.Keys.L){
            index.loadMap();
        }

        //remove object
        if((keycode == Input.Keys.R)){
            index.removeObject();
        }
        return false;
    }

    /**
     * update movement status
     * @param delta time increment
     */
    public void update(float delta) {
        if (buttonPress[DIRECTION.NORTH.ordinal()] && (buttonTimer[DIRECTION.NORTH.ordinal()] < .1)) {
            updateDirection(DIRECTION.NORTH, delta);
            return;
        }
        if (buttonPress[DIRECTION.SOUTH.ordinal()] && (buttonTimer[DIRECTION.SOUTH.ordinal()] < .1)) {
            updateDirection(DIRECTION.SOUTH, delta);
            return;
        }
        if (buttonPress[DIRECTION.WEST.ordinal()] && (buttonTimer[DIRECTION.WEST.ordinal()] < .1)) {
            updateDirection(DIRECTION.WEST, delta);
            return;
        }
        if (buttonPress[DIRECTION.EAST.ordinal()] && (buttonTimer[DIRECTION.EAST.ordinal()] < .1)) {
            updateDirection(DIRECTION.EAST, delta);
            return;
        }
    }

    /**
     * Runs every frame, for each direction whose key is pressed
     */
    private void updateDirection(DIRECTION dir, float delta) {
        buttonTimer[dir.ordinal()] += delta;    //increment timer

    }

    /**
     * Runs when a key is released, argument is its corresponding direction.
     */
    private void releaseDirection(DIRECTION dir) {
        buttonPress[dir.ordinal()] = false; //button not pressed
        buttonTimer[dir.ordinal()] = 0f;    //reset timer
        index.move(dir);
    }
}
