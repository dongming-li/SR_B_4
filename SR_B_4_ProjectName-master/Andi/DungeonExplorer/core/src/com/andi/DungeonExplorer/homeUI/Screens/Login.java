package com.andi.DungeonExplorer.homeUI.Screens;

import com.andi.DungeonExplorer.DungeonExplorer;
import com.andi.DungeonExplorer.Networking.Server.NetworkLogin;
import com.andi.DungeonExplorer.Networking.Server.NetworkRegister;
import com.andi.DungeonExplorer.homeUI.Account.Account;
import com.andi.DungeonExplorer.homeUI.Actors.LoginActor;
import com.andi.DungeonExplorer.homeUI.Actors.LogoActor;
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
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.Scanner;


/**
 * Created by Ntcarter on 11/4/2017.
 */

/**
 * Creates a Login ui screen that users can interact with and change screens
 */
public class Login implements Screen {

    private DungeonExplorer game;
    private Skin skin;
    private Stage stage;
    private SpriteBatch batch;
    private Sprite sprite;
    private TextField txfUsername;
    private TextField txfPassword;
    private NetworkLogin networkLogin;
    private Account account;

    public Login(DungeonExplorer game, Account account){
        this.game = game;
        this.account = account;
    }

    /**
     * Creates and sets the initial screen
     */
    @Override
    public void show() {
        networkLogin = new NetworkLogin();
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
        submitButton.setWidth(100);
        submitButton.setHeight(50);
        submitButton.setPosition(250,25);
        submitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                //Add code to check database for login info.
                final String userName =txfUsername.getText();
                // System.out.println(userName);
                final String userPassword = txfPassword.getText();
                // System.out.println(userPassword);
                networkLogin.SendRegisterInfo(userName,userPassword);
                Timer time = new Timer();
                float delay =1;
                time.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        final String reply = networkLogin.getPos();
                        Scanner scan1 = new Scanner(reply);
                        String result2 = "";
                        result2+= scan1.next();
                        result2 += " "+scan1.next();
                        System.out.println("reply = "+result2);
                        scan1.close();
                        final Dialog dialog  =new Dialog(result2,skin);
                        dialog.show(stage);
                        time2.scheduleTask(new Timer.Task() {
                            @Override
                            public void run() {
                                Scanner scan = new Scanner(reply);
                                String result = "";
                                result+= scan.next();
                                result += " "+scan.next();
                                System.out.println("result = "+result);
                                if(result.equals("Login Successful!")){
                                    //set the account = to the return values (scan reply) the order is userName userPassword accountID adminID
                                    int count = 0;
                                    while(count<4){
                                        //username
                                        if(count==0){
                                            account.SetUserName(scan.next());
                                        }
                                        //userPassword
                                        if(count==1){
                                            account.SetUserPassword(scan.next());
                                        }
                                        //accountID
                                        if(count==2){
                                            account.SetUserID(scan.nextInt());
                                        }
                                        if(count==3){
                                            account.SetAdminID(scan.nextInt());
                                        }
                                        count++;
                                    }
                                    scan.close();
                                    stage.dispose();
                                    //Test for successful login values
                                   // System.out.println(account.GetUserName());
                                    //System.out.println(account.GetUserPassword());
                                    //System.out.println(account.GetUserID());
                                    //System.out.println(account.GetAdminID());
                                    game.SetLMainScreen();
                                }
                                else{
                                    stage.dispose();
                                    game.SetLoginScreen();
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
        LoginActor loginTxt = new LoginActor();
        stage.addActor(loginTxt);
        stage.addActor(logo);
        batch = new SpriteBatch();
        sprite = new Sprite(new Texture(Gdx.files.internal("BG4.png")));
        sprite.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(stage);

    }

    /**
     * Renders a new frame of the screen every Delta seconds
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

