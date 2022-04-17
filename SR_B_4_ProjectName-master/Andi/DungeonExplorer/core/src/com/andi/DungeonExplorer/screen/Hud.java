package com.andi.DungeonExplorer.screen;

import com.andi.DungeonExplorer.actor.PlayerActor;

import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;

import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Andi Li on 11/22/2017.
 */
public class Hud implements Disposable{

    //Scene2D.ui Stage and its own Viewport for HUD
    public Stage stage;
    private Viewport viewport;



    //Scene2D widgets

    private Label hpLabel;
    private  Integer hp;

    private Label worldLabel;
    private String world;
    private PlayerActor player;

    /**
     * Hud Constructor
     * @param sb
     * @param player
     */
    public Hud(SpriteBatch sb,PlayerActor player){
        this.player = player;
        //define our tracking variables


        //setup the HUD viewport using a new camera seperate from our gamecam
        //define our stage using that viewport and our games spritebatch
        viewport = new ScreenViewport();
        stage = new Stage(viewport, sb);

        //define a table used to organize our hud's labels
        Table table = new Table();
        //Top-Align table
        table.top();
        //make the table fill the entire stage
        table.setFillParent(true);

        //define our labels using the String, and a Label style consisting of a font and color

        hpLabel = new Label(""+player.character.getHealth(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //add our labels to our table, padding the top, and giving them all equal width with expandX
        table.add(worldLabel).expandX().padTop(15);

        table.add(hpLabel).expandX().padTop(15);
        //add a second row to our table
        table.row();

        table.add(hpLabel).expandX();
        table.add(worldLabel).expandX();


        //add our table to the stage
        stage.addActor(table);

    }

    /**
     * updates the hp
     */
    public void getHp(){
        hp=player.character.getHealth();
        hpLabel.setText("HP: "+ hp);
    }

    /**
     * updates the world
     */
    public void getWorld(){
        world = player.getWorld().getName();
        worldLabel.setText("WORLD: "+world);
    }

    @Override
    public void dispose() { stage.dispose(); }


}
