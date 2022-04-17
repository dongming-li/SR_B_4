package com.andi.DungeonExplorer.homeUI.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Ntcarter on 11/5/2017.
 */

/**
 * Creates a new Logo image to be displayed and staged
 */
public class LogoActor extends Actor {

    Texture texture = new Texture(Gdx.files.internal("splorin1.png"));

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture,40,250);
    }
}
