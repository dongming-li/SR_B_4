package com.andi.DungeonExplorer.screen;

/**
 * Created by Ntcarter on 11/20/2017.
 */
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

import com.andi.DungeonExplorer.DungeonExplorer;
import com.andi.DungeonExplorer.Networking.Server.SendGetMultiplayercoords;
import com.andi.DungeonExplorer.battle.moves.Attack;
import com.andi.DungeonExplorer.controller.ActorMovementController;
import com.andi.DungeonExplorer.controller.DialogueController;
import com.andi.DungeonExplorer.controller.InteractionController;
import com.andi.DungeonExplorer.homeUI.Account.Account;
import com.andi.DungeonExplorer.map.Camera;
import com.andi.DungeonExplorer.map.DIRECTION;
import com.andi.DungeonExplorer.actor.Actor;
import com.andi.DungeonExplorer.actor.Character.Character;
import com.andi.DungeonExplorer.actor.Character.Monster;
import com.andi.DungeonExplorer.actor.PlayerActor;
import com.andi.DungeonExplorer.map.TERRAIN;
import com.andi.DungeonExplorer.map.Tile;
import com.andi.DungeonExplorer.world.Equipment.Sword;
import com.andi.DungeonExplorer.world.Equipment.Weapon;
import com.andi.DungeonExplorer.world.WorldLoader;
import com.andi.DungeonExplorer.world.World;
import com.andi.DungeonExplorer.world.events.ActorWalkEvent;
import com.andi.DungeonExplorer.world.events.CutsceneEvent;
import com.andi.DungeonExplorer.world.events.CutsceneEventQueuer;
import com.andi.DungeonExplorer.world.events.CutscenePlayer;
import com.andi.DungeonExplorer.screen.renderer.EventQueueRenderer;
import com.andi.DungeonExplorer.screen.renderer.WorldRenderer;
import com.andi.DungeonExplorer.screen.transition.Action;
import com.andi.DungeonExplorer.screen.transition.FadeInTransition;
import com.andi.DungeonExplorer.screen.transition.FadeOutTransition;
import com.andi.DungeonExplorer.ui.DialogueBox;
import com.andi.DungeonExplorer.ui.OptionBox;
import com.andi.DungeonExplorer.util.AnimationSet;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.andi.DungeonExplorer.battle.moves.Attack.damageType.IMPACT;

/**
 * Creates a new multiplayer game screen where 2 players can play together.
 */
public class MultiplayerGameScreen extends AbstractScreen implements CutscenePlayer, CutsceneEventQueuer {
    private InputMultiplexer multiplexer;
    private DialogueController dialogueController;
    private ActorMovementController playerController;
    private InteractionController interactionController;

    private HashMap<String, World> worlds = new HashMap<String, World>();
    private World world;
    private PlayerActor player;
    private PlayerActor player2;
    private Camera camera;

    private Queue<CutsceneEvent> eventQueue = new ArrayDeque<CutsceneEvent>();
    private CutsceneEvent currentEvent;

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
    private int coordX;
    private int coordY;
    private String direction;
    private SendGetMultiplayercoords sendGetMultiplayercoords;
    private String player2Coords;
    private AnimationSet mortyAnimations;
    private int player2X;
    private int player2Y;

    /**
     * Creates a new multiplayer map using the given hard coded information.
     * @param app
     * @param account
     * @param coordX
     * @param coordY
     * @param direction
     */
    public MultiplayerGameScreen(DungeonExplorer app, Account account, int coordX,int coordY,String direction) {
        super(app);
        this.account = account;
        gameViewport = new ScreenViewport();
        batch = new SpriteBatch();
        this.coordX = coordX;
        this.coordY = coordY;
        this.direction = direction;
        sendGetMultiplayercoords = new SendGetMultiplayercoords();
        player2Coords="";
        player2X = -100;
        player2Y = -100;

        TextureAtlas atlas = app.getAssetManager().get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);

      AnimationSet mortyAnimations = new AnimationSet(
                new Animation(0.3f/2f, atlas.findRegions("Morty_Walk_North"), PlayMode.LOOP_PINGPONG),
                new Animation(0.3f/2f, atlas.findRegions("Morty_Walk_South"), PlayMode.LOOP_PINGPONG),
                new Animation(0.3f/2f, atlas.findRegions("Morty_Walk_East"), PlayMode.LOOP_PINGPONG),
                new Animation(0.3f/2f, atlas.findRegions("Morty_Walk_West"), PlayMode.LOOP_PINGPONG),
                atlas.findRegion("Morty_Stand_North"),
                atlas.findRegion("Morty_Stand_South"),
                atlas.findRegion("Morty_Stand_East"),
                atlas.findRegion("Morty_Stand_West"),
              atlas.findRegion("Morty_Dying_North"),
              atlas.findRegion("Morty_Dying_South"),
              atlas.findRegion("Morty_Dying_East"),
              atlas.findRegion("Morty_Dying_West")
        );

        worlds.put("snow_Map", WorldLoader.snowMultiplayerMap(app.getAssetManager(), this, this));
        world = worlds.get("snow_Map");

        camera = new Camera();
        //this player
        player = new PlayerActor(world, coordX, coordY, mortyAnimations);
        player.reface(DIRECTION.SOUTH);
        player.character = new Character();
        player.character.owner = player;
        player.character.name = "Player";
        player.name = "Player";
        player.character.levelUp();
        Sword sword = new Sword(0, 0, null, 1, 1, null, true, new int[]{10,0,0,0,0,0,0,0,0,0}, "Magic Sword");
        player.character.equip(sword);
        //give the player a bunch of health so they don't die - reduce or remove this for testing
        player.character.maxHealth += 1500;
        player.character.heal(1500);
        world.addActor(player);

        if(player.getX()==39){
            player2 = new PlayerActor(world, 38, 29, mortyAnimations);
            //player2 = new PlayerActor(world, 46, 28, mortyAnimations);
            player2.character = new Character();
            player2.character.owner = player2;
            player2.character.name = "Player2";
            player2.name = "Player2";
            player.character.maxHealth += 1500;
            player.character.heal(1500);
        }
        else{
            player2 = new PlayerActor(world, 39, 29, mortyAnimations);
            player2.character = new Character();
            player2.character.owner = player2;
            player2.character.name = "Player2";
            player2.name = "Player2";
            player.character.maxHealth += 1500;
            player.character.heal(1500);
        }
        world.addActor(player2);


        initUI();

        multiplexer = new InputMultiplexer();

        playerController = new ActorMovementController(player);
        dialogueController = new DialogueController(dialogueBox, optionsBox);
        interactionController = new InteractionController(player, dialogueController);
        multiplexer.addProcessor(0, dialogueController);
        multiplexer.addProcessor(1, playerController);
        multiplexer.addProcessor(2, interactionController);

        worldRenderer = new WorldRenderer(getApp().getAssetManager(), world);
        queueRenderer = new EventQueueRenderer(app.getSkin(), eventQueue);



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

    /**
     * updates the game state allows changes to be made to the initial state
     * @param delta
     */
    @Override
    public void update(float delta) {

        if (Gdx.input.isKeyJustPressed(Keys.K)) {
            queueEvent(new ActorWalkEvent(player, DIRECTION.NORTH));
        }
        if (Gdx.input.isKeyJustPressed(Keys.X)) {
            //attack the character you face
            //System.out.println("trying attack");
            Weapon weapon = (Weapon)player.character.equipment.get("MainHand");
            if(weapon==null){
                return;
            }
            int maxRange = 1;
            for(Map.Entry entry : weapon.attacks.entrySet()){
                Attack attack = (Attack)entry.getValue();
                if(attack.range > maxRange){
                    maxRange = attack.range;
                }
            }
            Actor facingActor = null;
            int dist;
            for(dist=1; dist<=maxRange; dist++){
                facingActor = player.getFacingActor(dist);
                if(facingActor != null){break;}
            }
            if(facingActor != null && facingActor.character != null){
                System.out.println("hp before: " + facingActor.character.health);
                Character.attack(player.character, facingActor.character, weapon.chooseAttack(dist), false);
                //counterattack
                maxRange=1;
                Monster monster = (Monster)facingActor.character;
                for(Map.Entry entry : monster.innateAttacks.entrySet()){
                    Attack attack = (Attack)entry.getValue();
                    if(attack.range > maxRange){
                        maxRange = attack.range;
                    }
                }
                if(maxRange >= dist){//if in range for a counterattack
                    Attack attack = monster.innateAttacks.get(monster.combatAI.chooseAttack(dist));
                    System.out.println(facingActor.name + " counterattacks!");
                    Character.attack(monster, player.character, attack, true);
                }
                else{
                    System.out.println(facingActor.name + " can't counterattack!");
                }
            }
        }
        /*
        if (Gdx.input.isKeyJustPressed(Keys.C)) {
            //Simulate a combat until death
            //make sure the enemy is a monster or this will not work
            //System.out.println("trying attack");
            Actor facingActor = player.getFacingActor();
            while(facingActor != null && facingActor.character != null && facingActor.character.health > 0){
                System.out.println("Enemy hp before: " + facingActor.character.health);
                System.out.println("Player hp before: " + player.character.health);
                Character.attack(player.character, facingActor.character, new Attack("Punch", 12, 75, 5, IMPACT));
                //don't want to get attacked if it's already dead
                if(facingActor.character.health <= 0){
                    break;
                }
                Monster monster = (Monster)facingActor.character;
                Character.attack(facingActor.character, player.character, monster.innateAttacks.get(monster.combatAI.chooseAttack(1)));
            }
        }*/
        if (Gdx.input.isKeyJustPressed(Keys.U)){
            player.multiplayerHandler.getPlayerPosition();
            player.teleport(player.multiplayerHandler.getxPos(), player.multiplayerHandler.getyPos());
        }
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
            playerController.update(delta);
        }

        dialogueController.update(delta);

        if (!dialogueBox.isVisible()) {
            camera.update(player.getWorldX()+0.5f, player.getWorldY()+0.5f);
            world.update(delta);
        }
        uiStage.act(delta);

        DIRECTION tmp = player.getFacing();
        String s ="";
        if(tmp.equals(DIRECTION.EAST)){
            s = "East";
        }
        if(tmp.equals(DIRECTION.SOUTH)){
            s = "South";
        }
        if(tmp.equals(DIRECTION.NORTH)){
            s= "North";
        }
        if(tmp.equals(DIRECTION.WEST)){
            s = "West";
        }
        sendGetMultiplayercoords.SendMultiplayerStuff(account.GetUserName(),account.GetUserPassword(),player.getX(),player.getY(),s);
        if(sendGetMultiplayercoords.getPos()!=null){
            String player2Pos = sendGetMultiplayercoords.getPos();
            //System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEE"+player2Pos);
            Scanner scan = new Scanner(player2Pos);
            int count = 0;
            String player2Direction = "South";
            int player2Xtmp = player2X;
            int player2Ytmp = player2Y;
            while(scan.hasNext()){
                scan.next();
                player2X= scan.nextInt();
                //System.out.println("TRTRTRTRTRTRtr"+player2X);
                player2Y = scan.nextInt();
                //System.out.println("OOPOPOPOPOPOPOP"+player2Y);
                player2Direction = scan.next();
                // System.out.println("RERERERERERERERE"+player2Direction);
            }
            scan.close();
            if(player2Direction.equals("East")){
                player2.reface(DIRECTION.EAST);
            }
            if(player2Direction.equals("North")){
                player2.reface(DIRECTION.NORTH);
            }
            if(player2Direction.equals("South")){
                player2.reface(DIRECTION.SOUTH);
            }
            if(player2Direction.equals("West")){
                player2.reface(DIRECTION.WEST);
            }

            if(player2X>player2Xtmp&&player2Xtmp!=-100){
                int tmpCount = player2X;
                while(tmpCount>player2Xtmp){
                    player2.move(DIRECTION.EAST);
                    tmpCount--;
                }
            }
            if(player2Xtmp>player2X&&player2Xtmp!=-100){
                int tmpCount = player2Xtmp;
                while(tmpCount>player2X){
                    player2.move(DIRECTION.WEST);
                    tmpCount--;
                }
            }
            if(player2Y>player2Ytmp&&player2Ytmp!=-100){
                int tmpCount = player2Y;
                while(tmpCount>player2Ytmp){
                    player2.move(DIRECTION.NORTH);
                    tmpCount--;
                }
            }
            if(player2Ytmp>player2Y&&player2Ytmp!=-100){
                int tmpCount = player2Ytmp;
                while(tmpCount>player2Y){
                    player2.move(DIRECTION.SOUTH);
                    tmpCount--;
                }
            }
            int player2XFix = player2.getX();
            int player2YFix = player2.getY();

            int count1 = 0;
            if(player2XFix>player2X&&player2X!=-100){
                while(player2XFix>player2X&&count1<100){
                    player2.move(DIRECTION.WEST);
                    player2XFix--;
                    count1++;
                }
            }
            count1=0;
            if(player2XFix<player2X&&player2X!=-100){
                while(player2XFix<player2X&&count1<100) {
                    player2.move(DIRECTION.EAST);
                    player2XFix++;
                    count1++;
                }
            }
            count1=0;
            if(player2YFix>player2Y&&player2Y!=-100){
                while(player2YFix>player2Y&&count1<100){
                    player2.move(DIRECTION.SOUTH);
                    player2XFix--;
                    count1++;
                }
            }
            count1=0;
            if(player2YFix<player2Y&&player2Y!=-100){
                while(player2YFix<player2Y&&count1<100){
                    player2.move(DIRECTION.NORTH);
                    player2XFix++;
                    count1++;
                }
            }

        }

        if(player.getY()>57 && player2.getY()>57&& world.getMap().getTile(37,57).walkable()==true){
            world.getMap().getTile(37,57).setWalkable(false);
            world.getMap().getTile(38,57).setWalkable(false);
            world.getMap().getTile(39,57).setWalkable(false);
            world.getMap().getTile(40,57).setWalkable(false);
        }

        if(player.getX()==45 &&player.getY()==78 ||player2.getX()==45 &&player2.getY()==78){
            world.getMap().setTile(new Tile("snowTile",TERRAIN.SNOW_TILE,true),45,75);
            world.getMap().setTile(new Tile("snowTile",TERRAIN.SNOW_TILE,true),45,74);
            world.getMap().setTile(new Tile("snowTile",TERRAIN.SNOW_TILE,true),45,73);
            world.getMap().setTile(new Tile("snowTile",TERRAIN.SNOW_TILE,true),45,72);
            world.getMap().setTile(new Tile("snowTile",TERRAIN.SNOW_TILE,true),45,71);
        }


    }

    /**
     * renders a new frame of the map every delta seconds.
     * @param delta
     */
    @Override
    public void render(float delta) {

        gameViewport.apply();
        batch.begin();
        worldRenderer.render(batch, camera);
        queueRenderer.render(batch, currentEvent);
        batch.end();

        uiStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
        uiStage.getViewport().update(width/uiScale, height/uiScale, true);
        gameViewport.update(width, height);
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

    public void changeWorld(World newWorld, int x, int y, DIRECTION face) {
        player.changeWorld(newWorld, x, y);
        this.world = newWorld;
        player.refaceWithoutAnimation(face);
        this.worldRenderer.setWorld(newWorld);
        this.camera.update(player.getWorldX()+0.5f, player.getWorldY()+0.5f);
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
                        changeWorld(newWorld, x, y, facing);
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
    public DialogueController getDialogueController() {
        return dialogueController;
    }


    public void getPlayer2Coordinates(int xCoord, int coordY, String direction){
        Timer time = new Timer();
        sendGetMultiplayercoords.SendMultiplayerStuff(account.GetUserName(),account.GetUserPassword(),-100,-100,"NONE");
        time.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                player2Coords = sendGetMultiplayercoords.getPos();

            }
        },4);
    }
}
