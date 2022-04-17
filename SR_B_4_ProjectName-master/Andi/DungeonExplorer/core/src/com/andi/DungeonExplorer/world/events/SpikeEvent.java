package com.andi.DungeonExplorer.world.events;

import com.andi.DungeonExplorer.world.InteractableItems.SpikeTrapObject;

/**
 * Created by Andi Li on 10/6/2017.
 */

public class SpikeEvent extends CutsceneEvent {
    private boolean opening;
    private SpikeTrapObject spike;

    private boolean finished = false;

    public SpikeEvent(SpikeTrapObject spike, boolean opening) {
        this.spike = spike;
        this.opening = opening;

    }

    @Override
    public void begin(CutscenePlayer player) {
        super.begin(player);
        if (spike.getState() == SpikeTrapObject.STATE.UP && opening == false) {
            spike.close();
        } else if (spike.getState() == SpikeTrapObject.STATE.DOWN && opening == true) {
            spike.open();
        }
    }

    @Override
    public void update(float delta) {
        if (opening == true && spike.getState() == SpikeTrapObject.STATE.UP) {
            finished = true;
        } else if (opening == false && spike.getState() == SpikeTrapObject.STATE.DOWN) {
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
