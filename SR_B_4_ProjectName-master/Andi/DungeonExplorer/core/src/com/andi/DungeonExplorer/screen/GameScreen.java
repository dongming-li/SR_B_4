package com.andi.DungeonExplorer.screen;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.andi.DungeonExplorer.DungeonExplorer;
import com.andi.DungeonExplorer.actor.Inventory.Inventory;
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
import com.andi.DungeonExplorer.world.Equipment.Sword;
import com.andi.DungeonExplorer.world.Equipment.Weapon;
import com.andi.DungeonExplorer.world.WorldLoader;
import com.andi.DungeonExplorer.world.World;
import com.andi.DungeonExplorer.world.editor.EditorUtil;
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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.lwjgl.Sys;

import static com.andi.DungeonExplorer.battle.moves.Attack.damageType.IMPACT;

/**
 * the current game screen
 */
public class GameScreen extends AbstractScreen implements CutscenePlayer, CutsceneEventQueuer {

	private InputMultiplexer multiplexer;
	private DialogueController dialogueController;
	private ActorMovementController playerController;
	private InteractionController interactionController;

	public EditorUtil editorUtil;

	private Hud hud;
	private InventoryScreen invScreen;

	private HashMap<String, World> worlds = new HashMap<String, World>();
	private World world;
	private PlayerActor player;
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

	/**
	 * Game Screen Constructor
	 * @param app
	 * @param account
	 */
	public GameScreen(DungeonExplorer app, Account account) {
		super(app);
		this.account = account;
		gameViewport = new ScreenViewport();
		batch = new SpriteBatch();

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

		worlds.put("C_2", WorldLoader.Campaign2(app.getAssetManager(), this, this));
		worlds.put("Tutorial", WorldLoader.outdoorLevel(app.getAssetManager(), this, this));
		worlds.put("Dungeon Entrance", WorldLoader.startRoom(app.getAssetManager(), this, this));
		worlds.put("upperStar", WorldLoader.upperStar(app.getAssetManager(), this, this));
		worlds.put("mainRoom", WorldLoader.mainRoom(app.getAssetManager(), this, this));
		worlds.put("topRightStar", WorldLoader.topRightStar(app.getAssetManager(), this, this));
		worlds.put("bottomRightStar", WorldLoader.bottomRightStar(app.getAssetManager(), this, this));
		worlds.put("topLeftStar", WorldLoader.topLeftStar(app.getAssetManager(), this, this));
		worlds.put("bottomLeftStar", WorldLoader.bottomLeftStar(app.getAssetManager(), this, this));
		worlds.put("Reaper Room", WorldLoader.reaperRoom(app.getAssetManager(), this, this));
		worlds.put("Yeti Room", WorldLoader.yetiRoom(app.getAssetManager(), this, this));
		worlds.put("cleanSlate", WorldLoader.cleanSlate(app.getAssetManager(), this, this, null));

		world = worlds.get("C_2");

		//editorUtil = new EditorUtil(world);
		//editorUtil.internalReadTileFile();
		//editorUtil.internalReadObjectFile();
		//editorUtil.internalReadActorFile();




		//world = worlds.get("C_2");
		world = worlds.get("Tutorial");
		//world = worlds.get("Yeti Room");
		camera = new Camera();
		//Changed starting position to test new map need to change back later before demo
		//player = new PlayerActor(world, 39, 54, mortyAnimations);
		player = new PlayerActor(world, 2, 2, mortyAnimations);
		player.character = new Character();
        player.character.owner = player;
		player.character.name = "Player";
        player.name = "Player";
		player.character.levelUp();

        Sword sword = new Sword(0, 0, null, 1, 1, null, true, new int[]{10,5,5,5,5,5,5,5,5,5}, "Magic Sword");
        player.character.equip(sword);
		//give the player a bunch of health so they don't die - reduce or remove this for testing
		player.character.maxHealth += 5000;
		player.character.heal(5000);
        //StatusEffect effect = new StatusEffect(StatusEffect.type.POISON, -1, 10);
        //player.character.AddStatus(StatusEffect.type.POISON, -1, 10, false);
		world.addActor(player);
		hud = new Hud(batch,player);
		invScreen = new InventoryScreen(batch,player);

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
	 * Update the game map based on time
	 * @param delta
	 */
	@Override
	public void update(float delta) {
        List<Actor> actors = world.getActors();
        for(Actor actor : actors){
            if(actor.character != null){
                actor.character.Update();
            }
        }
        if(player.character.health <= 0){
            //return to main screen on death
            //SelectionScreen screen = new SelectionScreen(new SpriteBatch(), new Index(world, 10, 8, null));
        }
		hud.getHp();
		hud.getWorld();
		invScreen.getinventory();
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
				Character.attack(player.character, facingActor.character, weapon.chooseAttack(dist), false);
                System.out.println(facingActor.character.name + " has " + facingActor.character.health + " health remaining");
                //counterattack if not dead
                if(facingActor == null || facingActor.character == null || !facingActor.character.alive){
                    return;
                }
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
                    //System.out.println(facingActor.name + " counterattacks!");
                    Character.attack(monster, player.character, attack, true);
                }
                else{
                    System.out.println(facingActor.name + " can't counterattack!");
                }
			}
		}
		//Swap weapons
		if(Gdx.input.isKeyJustPressed(Keys.E)){
            Inventory inv = player.inventory;
            Weapon toEquip = (Weapon)inv.SwapWeapon((Weapon)player.character.equipment.get("MainHand"));
            player.character.equip(toEquip);
            System.out.println("You equipped " + toEquip.type);
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
		/*
		if (Gdx.input.isKeyJustPressed(Keys.U)){
			player.multiplayerHandler.getPlayerPosition();
			player.teleport(player.multiplayerHandler.getxPos(), player.multiplayerHandler.getyPos());
		}*/
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
		if(player.getY()>57 && world.getMap().getTile(37,57).walkable()==true){
			world.getMap().getTile(37,57).setWalkable(false);
			world.getMap().getTile(38,57).setWalkable(false);
			world.getMap().getTile(39,57).setWalkable(false);
			world.getMap().getTile(40,57).setWalkable(false);
		}
		uiStage.act(delta);

	}
	/**
	 * Render the game screen
	 */
	@Override
	public void render(float delta) {
		gameViewport.apply();
		batch.begin();
		worldRenderer.render(batch, camera);
		//queueRenderer.render(batch, currentEvent);
		batch.end();
		hud.stage.draw();
		invScreen.stage.draw();
		uiStage.draw();

	}

	/**
	 * Resize the game screen
	 * @param width
	 * @param height
	 */
	@Override
	public void resize(int width, int height) {
		batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
		uiStage.getViewport().update(width/uiScale, height/uiScale, true);
		hud.stage.getViewport().update(width, height, true);
		invScreen.stage.getViewport().update(width, height, true);
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

	/**
	 * Intializes the ingame UI
	 */
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
	 * Changes the world that appears on the Gamescreen
	 * @param newWorld
	 * @param x
	 * @param y
	 * @param face
	 */
	public void changeWorld(World newWorld, int x, int y, DIRECTION face) {
		player.changeWorld(newWorld, x, y);
		this.world = newWorld;
		player.refaceWithoutAnimation(face);
		this.worldRenderer.setWorld(newWorld);
		this.camera.update(player.getWorldX()+0.5f, player.getWorldY()+0.5f);
	}

	/**
	 * Changes the players location when changing worlds
	 * @param newWorld
	 * @param x
	 * @param y
	 * @param facing
	 * @param color
	 */
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

	/**
	 * Gets the world that the game screen is displaying
	 * @param worldName
	 * @return
	 */
	@Override
	public World getWorld(String worldName) {
		return worlds.get(worldName);
	}

	/**
	 * Queues events for the game screen to show
	 * @param event
	 */
	@Override
	public void queueEvent(CutsceneEvent event) {
		eventQueue.add(event);
	}


}
