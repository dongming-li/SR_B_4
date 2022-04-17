package com.andi.DungeonExplorer.homeUI.Screens;

import com.andi.DungeonExplorer.DungeonExplorer;
import com.andi.DungeonExplorer.homeUI.Account.Account;
import com.andi.DungeonExplorer.homeUI.Actors.LogoActor;
import com.andi.DungeonExplorer.map.DIRECTION;
import com.andi.DungeonExplorer.world.editor.EditorUtil;
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
 * Created by Ntcarter on 11/4/2017.
 */

/**
 * Creates a Offline ui screen that users can interact with and change screens
 */
public class Offline implements Screen {

    private DungeonExplorer game;
    private Skin skin;
    private Stage stage;
    private SpriteBatch batch;
    private Sprite sprite;
    private Table table;
    private Account account;
    private Table table2;

    public Offline(DungeonExplorer game, Account account){
        this.account = account;
        this.game = game;
    }

    /**
     * Creates and sets the initial screen
     */
    @Override
    public void show() {
        ScreenViewport viewPort = new ScreenViewport();
        stage = new Stage(viewPort);
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.center|Align.top);
        table.setPosition(0, Gdx.graphics.getHeight()/2-50);

        table2 = new Table();
        table2.setWidth(stage.getWidth());
        table2.align(Align.center);
        table2.setPosition(0, Gdx.graphics.getHeight()/2-100);


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


        final TextButton campaignButton = new TextButton("Campaign", skin, "default");
        campaignButton.setWidth(200);
        campaignButton.setHeight(100);
        campaignButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.SetCampaignScreen();
            }
        });

        final TextButton myMapsButton = new TextButton("Custom Maps", skin, "default");
        myMapsButton.setWidth(200);
        myMapsButton.setHeight(100);
        myMapsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.SetPlayMapsScreen();

            }
        });

        final TextButton editorButton = new TextButton("Map Editor", skin, "default");
        editorButton.setWidth(200);
        editorButton.setHeight(100);
        editorButton.addListener(new ClickListener() {
             @Override
             public void clicked(InputEvent event, float x, float y) {
                 game.SetEditMapsScreen();
             }
         });


        table.add(myMapsButton).padRight(30);
        table.add(campaignButton);
        table2.add(editorButton);
        stage.addActor(table);
        stage.addActor(table2);
        stage.addActor(backButton);
        LogoActor logo = new LogoActor();
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