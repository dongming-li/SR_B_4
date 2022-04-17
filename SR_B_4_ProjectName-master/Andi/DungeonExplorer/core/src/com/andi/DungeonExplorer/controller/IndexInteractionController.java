package com.andi.DungeonExplorer.controller;

import com.andi.DungeonExplorer.world.editor.Index;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

/**
 * Created by brytonhayes on 11/10/17.
 *
 * Control interaction between the index and world objects (door)
 */
public class IndexInteractionController extends InputAdapter {

    private Index i;    //index to control

    /**
     * Basic constructor
     * @param i index being used in this screen
     */
    public IndexInteractionController(Index i) {
        this.i = i;
    }

    /**
     * runs when a key is released
     * @param keycode code representing released key
     * @return true if any successful interaction
     */
    @Override
    public boolean keyUp(int keycode){
        if(keycode == Input.Keys.Z){
            i.interact();
            return true;
        }
        return false;
    }
}
