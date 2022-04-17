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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by Ntcarter on 11/18/2017.
 */

/**
 * Creates a LCampaign ui screen that users can interact with and change screens (Screen only appears once someone logs in)
 */
public class LCampaign implements Screen{

    private DungeonExplorer game;
    private Skin skin;
    private Stage stage;
    private SpriteBatch batch;
    private Sprite sprite;
    private Table table;
    private Account account;


    public LCampaign(DungeonExplorer game, Account account){
        this.account  =account;
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


        final TextButton backButton = new TextButton("      Back      ", skin, "default");
        backButton.setHeight(25);
        backButton.setWidth(60);
        backButton.setPosition(525,10);
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.SetLMainScreen();
            }
        });


        final TextButton logoutButton = new TextButton("      Logout      ", skin, "default");
        logoutButton.setHeight(25);
        logoutButton.setWidth(60);
        logoutButton.setPosition(25,10);
        logoutButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                account.LogOut();
                game.SetLoginScreen();
            }
        });

        final TextButton continueButton = new TextButton("Continue ", skin, "default");
        continueButton.setWidth(200);
        continueButton.setHeight(50);
        continueButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                //loads campaign game
            }
        });

        final TextButton newButton = new TextButton("New Game ", skin, "default");
        newButton.setWidth(50);
        newButton.setHeight(25);
        newButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.SetGameScreen();
            }
        });

        table.add(continueButton).padRight(30);
        table.add(newButton);
        stage.addActor(table);
        stage.addActor(logoutButton);
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
