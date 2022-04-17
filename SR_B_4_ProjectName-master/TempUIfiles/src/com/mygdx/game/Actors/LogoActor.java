package com.mygdx.game.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Ntcarter on 11/4/2017.
 */

public class LogoActor extends Actor {

    Texture texture = new Texture(Gdx.files.internal("logo.png"));

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture,60,300);
    }
}
