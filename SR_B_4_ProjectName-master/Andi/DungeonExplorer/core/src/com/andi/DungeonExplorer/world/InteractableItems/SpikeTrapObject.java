package com.andi.DungeonExplorer.world.InteractableItems;

import com.andi.DungeonExplorer.world.WorldObject;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

/**
 * Created by Andi Li on 10/6/2017.
 */



public class SpikeTrapObject extends WorldObject {
    public enum STATE {
        UP,
        DOWN,
        GOINGUP,
        GOINGDOWN,
        ;
    }
    private Animation downAnimation;
    private Animation upAnimation;
    public STATE state = STATE.DOWN;


    private float animationTimer = 0f;
    private float animationTime = 0.5f;



    public SpikeTrapObject(int x, int y, Animation upAnimation, Animation downAnimation) {
        super("spike_trap",x, y, true, (TextureRegion) upAnimation.getKeyFrames()[0], 1f, 1f, new GridPoint2(0,0),false);
        this.downAnimation = downAnimation;
        this.upAnimation =upAnimation;

    }

    public void open() {
        state = STATE.GOINGUP;
    }

    public void close() {
        state = STATE.GOINGDOWN;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if (state == STATE.GOINGDOWN || state == STATE.GOINGUP) {
            animationTimer += delta;
            if (animationTimer >= animationTime) {
                if (state == STATE.GOINGDOWN) {
                    state = STATE.DOWN;
                } else if (state == STATE.GOINGUP) {
                    state = STATE.UP;
                }
                animationTimer = 0f;
            }
        }
    }

    public STATE getState() {
        return state;
    }

    public TextureRegion getSprite() {
        if (state == STATE.UP) {
            return (TextureRegion) downAnimation.getKeyFrames()[3];
        } else if (state == STATE.DOWN) {
            return (TextureRegion)  downAnimation.getKeyFrames()[0];
        } else if (state == STATE.GOINGUP) {
            return (TextureRegion)  upAnimation.getKeyFrame(animationTimer);
        } else if (state == STATE.GOINGDOWN) {
            return (TextureRegion)  downAnimation.getKeyFrame(animationTimer);
        }
        return null;
    }
}
