package com.andi.DungeonExplorer.world.events;

import com.andi.DungeonExplorer.world.InteractableItems.Crystal;

/**
 * Created by Andi Li on 11/9/2017.
 */

public class CrystalEvent extends CutsceneEvent {
    private boolean opening;
    private Crystal crystal;
    private boolean finished = false;

    public CrystalEvent(Crystal crystal, boolean changing){
        this.crystal = crystal;
        this.opening = changing;
    }


    @Override
    public void begin(CutscenePlayer player) {
        super.begin(player);
        if (crystal.getState()== Crystal.STATE.RED && opening == true) {
            crystal.change();
        }
    }

    @Override
    public void update(float delta) {
        if (opening == true && crystal.getState() == Crystal.STATE.PURPLE) {
            finished = true;
        }
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public void screenShow() {

    }
}
