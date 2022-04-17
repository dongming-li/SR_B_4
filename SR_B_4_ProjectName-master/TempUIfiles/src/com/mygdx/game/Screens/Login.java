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
import com.mygdx.game.Actors.LoginActor;
import com.mygdx.game.Actors.LogoActor;
import com.mygdx.game.MyGdxGame;

/**
 * Created by Ntcarter on 11/4/2017.
 */

public class Login implements Screen {

    private MyGdxGame game;
    private Skin skin;
    private Stage stage;
    private SpriteBatch batch;
    private Sprite sprite;
    private TextField txfUsername;
    private TextField txfPassword;

    public Login(MyGdxGame game){
        this.game = game;
    }
    @Override
    public void show() {
        ScreenViewport viewPort = new ScreenViewport();
        stage = new Stage(viewPort);
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        txfUsername = new TextField("Username",skin);
        txfUsername.setPosition(175, Gdx.graphics.getHeight()/2-70);
        txfUsername.setSize(300,40);

        txfPassword = new TextField("Password",skin);
        txfPassword.setPosition(175, Gdx.graphics.getHeight()/2-140);
        txfPassword.setSize(300,40);

        final TextButton backButton = new TextButton("      back      ", skin, "default");
        backButton.setWidth(50);
        backButton.setHeight(25);
        backButton.setPosition(580,10);
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setLoginAndRegisterScreen();
            }
        });


        final TextButton submitButton = new TextButton("  Submit   ", skin, "default");
        submitButton.setWidth(100);
        submitButton.setHeight(50);
        submitButton.setPosition(275,35);
        submitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                //Add code to check database for login info.
            }
        });

        stage.addActor(submitButton);
        stage.addActor(txfPassword);
        stage.addActor(txfUsername);
        stage.addActor(backButton);
        LogoActor logo = new LogoActor();
        LoginActor loginTxt = new LoginActor();
        stage.addActor(loginTxt);
        stage.addActor(logo);
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
