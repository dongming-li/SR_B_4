package com.andi.DungeonExplorer.world.events;

import com.andi.DungeonExplorer.actor.Actor;

/**
 * Created by Andi Li on 10/6/2017.
 */
public class ActorVisibilityEvent extends CutsceneEvent {
	
	private Actor a;
	private boolean invisible;
	
	public ActorVisibilityEvent(Actor a, boolean invisible) {
		this.a = a;
		this.invisible = invisible;
	}
	
	@Override
	public void begin(CutscenePlayer player) {
		super.begin(player);
		if (invisible) {
			a.setVisible(false);
		} else {
			a.setVisible(true);
		}
	}

	@Override
	public void update(float delta) {}

	@Override
	public boolean isFinished() {
		return true;
	}

	@Override
	public void screenShow() {}

}
