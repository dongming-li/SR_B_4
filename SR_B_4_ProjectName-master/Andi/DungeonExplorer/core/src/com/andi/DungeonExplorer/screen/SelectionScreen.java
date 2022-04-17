package com.andi.DungeonExplorer.screen;

import com.andi.DungeonExplorer.DungeonExplorer;
import com.andi.DungeonExplorer.world.editor.Index;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by brytonhayes on 11/26/17.
 *
 * Screen providing drop down menus for adding tiles/objects with map editor
 *
 */

public class SelectionScreen implements Disposable {

    final DungeonExplorer game;
    public Stage stage;
    private Viewport viewport;

    //selectbox for tiles/objects
    final SelectBox<String> tileBox;
    final SelectBox<String> objBox;

    private Skin skin;          //ui skin
    private String[] tiles;     //tile options
    private String[] items; //object and actor options
    private Index index;        //index used in editor

    /**
     * Basic Constructor
     * @param sb spritebatch
     * @param i index
     */
    public SelectionScreen(final DungeonExplorer game, SpriteBatch sb, Index i){
        this.game = game;
        this.index = i;

        viewport = new ScreenViewport();
        stage = new Stage(viewport, sb);

        //set menu items
        tiles = new String[9];
        tiles[0] = "Blank";
        tiles[1] = "Grass";
        tiles[2] = "Dungeon Floor";
        tiles[3] = "Snow Tile";
        tiles[4] = "Ice";
        tiles[5] = "Purple Wall";
        tiles[6] = "Dungeon Wall";
        tiles[7] = "Snow Wall";
        tiles[8] = "Lava";

        items = new String[4];
        items[0] = "Player";
        items[1] = "Flowers";
        items[2] = "Tree";
        items[3] = "Boulder";
        //items[4] = "Bow";


        //set skin
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        tileBox = new SelectBox<String>(skin);
        objBox = new SelectBox<String>(skin);

        //set items
        tileBox.setItems(tiles);
        objBox.setItems(items);
        tileBox.setSelected(items[0]);
        objBox.setSelected(items[0]);
        index.tileSelection = tileBox.getSelected();
        index.objSelection = objBox.getSelected();

        final TextButton backButton = new TextButton("      back      ", skin, "default");
        backButton.setWidth(50);
        backButton.setHeight(25);
        backButton.setPosition(10,10);
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.SetOfflineScreen();
            }
        });

        /*

        final TextButton tileButton = new TextButton("    Place Tile    ", skin, "default");
        backButton.setWidth(50);
        backButton.setHeight(25);
        backButton.setPosition(540,10);
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                index.addTileSelection(index.tileSelection);
            }
        });

        final TextButton objectButton = new TextButton("    Place Object    ", skin, "default");
        backButton.setWidth(50);
        backButton.setHeight(25);
        backButton.setPosition(1000,50);
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                index.addObjectSelection(index.objSelection);
            }
        });

        final TextButton saveButton = new TextButton("      save      ", skin, "default");
        backButton.setWidth(50);
        backButton.setHeight(25);
        backButton.setPosition(10,540);
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                index.saveMap();
            }
        });

        final TextButton loadButton = new TextButton("      load      ", skin, "default");
        backButton.setWidth(50);
        backButton.setHeight(25);
        backButton.setPosition(10,530);
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                index.loadMap();
            }
        });
*/
        Table table = new Table();
        Table table1 = new Table();
        table.setFillParent(true);
        table.setPosition(Gdx.graphics.getWidth()/2-200, Gdx.graphics.getHeight()/2-50);
        table1.setPosition(Gdx.graphics.getWidth()/2-50, Gdx.graphics.getHeight()/2-50);
        table.add(tileBox);
        table.add(objBox);
        stage.addActor(table);
        stage.addActor(table1);
        stage.addActor(backButton);
        //stage.addActor(saveButton);
        //stage.addActor(loadButton);
        //stage.addActor(tileButton);
        //stage.addActor(objectButton);

        Gdx.input.setInputProcessor(stage);

        tileBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                index.tileSelection = tileBox.getSelected();
            }
        });

        objBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                index.objSelection = objBox.getSelected();
            }
        });

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
