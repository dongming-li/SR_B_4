package com.andi.DungeonExplorer.world.InteractableItems;


import com.andi.DungeonExplorer.dialogue.Dialogue;
import com.andi.DungeonExplorer.dialogue.LinearDialogueNode;
import com.andi.DungeonExplorer.actor.Actor;
import com.andi.DungeonExplorer.actor.PlayerActor;
import com.andi.DungeonExplorer.world.Equipment.Key;
import com.andi.DungeonExplorer.world.WorldObject;
import com.andi.DungeonExplorer.world.events.ChestEvent;
import com.andi.DungeonExplorer.world.events.CutsceneEventQueuer;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;

/**
 * Created by Andi Li on 10/10/2017.
 */

public class Chest extends WorldObject {
    private CutsceneEventQueuer broadcaster;


    private int chestId;
    private Animation openAnimation;
    private ArrayList<WorldObject> chestsItem;

    private float animationTimer = 0f;
    private float animationTime = 0.5f;
    private boolean chestChecker;
    private STATE state = STATE.CLOSED;
    public ArrayList<WorldObject> getChestsItem() {
        return chestsItem;
    }


    public enum STATE {
        OPEN,
        CLOSED,
        OPENING,
        ;
    }




    public Chest(int x, int y, Animation openAnimation, int chestID, CutsceneEventQueuer broadcaster) {
        super("chest",x, y, false, (TextureRegion) openAnimation.getKeyFrames()[0], 1f, 1f, new GridPoint2(0,0),true,0,true);
        chestsItem = new ArrayList<WorldObject>();
        this.openAnimation = openAnimation;
        this.chestId = chestID;
        this.broadcaster = broadcaster;


    }

    /**
     * opens the chest
     */
    public void open() {
        state = STATE.OPENING;
    }



    @Override
    public void update(float delta) {
        super.update(delta);

        if (state == STATE.OPENING) {
            animationTimer += delta;
            if (animationTimer >= animationTime) {
                if (state == STATE.OPENING) {
                    state = STATE.OPEN;
                }
                animationTimer = 0f;
            }
        }
    }
    public STATE getState() {
        return state;
    }

    /**
     * Grabs the sprite that is connected with this object
     * @return
     */
    public TextureRegion getSprite() {
        if (state == STATE.OPEN) {
            return (TextureRegion) openAnimation.getKeyFrames()[3];
        } else if (state == STATE.CLOSED) {
            return (TextureRegion)  openAnimation.getKeyFrames()[0];
        } else if (state == STATE.OPENING) {
            return (TextureRegion)  openAnimation.getKeyFrame(animationTimer);
        }
        return null;
    }

    /**
     * When the actor interacts with the object
     * @param a
     */
    @Override
    public void actorInteract(Actor a) {
        if(a instanceof PlayerActor){
            interactChest(a);
            if(chestChecker == true){
                chestChecker= false;
                broadcaster.queueEvent(new ChestEvent(this,true));

            }

        }

    }

    /**
     * Checks the conditions for the chest to open
     * @param a
     * @return
     */
    private Chest interactChest( Actor a){
        int checkId = this.getChestId();
        Key key = a.getInventory().getKey(checkId);
        if(this.state== STATE.OPEN){
            Dialogue open = new Dialogue();
            LinearDialogueNode node1 = new LinearDialogueNode("You have already opened this chest",0,false);
            open.addNode(node1);
            this.setDialogue(open);
        }
        else if(checkId == 0){
            setDialogueStarter(true);
            chestChecker = true;
            if(this.getChestsItem().size() == 1){
                a.getInventory().store(this.getChestsItem().get(0),1);
                Dialogue open = new Dialogue();
                LinearDialogueNode node1 = new LinearDialogueNode("You have received:" + this.getChestsItem().get(0).type,0,false);
                open.addNode(node1);
                this.setDialogue(open);
                this.getChestsItem().clear();
                return this;
            }
            else{
                for(int i = 0;i<this.getChestsItem().size();i++){
                    a.getInventory().store(this.getChestsItem().get(i),1);
                    Dialogue open = new Dialogue();
                    LinearDialogueNode node1 = new LinearDialogueNode("You have received:" + this.getChestsItem().toString(),0,false);
                    open.addNode(node1);
                    this.setDialogue(open);
                    this.getChestsItem().clear();
                    return this;
                }
            }
        }
        else if(key == null || key.getKeyId()!=checkId){
            setDialogueStarter(true);
            chestChecker = false;
            Dialogue closed = new Dialogue();
            LinearDialogueNode node1 = new LinearDialogueNode("You need a key to open this chest.", 0,false);
            closed.addNode(node1);
            this.setDialogue(closed);

            return this;

        }
        else if(key.getKeyId() == checkId){
            setDialogueStarter(true);
            chestChecker = true;
            for(int i = 0;i<this.getChestsItem().size();i++){
                a.getInventory().store(this.getChestsItem().get(i),1);
                Dialogue open = new Dialogue();
                LinearDialogueNode node1 = new LinearDialogueNode("You have received:" + this.getChestsItem().toString(),0,false);
                open.addNode(node1);
                this.setDialogue(open);
                this.getChestsItem().clear();
                a.getInventory().drop(key);
                return this;
            }
        }

        return null;
    }
    public  int getChestId() {
        return chestId;
    }

    public void addObject(WorldObject obj){
        chestsItem.add(obj);
    }
}
