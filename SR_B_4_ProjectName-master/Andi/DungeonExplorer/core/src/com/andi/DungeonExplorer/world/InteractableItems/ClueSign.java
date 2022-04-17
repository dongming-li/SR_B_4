package com.andi.DungeonExplorer.world.InteractableItems;

import com.andi.DungeonExplorer.dialogue.Dialogue;
import com.andi.DungeonExplorer.dialogue.LinearDialogueNode;
import com.andi.DungeonExplorer.actor.Actor;
import com.andi.DungeonExplorer.actor.PlayerActor;
import com.andi.DungeonExplorer.world.WorldObject;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

/**
 * Created by Andi Li on 11/10/2017.
 */

public class ClueSign extends WorldObject {
    private String message;
    public ClueSign(int x, int y,TextureRegion texture,String message) {
        super("clue_sign",x, y, false, texture, 1f, 1.3f, new GridPoint2(0,0),false);
        this.message = message;
    }

    /**
     * Code gets executed when actor interacts with this object
     * @param a
     */
    @Override
    public void actorInteract(Actor a) {
        if(a instanceof PlayerActor){
            setDialogueStarter(true);
            Dialogue closed = new Dialogue();
            LinearDialogueNode node1 = new LinearDialogueNode(message, 0,false);
            closed.addNode(node1);
            this.setDialogue(closed);
        }

    }


}
