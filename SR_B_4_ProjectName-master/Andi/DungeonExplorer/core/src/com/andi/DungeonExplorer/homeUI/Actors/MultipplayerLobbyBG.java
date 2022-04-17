package com.andi.DungeonExplorer.homeUI.Actors;

/**
 * Created by Ntcarter on 11/18/2017.
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Creates a new Lobby background image to be displayed and staged
 */
public class MultipplayerLobbyBG extends Actor{

    Texture texture = new Texture(Gdx.files.internal("LobbyBG4.png"));

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture,20,-50);
    }
}
