package com.andi.DungeonExplorer.homeUI.Screens;

/**
 * Created by Ntcarter on 11/18/2017.
 */
import com.andi.DungeonExplorer.DungeonExplorer;
import com.andi.DungeonExplorer.Networking.Server.GetMultiplayerPlayers;
import com.andi.DungeonExplorer.Networking.Server.JoinLobby;
import com.andi.DungeonExplorer.Networking.Server.LeaveLobby;
import com.andi.DungeonExplorer.Networking.Server.SendGetMultiplayercoords;
import com.andi.DungeonExplorer.homeUI.Account.Account;
import com.andi.DungeonExplorer.homeUI.Actors.LogoActor;
import com.andi.DungeonExplorer.homeUI.Actors.MultipplayerLobbyBG;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.Scanner;


public class LMultiplayer implements Screen {

    private DungeonExplorer game;
    private Skin skin;
    private Stage stage;
    private Stage stage2;
    private Table table;
    private Table table2;
    private SpriteBatch batch;
    private Sprite sprite;
    private Account account;
    private int lobbyCounter;
    private VerticalGroup vGroup;
    private Label label1;
    private Label label2;
    private int count;
    private Timer timer;
    private Timer timer3;
    private GetMultiplayerPlayers gMP;
    private String s;
    private String tmp = "";
    private String tmp2 = "";
    private JoinLobby joiner;
    private LeaveLobby leaver;
    private SendGetMultiplayercoords sendGetMultiplayercoords;

    public LMultiplayer(DungeonExplorer game, Account account){
        this.game = game;
        this.account = account;
        gMP = new GetMultiplayerPlayers();
        lobbyCounter = 0;
        joiner = new JoinLobby();
        leaver = new LeaveLobby();
        sendGetMultiplayercoords = new SendGetMultiplayercoords();

    }

    /**
     * Creates and sets the initial screen
     */
    @Override
    public void show() {
        ScreenViewport viewPort = new ScreenViewport();
        stage = new Stage(viewPort);
        stage2 = new Stage(viewPort);
        vGroup = new VerticalGroup();
        table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.center|Align.top);
        table.setPosition(180, Gdx.graphics.getHeight()/2+55);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        label1= new Label("",skin);
        label2= new Label("",skin);
        label1.setSize(100,100);
        label2.setSize(100,100);
        timer = new Timer();
        timer3 = new Timer();
        final TextButton backButton = new TextButton("  Back  ", skin, "default");
        final TextButton logoutButton = new TextButton("Logout", skin, "default");
        final TextButton joinButton = new TextButton("  Join  ", skin, "default");
        final TextButton leaveButton = new TextButton("Leave", skin, "default");
        final TextButton startButton = new TextButton("  Start Game  ", skin, "default");

        logoutButton.setHeight(25);
        logoutButton.setWidth(60);
        logoutButton.setPosition(25,10);
        final Timer time3 = new Timer();
        logoutButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){

                leaver.SendLeaveInfo(account.GetUserName(),account.GetUserPassword());
                Timer time = new Timer();
                float delay =1;
                time.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        final String s = "Leaving Lobby";
                        final Dialog dialog  =new Dialog(s,skin);
                        dialog.show(stage);
                        time3.scheduleTask(new Timer.Task() {
                            @Override
                            public void run() {
                                if(s.equals("Leaving Lobby")){
                                    account.LogOut();
                                    game.SetLoginScreen();
                                }
                                else{
                                    account.LogOut();
                                    game.SetLoginScreen();
                                }

                            }
                        },3);
                    }
                },delay);
            }
        });



        backButton.setHeight(25);
        backButton.setWidth(60);
        backButton.setPosition(525,10);
        final Timer time4 = new Timer();
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){

                leaver.SendLeaveInfo(account.GetUserName(),account.GetUserPassword());
                Timer time = new Timer();
                float delay =1;
                time.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        final String s = "Leaving Lobby";
                        final Dialog dialog  =new Dialog(s,skin);
                        dialog.show(stage);
                        time4.scheduleTask(new Timer.Task() {
                            @Override
                            public void run() {
                                if(s.equals("Leaving Lobby")){
                                    game.SetLMainScreen();
                                }
                                else{
                                    game.SetLMainScreen();
                                }

                            }
                        },3);
                    }
                },delay);
            }
        });




        final Timer time2 = new Timer();
        joinButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
               // lobbyCounter++;
                joiner.SendJoinInfo(account.GetUserName(),account.GetUserPassword());

                Timer time = new Timer();
                float delay =1;
                time.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        final String s = joiner.getPos();
                        System.out.println("reply = "+s);
                        final Dialog dialog  =new Dialog(s,skin);
                        dialog.show(stage);
                        time2.scheduleTask(new Timer.Task() {
                            @Override
                            public void run() {
                                if(s.equals("Joining lobby")){
                                    sendGetMultiplayercoords.SendMultiplayerStuff(account.GetUserName(),account.GetUserPassword(),-100,-100,"South");
                                    stage.dispose();
                                    game.SetLMultiplayerScreen();
                                }
                                else{
                                    stage.dispose();
                                    game.SetLMultiplayerScreen();
                                }

                            }
                        },3);
                    }
                },delay);
            }
        });

        leaveButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
               // lobbyCounter--;
                leaver.SendLeaveInfo(account.GetUserName(),account.GetUserPassword());
                Timer time = new Timer();
                float delay =1;
                time.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        final String s = leaver.getPos();
                        System.out.println("reply = "+s);
                        final Dialog dialog  =new Dialog(s,skin);
                        dialog.show(stage);
                        time2.scheduleTask(new Timer.Task() {
                            @Override
                            public void run() {
                                if(s.equals("Leaving Lobby")){
                                    stage.dispose();
                                    game.SetLMultiplayerScreen();
                                }
                                else{
                                    stage.dispose();
                                    game.SetLMultiplayerScreen();
                                }

                            }
                        },3);
                    }
                },delay);
            }
        });

        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(lobbyCounter==2){
                    final String s ="Starting Game";
                    final Dialog dialog  =new Dialog(s,skin);
                    dialog.show(stage);
                    time2.scheduleTask(new Timer.Task() {
                        @Override
                        public void run() {
                            if(s.equals("Starting Game")){
                                sendGetMultiplayercoords.SendMultiplayerStuff(account.GetUserName(),account.GetUserPassword(),-100,-100,"South");
                                timer3.scheduleTask(new Timer.Task() {
                                    @Override
                                    public void run() {
                                        System.out.println("TESTTTTTTTTTTTTTTTTTTTTT "+sendGetMultiplayercoords.getPos());
                                        calculateStartingCoords(sendGetMultiplayercoords.getPos());
                                    }
                                },4);
                            }
                        }
                    },3);

                }
                else{
                    final Dialog dialog  =new Dialog("2 players are need to start a game",skin);
                    final String s = dialog.toString();
                    dialog.show(stage);
                    time2.scheduleTask(new Timer.Task() {
                        @Override
                        public void run() {
                            if(s.equals("2 players are need to start a game")){
                                stage.dispose();
                                game.SetLMultiplayerScreen();
                            }
                            else{
                                stage.dispose();
                                game.SetLMultiplayerScreen();
                            }

                        }
                    },3);

                }
            }
        });

        table.add(joinButton).padBottom(40);
        table.row();
        table.add(leaveButton).padBottom(40);
        table.row();
        table.add(startButton);

        MultipplayerLobbyBG lobbyBG = new MultipplayerLobbyBG();
        stage.addActor(lobbyBG);
        LogoActor actor = new LogoActor();
        stage.addActor(actor);;
        stage.addActor(table);
        stage.addActor(logoutButton);
        stage.addActor(backButton);

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

        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                tmp = lobbyCounter+ "    ";
                tmp2 = lobbyCounter+"    ";
                s="";
                gMP.getStringResponse();
                s = gMP.getPos();
                System.out.println("s "+s);
                if(s!=null) {
                    Scanner scan = new Scanner(s);
                    count = 0;
                    while (scan.hasNext()) {
                        //first result
                        System.out.println(count);
                        if (count < 3) {
                            if (scan.hasNextInt()) {
                                int d = scan.nextInt();
                                tmp = tmp + d + "     ";
                            } else if(scan.hasNext()) {
                                tmp = tmp + scan.next() + "    ";
                            }
                            //System.out.println("TMP= "+tmp);
                        }
                        //second user
                        else {
                            if (scan.hasNextInt()) {
                                int d = scan.nextInt();
                                tmp2 = tmp2 + d + "    ";
                            } else if(scan.hasNext()) {
                                tmp2 = tmp2 + scan.next() + "    ";
                            }


                        }
                        count++;
                    }
                    scan.close();
                }
                if(count<3){
                    lobbyCounter=1;
                }
                if(count>=4){
                    lobbyCounter=2;
                }
                if(tmp.equals(tmp2)||tmp2.equals("0")||tmp2.equals("1")||tmp2.equals("2")){
                    tmp2= "";
                }
                if(tmp.equals("0")||tmp.equals("1")||tmp.equals("2")){
                    tmp = "";
                }
                label1.setText(tmp);
                label2.setText(tmp2);
                vGroup.addActor(label1);
                vGroup.addActor(label2);
                vGroup.setPosition(150,100);
                vGroup.setSize(100,100);
                stage.addActor(vGroup);
                timer.clear();
            }
        },3);

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

    public void calculateStartingCoords(String s){
        if(s==null){
            return;
        }
        Scanner scan = new Scanner(s);
        String tmpUsername="";
        String tmpDirection="South";
        int tmpX=-100;
        int tmpY=-100;
        System.out.println("TESTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
        while(scan.hasNext()){
           if(scan.hasNext()){
               tmpDirection = scan.next();
           }
           if(scan.hasNextInt()){
               int tmp = scan.nextInt();
               //if tmp == -100 then this player is the first to enter the game start at 31,28
               if(tmp == -100){
                   sendGetMultiplayercoords.SendMultiplayerStuff(account.GetUserName(),account.GetUserPassword(),31,28,"South");
                   tmpX = 39;
                   tmpY = 29;
                   tmpDirection = "South";
               }
               else{
                   sendGetMultiplayercoords.SendMultiplayerStuff(account.GetUserName(),account.GetUserPassword(),46,28,"South");
                   tmpX = 38;
                   tmpY = 29;
                   tmpDirection = "South";
               }
           }
        }
        System.out.println("eeeeeeeeeeeeeeeeeeeeeee "+tmpY);
        System.out.println(tmpX);
        System.out.println(tmpDirection);
        stage.dispose();
        game.SetMultiplayerGameScreen(tmpX,tmpY,tmpDirection);

    }
}
