package com.mygdx.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Actors.LogoActor;
import com.mygdx.game.MyGdxGame;


import sun.applet.Main;

/**
 * Created by Ntcarter on 11/4/2017.
 */

public class MainMenu implements Screen {

    private MyGdxGame game;
    Skin skin;
    Stage stage;

    public MainMenu(MyGdxGame game){
        this.game = game;
    }


    @Override
    public void show() {
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        ScreenViewport viewPort = new ScreenViewport();
        stage = new Stage(viewPort);
        final TextButton button = new TextButton("Login", skin, "default");

        button.setWidth(200);
        button.setHeight(50);
        final Dialog dialog = new Dialog("CLICK", skin);
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                dialog.show(stage);
                game.setLoginAndRegisterScreen();
            }
        });

        LogoActor actor = new LogoActor();
        stage.addActor(actor);

        stage.addActor(button);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
