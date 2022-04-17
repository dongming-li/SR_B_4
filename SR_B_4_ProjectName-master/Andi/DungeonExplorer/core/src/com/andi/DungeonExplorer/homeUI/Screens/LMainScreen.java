package com.andi.DungeonExplorer.homeUI.Screens;

/**
 * Created by Ntcarter on 11/18/2017.
 * Classes Beginning with a L are screen that only appear after someone has logged in
 */

import com.andi.DungeonExplorer.DungeonExplorer;
import com.andi.DungeonExplorer.homeUI.Account.Account;
import com.andi.DungeonExplorer.homeUI.Actors.LogoActor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


/**
 * Creates a LmainScreen ui screen that users can interact with and change screens (Screen only appears once someone logs in)
 */
public class LMainScreen implements Screen{

    private DungeonExplorer game;
    private Skin skin;
    private Stage stage;
    private Table table;
    private Table table2;
    private SpriteBatch batch;
    private Sprite sprite;
    private Account account;

    public LMainScreen(DungeonExplorer game, Account account){
        this.game = game;
        this.account = account;
    }

    /**
     * Creates and sets the initial screen
     */
    @Override
    public void show() {
        ScreenViewport viewPort = new ScreenViewport();
        stage = new Stage(viewPort);
        table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.center|Align.top);
        table.setPosition(0, Gdx.graphics.getHeight()/2-50);


        skin = new Skin(Gdx.files.internal("uiskin.json"));
        final TextButton campaignButton = new TextButton("Campaign", skin, "default");
        final TextButton logoutButton = new TextButton("Logout", skin, "default");
        final TextButton myMapsButton = new TextButton("   MyMaps   ", skin, "default");
        final TextButton mapEditorButton = new TextButton("MapEditor", skin, "default");
        final TextButton multiplayerButton = new TextButton("Multiplayer", skin, "default");

        campaignButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.SetLCampaign();
            }
        });

        logoutButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                account.LogOut();
                game.SetLoginScreen();
            }
        });

        mapEditorButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.SetEditMapsScreen();
            }
        });


        multiplayerButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.SetLMultiplayerScreen();
            }
        });

        table.add(campaignButton).padRight(20).padBottom(20);
        table.add(myMapsButton).padBottom(20);
        table.row();
        table.add(mapEditorButton).padRight(20);
        table.add(multiplayerButton);
        logoutButton.setHeight(25);
        logoutButton.setWidth(60);
        logoutButton.setPosition(25,10);

        LogoActor actor = new LogoActor();
        stage.addActor(actor);

        stage.addActor(table);
        stage.addActor(logoutButton);
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
