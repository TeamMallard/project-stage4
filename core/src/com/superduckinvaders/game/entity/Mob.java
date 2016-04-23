package com.superduckinvaders.game.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.ai.AI;
import com.superduckinvaders.game.ai.DummyAI;
import com.superduckinvaders.game.assets.Assets;
import com.superduckinvaders.game.assets.TextureSet;

/**
 * Represents an enemy in the game
 */
public class Mob extends Character {

    /**
     * The chance that a Mob will fire a projectile each frame.
     */
    private static final double RANGED_CHANGE = 0.01;

    /**
     * The texture set to use for this Mob.
     */
    private TextureSet textureSet;

    /**
     * AI class for the mob
     */
    private AI ai;

    /**
     * Speed of the mob in pixels per second
     */
    private int speed;

    /**
     * The speed at which this mob walks, in pixels per second.
     */
    private int walkSpeed;

    /**
     * Boolean indicating whether or not the mob is a ranged mob.
     */
    private boolean ranged;

    /**
     * Whether or not this Mob is a boss.
     */
    private boolean boss;

    /**
     * Doubles indicating the position x,y of the ranged mob target.
     */
    private double targetX, targetY;

    /**
     * Constructor for a Mob character.
     *
     * @param parent     The round the mob is created in.
     * @param x          Mobs position in x
     * @param y          Mobs position in y
     * @param health     The mobs health
     * @param textureSet The textureSet of the mob.
     * @param speed      The speed of the mob.
     * @param ai         Which AI to spawn the mob with.
     * @param ranged     Is the mob ranged or not.
     * @param boss       Is the mob a boss or not.
     */
    public Mob(Round parent, double x, double y, int health, TextureSet textureSet, int speed, AI ai, boolean ranged, boolean boss) {
        super(parent, x, y, health);
        this.textureSet = textureSet;
        this.walkSpeed = speed;
        this.ai = ai;
        this.boss = boss;

        // There are no ranged bosses.
        this.ranged = !this.boss && ranged;
    }

    public Mob(Round parent, int x, int y, int health, TextureSet textureSet, int speed) {
        this(parent, x, y, health, textureSet, speed, new DummyAI(parent), false, false);
    }

    /**
     * change where the given mob moves to according to its speed and a new direction vector
     *
     * @param dirX x component of the direction vector
     * @param dirY y component of the direction vector
     */
    public void setVelocity(int dirX, int dirY) {
        if (dirX == 0 && dirY == 0) {
            velocityX = 0;
            velocityY = 0;
            return;
        }
        double magnitude = Math.sqrt(dirX * dirX + dirY * dirY);
        velocityX = (dirX * speed) / magnitude;
        velocityY = (dirY * speed) / magnitude;

    }

    /**
     * gets texture width
     */
    @Override
    public int getWidth() {
        return textureSet.getTexture(TextureSet.FACING_FRONT, 0).getRegionWidth();
    }

    /**
     * gets texture height
     */
    @Override
    public int getHeight() {
        return textureSet.getTexture(TextureSet.FACING_FRONT, 0).getRegionHeight();
    }

    /**
     * Updates the ranged target position of this Mob.
     *
     * @param x the new x coordinate of the target
     * @param y the new y coordinate of the target
     */
    public void updateTargetPosition(double x, double y) {
        targetX = x;
        targetY = y;
    }

    /**
     * Updates mob
     */
    @Override
    public void update(float delta) {
        ai.update(this, delta);
        float random = MathUtils.random();

        /**
         * CHANGE D4: Added functionality to fire projectiles in all directions when a mob exits demented mode.
         */
        if (lastDementedTimer < 0 && dementedTimer > 0) {
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    if (!(x == 0 && y == 0)) {
                        fireAt(this.x + getWidth() / 2 + x, this.y + getHeight() / 2 + y, 300, 1);
                    }
                }
            }
        }

        // If mob is ranged, fire projectile with given probability.
        // Mobs can't fire in water.
        if (random < RANGED_CHANGE && ranged && !this.isSwimming) {
            fireAt(targetX, targetY, 300, 1);
        }

        // Slow mob down in water.
        if (this.getSwimming()) {
            speed = walkSpeed / 2;
        } else {
            speed = walkSpeed;
        }

        // Chance of spawning a random powerup.
        if (isDead()) {
            Player.Powerup powerup = null;

            if (random < 0.05) {
                powerup = Player.Powerup.SCORE_MULTIPLIER;
            } else if (random >= 0.05 && random < 0.1) {
                powerup = Player.Powerup.INVULNERABLE;
            } else if (random >= 0.1 && random < 0.15) {
                powerup = Player.Powerup.SUPER_SPEED;
            } else if (random >= 0.15 && random < 0.2) {
                powerup = Player.Powerup.RATE_OF_FIRE;
            } else if (random >= 0.2 && random < 0.25) {
                powerup = Player.Powerup.REGENERATION;
            }

            if (powerup != null) {
                parent.createPowerup(x, y, powerup);
            }
        }

        super.update(delta);
    }

    /**
     * Set the direction of facing
     *
     * @param newFacing direction
     */
    public void setFacing(int newFacing) {
        facing = newFacing;
    }

    /**
     * Renders the mob
     */
    @Override
    public void render(SpriteBatch spriteBatch) {

        if (this.boss) {
            textureSet = Assets.bossNormal;
        }

        if (this.getSwimming()) {
            if (this.boss) {
                textureSet = Assets.bossSwimming;
            } else {
                textureSet = isDemented() ? Assets.badGuySwimmingInv : Assets.badGuySwimming;
            }


        } else if (this.isRanged()) {
            textureSet = isDemented() ? Assets.badGuyGunInv : Assets.badGuyGun;
        } else if (!this.boss) {
            textureSet = isDemented() ? Assets.badGuyNormalInv : Assets.badGuyNormal;
        }


        spriteBatch.draw(textureSet.getTexture(facing, stateTime), (int) x, (int) y);
    }

    /**
     * Determine if the mob is ranged or not.
     *
     * @return boolean indicating if mob is ranged or not.
     */
    public boolean isRanged() {
        return ranged;
    }

    /**
     * Determine if the mob is a boss or not.
     *
     * @return boolean indicating if mob is a boss or not.
     */
    public boolean isBoss() {
        return boss;
    }
}
