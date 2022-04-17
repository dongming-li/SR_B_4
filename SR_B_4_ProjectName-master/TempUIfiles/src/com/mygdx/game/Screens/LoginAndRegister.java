package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Actors.LogoActor;
import com.mygdx.game.MyGdxGame;

/**
 * Created by Ntcarter on 11/4/2017.
 */

public class LoginAndRegister implements Screen {
        private MyGdxGame game;
        private Skin skin;
        private Stage stage;
        private Table table;
        private Table table2;
        private SpriteBatch batch;
        private Sprite sprite;

    public LoginAndRegister(MyGdxGame game){
        this.game = game;
    }

    @Override
    public void show() {
        ScreenViewport viewPort = new ScreenViewport();
        stage = new Stage(viewPort);
        table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.center|Align.top);
        table.setPosition(0, Gdx.graphics.getHeight()/2-50);

        table2 = new Table();
        table2.setWidth(stage.getWidth());
        table2.align(Align.center);
        table2.setPosition(0, Gdx.graphics.getHeight()/2-100);

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        final TextButton loginButton = new TextButton("      Login      ", skin, "default");
        loginButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.SetLoginScreen();
            }
        });
        final TextButton registerButton = new TextButton("   Register   ", skin, "default");
        registerButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.SetRegisterScreen();
            }
        });
        final TextButton POButton = new TextButton("Play Offline", skin, "default");
        POButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.SetOfflineScreen();
            }
        });

        loginButton.setWidth(200);
        loginButton.setHeight(50);
        registerButton.setWidth(200);
        registerButton.setHeight(50);
        POButton.setWidth(200);
        POButton.setHeight(50);


        table2.add(POButton);
        table.add(loginButton).padRight(10);
        table.add(registerButton);




        LogoActor actor = new LogoActor();
        stage.addActor(actor);

        stage.addActor(table);
        stage.addActor(table2);

        batch = new SpriteBatch();
        sprite = new Sprite(new Texture(Gdx.files.internal("B2.png")));
        sprite.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        sprite.draw(batch);
        batch.end();
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
