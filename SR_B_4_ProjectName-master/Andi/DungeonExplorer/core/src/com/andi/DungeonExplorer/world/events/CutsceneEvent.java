package com.andi.DungeonExplorer.world.events;


/**
 * Created by Andi Li on 10/6/2017.
 */
public abstract class CutsceneEvent {
	
	private CutscenePlayer player;
	
	public void begin(CutscenePlayer player) {
		this.player = player;
	}
	
	public abstract void update(float delta);
	
	public abstract boolean isFinished();
	
	protected CutscenePlayer getPlayer() {
		return player;
	}
	
	/**
	 * Called when the CutscenePlayer is finished changing screens, as it does in a world change
	 */
	public abstract void screenShow();

}
