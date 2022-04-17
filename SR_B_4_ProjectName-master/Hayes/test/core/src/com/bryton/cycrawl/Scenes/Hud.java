package com.bryton.cycrawl.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bryton.cycrawl.cycrawl;

/**
 * Created by brytonhayes on 9/21/17.
 */

public class Hud {
    public Stage stage;
    private Viewport viewport;

    private Integer timeCount;
    private Integer health;

    Label healthLabel;
    Label healthTag;
    Label timeLabel;
    Label timeTag;
    Label mapLabel;
    Label mapTag;

    public Hud(SpriteBatch sb){
        timeCount = 0;
        health = 100;

        viewport = new FitViewport(cycrawl.V_WIDTH, cycrawl.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        healthLabel = new Label(String.format("%03d", health), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label(String.format("%03d", timeCount), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        mapLabel = new Label("First", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        healthTag = new Label("Health", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeTag = new Label("Time", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        mapTag = new Label("Map", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(healthTag).expandX().padTop(10);
        table.add(mapTag).expandX().padTop(10);
        table.add(timeTag).expandX().padTop(10);

        table.row();
        table.add(healthLabel).expandX();
        table.add(mapLabel).expandX();
        table.add(timeLabel).expandX();

        stage.addActor(table);

    }
}
