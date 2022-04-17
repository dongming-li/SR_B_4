package com.andi.DungeonExplorer.homeUI.Screens;

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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by brytonhayes on 12/4/17.
 */

public class PlayMaps implements Screen {
    private DungeonExplorer game;
    private Skin skin;
    private Stage stage;
    private SpriteBatch batch;
    private Sprite sprite;
    private Table table;
    private Account account;
    private Table table2;

    public PlayMaps(DungeonExplorer game, Account account){
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
        table.setPosition(0, Gdx.graphics.getHeight()-100);


        final TextButton backButton = new TextButton("      back      ", skin, "default");
        backButton.setWidth(50);
        backButton.setHeight(25);
        backButton.setPosition(540,10);
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.SetOfflineScreen();
            }
        });


        final TextButton custom1 = new TextButton("Custom 1", skin, "default");
        custom1.setWidth(200);
        custom1.setHeight(100);
        custom1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.SetCustomGameScreen("Custom 1");
            }
        });

        final TextButton custom2 = new TextButton("Custom 2", skin, "default");
        custom2.setWidth(200);
        custom2.setHeight(100);
        custom2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.SetCustomGameScreen("Custom 2");
            }
        });

        final TextButton custom3 = new TextButton("Custom 3", skin, "default");
        custom3.setWidth(200);
        custom3.setHeight(100);
        custom3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.SetCustomGameScreen("Custom 3");
            }
        });

        final TextButton custom4 = new TextButton("Custom 4", skin, "default");
        custom4.setWidth(200);
        custom4.setHeight(100);
        custom4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.SetCustomGameScreen("Custom 4");
            }
        });


        final TextButton custom5 = new TextButton("Custom 5", skin, "default");
        custom5.setWidth(200);
        custom5.setHeight(100);
        custom5.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.SetCustomGameScreen("Custom 5");
            }
        });


        table.add(custom1);
        table.row();
        table.row();
        table.add(custom2);
        table.row();
        table.row();
        table.add(custom3);
        table.row();
        table.row();
        table.add(custom4);
        table.row();
        table.row();
        table.add(custom5);


        stage.addActor(table);
        stage.addActor(backButton);
        LogoActor logo = new LogoActor();
        stage.addActor(logo);
        batch = new SpriteBatch();
        sprite = new Sprite(new Texture(Gdx.files.internal("BG4.png")));
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(stage);

    }

    /**
     * Renders a new frame of the screen every Delta seconds
     *
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
