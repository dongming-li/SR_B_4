package com.andi.DungeonExplorer.world.InteractableItems;

import com.andi.DungeonExplorer.actor.PlayerActor;
import com.andi.DungeonExplorer.dialogue.Dialogue;
import com.andi.DungeonExplorer.dialogue.LinearDialogueNode;
import com.andi.DungeonExplorer.actor.Actor;
import com.andi.DungeonExplorer.world.WorldObject;
import com.andi.DungeonExplorer.world.events.CrystalEvent;
import com.andi.DungeonExplorer.world.events.CutsceneEventQueuer;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

/**
 * Created by Andi Li on 11/8/2017.
 * Crystal to be activated
 */

public class Crystal extends WorldObject {
    private int crystalId;
    private boolean crystalChecker;
    private float animationTimer = 0f;
    private float animationTime = 0.5f;
    private STATE state = STATE.RED;
    private Animation openAnimation;
    private CutsceneEventQueuer broadcaster;
    private boolean activated;

    public enum STATE {
        PURPLE,
        RED,
        CHANGING,
        ;
    }



    public Crystal(int x, int y, Animation openAnimation, int chestID, CutsceneEventQueuer broadcaster) {
        super("crystal",x, y, false, (TextureRegion) openAnimation.getKeyFrames()[0], 1f, 1f, new GridPoint2(0,0),true,0,true);
        this.openAnimation = openAnimation;
        this.crystalId = chestID;
        this.broadcaster = broadcaster;


    }

    @Override
    public void actorInteract(Actor a) {

       //a.getWorld().addPortal(a.getWorld(),18,0,"hole",1f,1f);
        if(a instanceof PlayerActor){
            interactCrystal(a);
            if(crystalChecker == true){
                crystalChecker= false;
                broadcaster.queueEvent(new CrystalEvent(this,true));
            }
            int counter = 0;
            CrystalPiece crystal = null;
          counter = a.getInventory().checkCrystals(crystal);

            if(counter == 5){
                a.getInventory().dropCrystals(crystal);
                a.getWorld().addPortal(a.getWorld(),18,0,"hole",1f,1f);
            }
        }

    }

    /**
     * checks the conditions for the crystal to activate
     * @param a
     * @return
     */
    private Crystal interactCrystal(Actor a){
        int checkId = crystalId;
        CrystalPiece key = a.getInventory().getCrystalPiece(checkId);
        if(key == null || key.getCrystalId()!=checkId){
            setDialogueStarter(true);
            crystalChecker = false;
            Dialogue closed = new Dialogue();
            LinearDialogueNode node1 = new LinearDialogueNode("You have no piece or this is the wrong piece", 0,false);
            closed.addNode(node1);
            this.setDialogue(closed);

            return this;

        }
        else if(this.state== STATE.PURPLE){
            setDialogueStarter(true);
            crystalChecker = true;
            Dialogue closed = new Dialogue();
            LinearDialogueNode node1 = new LinearDialogueNode("This Crystal is Transformed Already", 0,false);
            closed.addNode(node1);
            this.setDialogue(closed);
        }
        else if(key.getCrystalId() == checkId){
            setDialogueStarter(true);
            crystalChecker = true;
            Dialogue closed = new Dialogue();
            LinearDialogueNode node1 = new LinearDialogueNode("The Crystal Transforms", 0,false);
            closed.addNode(node1);
            this.setDialogue(closed);
            this.activated = true;
        }


        return null;
    }
    @Override
    public void update(float delta) {
        super.update(delta);

        if ( state == STATE.CHANGING) {
            animationTimer += delta;
            if (animationTimer >= animationTime) {
                if (state == STATE.CHANGING) {
                    state = STATE.PURPLE;
                }
                animationTimer = 0f;
            }
        }
    }
    public STATE getState() {
        return state;
    }

    public TextureRegion getSprite() {
        if (state == STATE.PURPLE) {
            return (TextureRegion) openAnimation.getKeyFrames()[3];
        } else if (state == STATE.RED) {
            return (TextureRegion)  openAnimation.getKeyFrames()[0];
        } else if (state == STATE.CHANGING) {
            return (TextureRegion)  openAnimation.getKeyFrame(animationTimer);
        }
        return null;
    }
    public void change(){
        state = STATE.CHANGING;
    }
    public boolean isActivated() {
        return activated;
    }
}
