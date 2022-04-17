package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.Screens.Campaign;
import com.mygdx.game.Screens.Login;
import com.mygdx.game.Screens.LoginAndRegister;
import com.mygdx.game.Screens.Offline;
import com.mygdx.game.Screens.Register;

public class MyGdxGame extends Game {
	private LoginAndRegister loginAndRegisterScreen;
	private Login loginScreen;
	private Register registerScreen;
	private Offline offlineScreen;
	private Campaign campaignScreen;

	@Override
	public void create() {
		this.setLoginAndRegisterScreen();
	}

	public void setLoginAndRegisterScreen(){
		loginAndRegisterScreen = new LoginAndRegister(this);
		setScreen(loginAndRegisterScreen);
	}
	public void SetLoginScreen(){
		loginScreen = new Login(this);
		setScreen(loginScreen);
	}
	public void SetRegisterScreen(){
		registerScreen = new Register(this);
		setScreen(registerScreen);
	}
	public void SetOfflineScreen(){
		offlineScreen = new Offline(this);
		setScreen(offlineScreen);
	}
	public void SetCampaignScreen(){
		campaignScreen = new Campaign(this);
		setScreen(campaignScreen);
	}
}
