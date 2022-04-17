package com.andi.DungeonExplorer.homeUI.Screens;

import com.andi.DungeonExplorer.DungeonExplorer;
import com.andi.DungeonExplorer.Networking.Server.NetworkRegister;
import com.andi.DungeonExplorer.homeUI.Account.Account;
import com.andi.DungeonExplorer.homeUI.Actors.LogoActor;
import com.andi.DungeonExplorer.homeUI.Actors.RegisterActor;
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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.TimerTask;


/**
 * Created by Ntcarter on 11/4/2017.
 */

/**
 * Creates a Register ui screen that users can interact with and change screens
 */
public class Register implements Screen {

    private DungeonExplorer game;
    private Skin skin;
    private Stage stage;
    private SpriteBatch batch;
    private Sprite sprite;
    private TextField txfUsername;
    private TextField txfPassword;
    private NetworkRegister networkRegister;
    private String reply;
    private Account account;


    public Register(DungeonExplorer game, Account account){
        this.account = account;
        this.game = game;

    }

    /**
     * Creates and sets the initial screen
     */
    @Override
    public void show() {
        networkRegister = new NetworkRegister();
        ScreenViewport viewPort = new ScreenViewport();
        stage = new Stage(viewPort);
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        txfUsername = new TextField("Username",skin);
        txfUsername.setPosition(150, Gdx.graphics.getHeight()/2-50);
        txfUsername.setSize(300,40);

        txfPassword = new TextField("Password",skin);
        txfPassword.setPosition(150, Gdx.graphics.getHeight()/2-120);
        txfPassword.setSize(300,40);

        final TextButton backButton = new TextButton("      back      ", skin, "default");
        backButton.setWidth(50);
        backButton.setHeight(25);
        backButton.setPosition(540,10);
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setLoginAndRegisterScreen();
            }
        });

        final Timer time2 = new Timer();
        final TextButton submitButton = new TextButton("  Submit   ", skin, "default");
        final Dialog dialog;
        submitButton.setWidth(100);
        submitButton.setHeight(50);
        submitButton.setPosition(250,25);
        submitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                //Add code to check database for login info.
                String userName =txfUsername.getText();
               // System.out.println(userName);
                String userPassword = txfPassword.getText();
               // System.out.println(userPassword);
                networkRegister.SendRegisterInfo(userName,userPassword);
                Timer time = new Timer();
                float delay =1;
                time.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        reply = networkRegister.getPos();
                        System.out.println("reply = "+reply);
                        final Dialog dialog  =new Dialog(reply,skin);
                        dialog.show(stage);
                        time2.scheduleTask(new Timer.Task() {
                            @Override
                            public void run() {
                                if(reply.equals("You have successfully registered!")){
                                    stage.dispose();
                                    game.SetLoginScreen();
                                }
                                else{
                                    stage.dispose();
                                    game.SetRegisterScreen();
                                }

                            }
                        },3);
                    }
                },delay);
            }
        });




        stage.addActor(submitButton);
        stage.addActor(txfPassword);
        stage.addActor(txfUsername);
        stage.addActor(backButton);
        LogoActor logo = new LogoActor();
        RegisterActor registerTxt = new RegisterActor();
        stage.addActor(registerTxt);
        stage.addActor(logo);
        batch = new SpriteBatch();
        sprite = new Sprite(new Texture(Gdx.files.internal("BG4.png")));
        sprite.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Renders a new frame of the screen every Delta seconds.
     * @param delta
     */
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
