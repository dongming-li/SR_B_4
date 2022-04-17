package com.andi.DungeonExplorer.screen;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

import com.andi.DungeonExplorer.DungeonExplorer;
import com.andi.DungeonExplorer.controller.DialogueController;
import com.andi.DungeonExplorer.controller.IndexInteractionController;
import com.andi.DungeonExplorer.controller.IndexMovementController;
import com.andi.DungeonExplorer.dialogue.Dialogue;
import com.andi.DungeonExplorer.homeUI.Account.Account;
import com.andi.DungeonExplorer.map.Camera;
import com.andi.DungeonExplorer.map.DIRECTION;
import com.andi.DungeonExplorer.world.WorldLoader;
import com.andi.DungeonExplorer.world.World;
import com.andi.DungeonExplorer.world.editor.EditorUtil;
import com.andi.DungeonExplorer.world.events.CutsceneEvent;
import com.andi.DungeonExplorer.world.events.CutsceneEventQueuer;
import com.andi.DungeonExplorer.world.events.CutscenePlayer;
import com.andi.DungeonExplorer.world.editor.Index;
import com.andi.DungeonExplorer.screen.renderer.EventQueueRenderer;
import com.andi.DungeonExplorer.screen.renderer.WorldRenderer;
import com.andi.DungeonExplorer.screen.transition.Action;
import com.andi.DungeonExplorer.screen.transition.FadeInTransition;
import com.andi.DungeonExplorer.screen.transition.FadeOutTransition;
import com.andi.DungeonExplorer.ui.DialogueBox;
import com.andi.DungeonExplorer.ui.OptionBox;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * @author brytonhayes
 *
 * Screen used to edit maps
 * //TODO add functionality for saving and loading
 *
 */
public class EditorScreen extends AbstractScreen implements CutscenePlayer, CutsceneEventQueuer {

    private String worldName;

    private InputMultiplexer multiplexer;                       //process multiplexer
    private DialogueController dialogueController;              //controls dialogue
    private IndexMovementController indexController;            //controls movement
    private IndexInteractionController interactionController;   //controls interactions

    private EditorUtil editorUtil;

    //multiple worlds
    private HashMap<String, World> worlds = new HashMap<String, World>();
    private World world;        //current world
    private Index index;        //index used to edit map
    private Camera camera;
    private Dialogue dialogue;
    private WorldLoader worldLoader;
    private Queue<CutsceneEvent> eventQueue = new ArrayDeque<CutsceneEvent>();
    private CutsceneEvent currentEvent;

    private SelectionScreen selectionScreen;        //drop down menu

    private SpriteBatch batch;
    private Viewport gameViewport;

    private WorldRenderer worldRenderer;
    private EventQueueRenderer queueRenderer;

    private int uiScale = 2;

    private Stage uiStage;
    private Table root;
    private DialogueBox dialogueBox;
    private OptionBox optionsBox;

    private Account account;

    public EditorScreen(DungeonExplorer app, Account account, String worldName) {
        super(app);
        this.worldName = worldName;
        this.account=account;
        gameViewport = new ScreenViewport();
        batch = new SpriteBatch();

        //grab textures
        TextureAtlas atlas = app.getAssetManager().get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
        TextureRegion cursor = atlas.findRegion("redSquare");

        //add available worlds
        worlds.put("Tutorial", worldLoader.outdoorLevel(app.getAssetManager(), this, this));
        worlds.put("Dungeon Entrance", worldLoader.startRoom(app.getAssetManager(), this, this));
        worlds.put("upperStar", worldLoader.upperStar(app.getAssetManager(), this, this));
        worlds.put("mainRoom", worldLoader.mainRoom(app.getAssetManager(), this, this));
        worlds.put("topRightStar", worldLoader.topRightStar(app.getAssetManager(), this, this));
        worlds.put("bottomRightStar", worldLoader.bottomRightStar(app.getAssetManager(), this, this));
        worlds.put("cleanSlate", worldLoader.cleanSlate(app.getAssetManager(), this, this, null));
        worlds.put("Custom 1", worldLoader.custom1(app.getAssetManager(), this, this, null));
        worlds.put("Custom 2", worldLoader.custom2(app.getAssetManager(), this, this, null));
        worlds.put("Custom 3", worldLoader.custom3(app.getAssetManager(), this, this, null));
        worlds.put("Custom 4", worldLoader.custom4(app.getAssetManager(), this, this, null));
        worlds.put("Custom 5", worldLoader.custom5(app.getAssetManager(), this, this, null));
        world = worlds.get(worldName);

        editorUtil = new EditorUtil(world);
        editorUtil.internalReadTileFile();
        editorUtil.internalReadObjectFile();
        editorUtil.internalReadActorFile();


        camera = new Camera();

        index = new Index(world, 10, 8, cursor);
        world.addIndex(index);

        //index.loadMap();

        initUI();

        multiplexer = new InputMultiplexer();
        selectionScreen = new SelectionScreen(app,batch, index);

        indexController = new IndexMovementController(index);
        dialogueController = new DialogueController(dialogueBox, optionsBox);
        interactionController = new IndexInteractionController(index);
        multiplexer.addProcessor(0, dialogueController);
        multiplexer.addProcessor(1, indexController);
        multiplexer.addProcessor(2, interactionController);
        multiplexer.addProcessor(3, selectionScreen.stage);

        worldRenderer = new WorldRenderer(getApp().getAssetManager(), world);
        queueRenderer = new EventQueueRenderer(app.getSkin(), eventQueue);

        index.teleport(0,0);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void update(float delta) {

        while (currentEvent == null || currentEvent.isFinished()) { // no active event
            if (eventQueue.peek() == null) { // no event queued up
                currentEvent = null;
                break;
            } else {					// event queued up
                currentEvent = eventQueue.poll();
                currentEvent.begin(this);
            }
        }

        if (currentEvent != null) {
            currentEvent.update(delta);
        }

        if (currentEvent == null) {
            indexController.update(delta);
        }

        dialogueController.update(delta);

        if (!dialogueBox.isVisible()) {
            camera.update(index.getWorldX()+0.5f, index.getWorldY()+0.5f);
            world.update(delta);
        }
        uiStage.act(delta);
    }

    @Override
    public void render(float delta) {
        gameViewport.apply();
        batch.begin();
        //render world
        worldRenderer.render(batch, camera);
        queueRenderer.render(batch, currentEvent);
        batch.end();
        //render selection screen
        selectionScreen.stage.act();
        selectionScreen.tileBox.getScrollPane().act(delta);
        selectionScreen.objBox.getScrollPane().act(delta);
        selectionScreen.stage.draw();
        uiStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
        uiStage.getViewport().update(width/uiScale, height/uiScale, true);
        gameViewport.update(width, height);
        selectionScreen.stage.getViewport().update(width, height, true);
    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiplexer);
        if (currentEvent != null) {
            currentEvent.screenShow();
        }

    }

    private void initUI() {
        uiStage = new Stage(new ScreenViewport());
        uiStage.getViewport().update(Gdx.graphics.getWidth()/uiScale, Gdx.graphics.getHeight()/uiScale, true);
        //uiStage.setDebugAll(true);

        root = new Table();
        root.setFillParent(true);
        uiStage.addActor(root);

        dialogueBox = new DialogueBox(getApp().getSkin());
        dialogueBox.setVisible(false);

        optionsBox = new OptionBox(getApp().getSkin());
        optionsBox.setVisible(false);

        Table dialogTable = new Table();
        dialogTable.add(optionsBox)
                .expand()
                .align(Align.right)
                .space(8f)
                .row();
        dialogTable.add(dialogueBox)
                .expand()
                .align(Align.bottom)
                .space(8f)
                .row();


        root.add(dialogTable).expand().align(Align.bottom);
    }

    /**
     * change to a new world
     * @param newWorld  desired world
     * @param x new index x coordinate
     * @param y new index y coordinate
     */
    public void changeWorld(World newWorld, int x, int y) {
        index.changeWorld(newWorld, x, y);
        this.world = newWorld;
        this.worldRenderer.setWorld(newWorld);
        this.camera.update(index.getWorldX()+0.5f, index.getWorldY()+0.5f);
    }

    @Override
    public void changeLocation(final World newWorld, final int x, final int y, final DIRECTION facing, Color color) {
        getApp().startTransition(
                this,
                this,
                new FadeOutTransition(0.8f, color, getApp().getTweenManager(), getApp().getAssetManager()),
                new FadeInTransition(0.8f, color, getApp().getTweenManager(), getApp().getAssetManager()),
                new Action() {
                    @Override
                    public void action() {
                        changeWorld(newWorld, x, y);
                    }
                });
    }

    @Override
    public World getWorld(String worldName) {
        return worlds.get(worldName);
    }

    @Override
    public void queueEvent(CutsceneEvent event) {
        eventQueue.add(event);
    }

}