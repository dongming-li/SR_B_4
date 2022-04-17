package com.andi.DungeonExplorer.world.events;

import com.andi.DungeonExplorer.world.InteractableItems.Door;

public class DoorEvent extends CutsceneEvent {
	
	private boolean opening;
	private Door door;
	
	private boolean finished = false;
	
	public DoorEvent(Door door, boolean opening) {
		this.door = door;
		this.opening = opening;
		
	}
	
	@Override
	public void begin(CutscenePlayer player) {
		super.begin(player);
		if (door.getState() == Door.STATE.OPEN && opening == false) {
			door.close();
		} else if (door.getState() == Door.STATE.CLOSED && opening == true) {
			door.open();
		}
	}

	@Override
	public void update(float delta) {
		if (opening == true && door.getState() == Door.STATE.OPEN) {
			finished = true;
		} else if (opening == false && door.getState() == Door.STATE.CLOSED) {
			finished = true;
		}
	}

	@Override
	public boolean isFinished() {
		return finished;
	}

	@Override
	public void screenShow() {}

}
