package com.superduckinvaders.game.entity.item;

import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.entity.Player;

import java.util.HashMap;

/**
 * Represents a powerup on the floor.
 */
public class Powerup extends Item {

    private static final int[] POWERUP_MAX_TIMES = new int[Player.Powerup.values.length];

    static {
        POWERUP_MAX_TIMES[Player.Powerup.INVULNERABLE.ordinal()] = 10;
        POWERUP_MAX_TIMES[Player.Powerup.RATE_OF_FIRE.ordinal()] = 10;
        POWERUP_MAX_TIMES[Player.Powerup.SCORE_MULTIPLIER.ordinal()] = 10;
        POWERUP_MAX_TIMES[Player.Powerup.SUPER_SPEED.ordinal()] = 10;
        POWERUP_MAX_TIMES[Player.Powerup.REGENERATION.ordinal()] = 3;
    }

    /**
     * The powerup that this Powerup gives to the player.
     */
    private Player.Powerup powerup;

    /**
     * How long the powerup will last for.
     */
    private double time;

    public Powerup(Round parent, double x, double y, Player.Powerup powerup) {
        super(parent, x, y, Player.Powerup.getTextureForPowerup(powerup));

        this.powerup = powerup;
        this.time = POWERUP_MAX_TIMES[powerup.ordinal()];
    }

    /**
     * Returns the max duration for a specific powerup
     *
     * @param powerup The powerup
     * @return The max duration for a specific powerup
     */
    public static int getMaxPowerupTime(Player.Powerup powerup) {
        return POWERUP_MAX_TIMES[powerup.ordinal()];
    }

    @Override
    public void update(float delta) {
        Player player = parent.getPlayer();

        if (this.intersects(player.getX(), player.getY(), player.getWidth(), player.getHeight())) {
            player.setPowerup(powerup, time);
            removed = true;
        }
    }
}
