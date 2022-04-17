package com.andi.DungeonExplorer;


import com.andi.DungeonExplorer.homeUI.Account.Account;
import com.andi.DungeonExplorer.homeUI.Screens.Campaign;
import com.andi.DungeonExplorer.homeUI.Screens.EditMaps;
import com.andi.DungeonExplorer.homeUI.Screens.LCampaign;
import com.andi.DungeonExplorer.homeUI.Screens.LMainScreen;
import com.andi.DungeonExplorer.homeUI.Screens.LMultiplayer;
import com.andi.DungeonExplorer.homeUI.Screens.Login;
import com.andi.DungeonExplorer.homeUI.Screens.LoginAndRegister;
import com.andi.DungeonExplorer.homeUI.Screens.Offline;
import com.andi.DungeonExplorer.homeUI.Screens.PlayMaps;
import com.andi.DungeonExplorer.homeUI.Screens.Register;
import com.andi.DungeonExplorer.screen.CustomGameScreen;
import com.andi.DungeonExplorer.screen.EditorScreen;
import com.andi.DungeonExplorer.screen.MultiplayerGameScreen;
import com.andi.DungeonExplorer.screen.TransitionScreen;
import com.andi.DungeonExplorer.screen.transition.Action;
import com.andi.DungeonExplorer.screen.transition.Transition;
import com.andi.DungeonExplorer.util.SkinGenerator;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.andi.DungeonExplorer.screen.AbstractScreen;
import com.andi.DungeonExplorer.screen.GameScreen;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

/**
 * Topmost class of the game. Logic is delegated to Screens from here.
 */
public class DungeonExplorer extends Game {
	
	private GameScreen gameScreen;
	private CustomGameScreen customGameScreen;

	private TransitionScreen transitionScreen;
	private EditorScreen editorScreen;

	private EditMaps editMapsScreen;
	private PlayMaps playMapsScreen;
	
	private AssetManager assetManager;
	
	private Skin skin;
	
	private TweenManager tweenManager;
	
	private ShaderProgram overlayShader;
	private ShaderProgram transitionShader;

	//UI screens
	private LoginAndRegister loginAndRegisterScreen;
	private Login loginScreen;
	private Register registerScreen;
	private Offline offlineScreen;
	private Campaign campaignScreen;
    private LMainScreen lMainScreen;
    private LCampaign lCampaign;
    private LMultiplayer lMultiplayer;
    private MultiplayerGameScreen multiplayerGameScreen;

	//account
	Account account;

	@Override
	public void create() {
		/*
		 * LOADING SHADERS
		 */
		ShaderProgram.pedantic = false;
		overlayShader = new ShaderProgram(
				Gdx.files.internal("res/shaders/overlay/vertexshader.txt"), 
				Gdx.files.internal("res/shaders/overlay/fragmentshader.txt"));
		if (!overlayShader.isCompiled()) {
			System.out.println(overlayShader.getLog());
		}
		transitionShader = new ShaderProgram(
				Gdx.files.internal("res/shaders/transition/vertexshader.txt"), 
				Gdx.files.internal("res/shaders/transition/fragmentshader.txt"));
		if (!transitionShader.isCompiled()) {
			System.out.println(transitionShader.getLog());
		}
		
		/*
		 * SETTING UP TWEENING
		 */
		tweenManager = new TweenManager();
	
		
		/*
		 * LOADING ASSETS
		 */
		assetManager = new AssetManager();
		assetManager.load("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
		assetManager.load("res/graphics_packed/ui/uipack.atlas", TextureAtlas.class);
		assetManager.load("res/graphics/statuseffect/white.png",Texture.class);


		
		for (int i = 0; i < 13; i++) {
			assetManager.load("res/graphics/transitions/transition_"+i+".png", Texture.class);
		}
		assetManager.load("res/font/small_letters_font.fnt", BitmapFont.class);
		
		assetManager.finishLoading();
		
		skin = SkinGenerator.generateSkin(assetManager);
		

		

		
		transitionScreen = new TransitionScreen(this);
		//Sets to the LoginAndRegister UI Screen
		account = new Account();
		this.setLoginAndRegisterScreen();
	}
	
	@Override
	public void render() {
		//System.out.println(Gdx.graphics.getFramesPerSecond());
		
		/* UPDATE */
		tweenManager.update(Gdx.graphics.getDeltaTime());
		if (getScreen() instanceof AbstractScreen) {
			((AbstractScreen)getScreen()).update(Gdx.graphics.getDeltaTime());
		}
		
		/* RENDER */
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		getScreen().render(Gdx.graphics.getDeltaTime());
	}
	
	public AssetManager getAssetManager() {
		return assetManager;
	}
	
	public Skin getSkin() {
		return skin;
	}
	
	public TweenManager getTweenManager() {
		return tweenManager;
	}
	
	public GameScreen getGameScreen() {
		return gameScreen;
	}

	public EditorScreen getEditorScreen() {
		return editorScreen;
	}
	

	public void startTransition(AbstractScreen from, AbstractScreen to, Transition out, Transition in, Action action) {
		transitionScreen.startTransition(from, to, out, in, action);
	}
	
	public ShaderProgram getOverlayShader() {
		return overlayShader;
	}
	
	public ShaderProgram getTransitionShader() {
		return transitionShader;
	}


	/**
	 * Changes the view of the app (What the user sees) to the given screen
	 */
	public void setLoginAndRegisterScreen(){
		loginAndRegisterScreen = new LoginAndRegister(this,account);
		setScreen(loginAndRegisterScreen);
	}
	/**
	 * Changes the view of the app (What the user sees) to the given screen
	 */
	public void SetLoginScreen(){
		loginScreen = new Login(this, account);
		setScreen(loginScreen);
	}
	public void SetRegisterScreen(){
		registerScreen = new Register(this,account);
		setScreen(registerScreen);
	}
	/**
	 * Changes the view of the app (What the user sees) to the given screen
	 */
	public void SetOfflineScreen(){
		offlineScreen = new Offline(this,account);
		setScreen(offlineScreen);
	}
	/**
	 * Changes the view of the app (What the user sees) to the given screen
	 */
	public void SetCampaignScreen(){
		campaignScreen = new Campaign(this,account);
		setScreen(campaignScreen);
	}
	/**
	 * Changes the view of the app (What the user sees) to the given screen
	 */
	public void SetGameScreen(){
		gameScreen = new GameScreen(this,account);
		this.setScreen(gameScreen);
	}
	/**
	 * Changes the view of the app (What the user sees) to the given screen
	 */
	public void SetCustomGameScreen(String map){
		customGameScreen = new CustomGameScreen(this,account,map);
		this.setScreen(customGameScreen);
	}

	/**
	 * Changes the view of the app (What the user sees) to the given screen
	 */
	public void SetEditMapsScreen(){
		editMapsScreen = new EditMaps(this,account);
		this.setScreen(editMapsScreen);
	}

	/**
	 * Changes the view of the app (What the user sees) to the given screen
	 */
	public void SetPlayMapsScreen(){
		playMapsScreen = new PlayMaps(this,account);
		this.setScreen(playMapsScreen);
	}


	public void SetEditorScreen(String worldName){
		editorScreen = new EditorScreen(this,account,worldName);
		this.setScreen(editorScreen);
	}

	/**
	 * Changes the view of the app (What the user sees) to the given screen
	 */
	public void SetLMainScreen(){
        lMainScreen = new LMainScreen(this,account);
        this.setScreen(lMainScreen);
    }

	/**
	 * Changes the view of the app (What the user sees) to the given screen
	 */
    public void SetLCampaign(){
        lCampaign = new LCampaign(this,account);
        this.setScreen(lCampaign);
    }
	/**
	 * Changes the view of the app (What the user sees) to the given screen
	 */
    public void SetLMultiplayerScreen(){
        lMultiplayer = new LMultiplayer(this,account);
        this.setScreen(lMultiplayer);
    }
	/**
	 * Changes the view of the app (What the user sees) to the given screen
	 */
    public void SetMultiplayerGameScreen(int tmpX,int tmpY,String tmpDirection){
        multiplayerGameScreen = new MultiplayerGameScreen(this,account,tmpX,tmpY,tmpDirection);
        this.setScreen(multiplayerGameScreen);
    }
}
