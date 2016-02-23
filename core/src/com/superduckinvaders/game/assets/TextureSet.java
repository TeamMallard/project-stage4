package com.superduckinvaders.game.assets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Represents a standard set of textures for character facing and animation.
 */
public final class TextureSet {

    /**
     * Constants for which way the character is facing.
     */
    public static final int FACING_FRONT = 0, FACING_BACK = 1, FACING_LEFT = 2, FACING_RIGHT = 3;

    /**
     * Textures to use when the character isn't moving.
     */
    private TextureRegion[] idleTextures = new TextureRegion[4];

    /**
     * Animations to use when the character is moving.
     */
    private Animation[] movementAnimations = new Animation[4];

    /**
     * Initialises this TextureSet with a single texture used for everything.
     *
     * @param all the texture to use for everything
     */
    public TextureSet(TextureRegion all) {
        this(all, all, all, all);
    }

    /**
     * Initialises this TextureSet with textures for facing. No movement animations will be used.
     *
     * @param front the foward facing texture
     * @param back  the backward facing texture
     * @param left  the left facing texture
     * @param right the right facing texture
     */
    public TextureSet(TextureRegion front, TextureRegion back, TextureRegion left, TextureRegion right) {
        this(front, back, left, right, new Animation(0, front),
                new Animation(0, back), new Animation(0, left), new Animation(0, right));
    }

    /**
     * Initilaises this TextureSet with textures for facing and movement animations.
     *
     * @param front       the foward facing texture
     * @param back        the backward facing texture
     * @param left        the left facing texture
     * @param right       the right facing texture
     * @param movingFront the moving forward animation
     * @param movingBack  the moving backward animation
     * @param movingLeft  the moving left animation
     * @param movingRight the moving right animation
     */
    public TextureSet(TextureRegion front, TextureRegion back, TextureRegion left, TextureRegion right,
                      Animation movingFront, Animation movingBack, Animation movingLeft, Animation movingRight) {
        idleTextures[FACING_FRONT] = front;
        idleTextures[FACING_BACK] = back;
        idleTextures[FACING_LEFT] = left;
        idleTextures[FACING_RIGHT] = right;

        movementAnimations[FACING_FRONT] = movingFront;
        movementAnimations[FACING_BACK] = movingBack;
        movementAnimations[FACING_LEFT] = movingLeft;
        movementAnimations[FACING_RIGHT] = movingRight;
    }

    /**
     * Gets the representative width of this TextureSet (the front idle texture).
     *
     * @return the width
     */
    public int getWidth() {
        return idleTextures[0].getRegionWidth();
    }

    /**
     * Gets the representative height of this TextureSet (the front idle texture).
     *
     * @return the height
     */
    public int getHeight() {
        return idleTextures[0].getRegionHeight();
    }

    /**
     * Gets the texture for the specified facing at the specified state time. A state time of 0 means not moving; idle.
     *
     * @param facing    the facing
     * @param stateTime the state time
     * @return the appropriate texture
     */
    public TextureRegion getTexture(int facing, float stateTime) {
        if (stateTime > 0) {
            return movementAnimations[facing].getKeyFrame(stateTime, true);
        } else {
            return idleTextures[facing];
        }
    }
}
