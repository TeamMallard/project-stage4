package com.superduckinvaders.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.superduckinvaders.game.assets.Assets;
import com.superduckinvaders.game.entity.Player;

/**
 * Manages cheats.
 */
public class CheatProcessor implements InputProcessor {

    private static final Sound CHEAT_SOUND = Assets.buttonPress;

    /**
     * How long each cheat is active for.
     */
    private static final float CHEAT_ACTIVATION_TIME = 10f;

    /**
     * How long each cheat takes to cool down before it can be used again.
     */
    private static final float CHEAT_COOLDOWN_TIME = 60f;

    /**
     * The cheat code used to activate all powerups for a time (the famous Konami code).
     */
    private static final int[] CHEAT_CODE_POWERUP = new int[]{Input.Keys.UP, Input.Keys.UP, Input.Keys.DOWN, Input.Keys.DOWN, Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.B, Input.Keys.A};

    /**
     * The cheat code used to activate noclip for a time (the word "mallard").
     */
    private static final int[] CHEAT_CODE_NOCLIP = new int[]{Input.Keys.M, Input.Keys.A, Input.Keys.L, Input.Keys.L, Input.Keys.A, Input.Keys.R, Input.Keys.D};

    /**
     * The round this CheatProcessor belongs to.
     */
    private Round parent;

    /**
     * The current index into each of the cheat codes.
     */
    private int powerupCodeIndex = 0, noclipCodeIndex = 0;

    /**
     * The timers for each cheat code.
     */
    private float powerupTimer = 0, noclipTimer = 0;

    /**
     * Creates a new CheatProcessor.
     * @param parent the round this CheatProcessor belongs to
     */
    public CheatProcessor(Round parent) {
        this.parent = parent;
    }

    /**
     * Returns whether the noclip cheat code is currently active.
     *
     * @return whether the noclip cheat code is currently active
     */
    public boolean isNoclipActive() {
        return noclipTimer > CHEAT_COOLDOWN_TIME;
    }

    /**
     * Updates the cheat code timers.
     *
     * @param delta the time elapsed since the last update
     */
    public void update(float delta) {
        if (powerupTimer > 0) {
            powerupTimer -= delta;
        }

        if (noclipTimer > 0) {
            noclipTimer -= delta;
        }
    }

    /**
     * Called when a key is pressed.
     *
     * @param keycode the key code
     * @return true, as the event was handled
     */
    @Override
    public boolean keyDown(int keycode) {
        // Check for powerup cheat.
        if (keycode == CHEAT_CODE_POWERUP[powerupCodeIndex]) {
            powerupCodeIndex++;

            if (powerupCodeIndex == CHEAT_CODE_POWERUP.length) {
                powerupCodeIndex = 0;

                // Activate the powerup cheat.
                if (powerupTimer <= 0) {
                    powerupTimer = CHEAT_ACTIVATION_TIME + CHEAT_COOLDOWN_TIME;

                    // Simply add all powerups to a player for the activation time.
                    for(Player.Powerup powerup : Player.Powerup.values()) {
                        parent.getPlayer().setPowerup(powerup, CHEAT_ACTIVATION_TIME);
                    }

                    // Play sound for cheat activation.
                    CHEAT_SOUND.play();
                }
            }
        }

        // Check for noclip cheat.
        if (keycode == CHEAT_CODE_NOCLIP[noclipCodeIndex]) {
            noclipCodeIndex++;

            if (noclipCodeIndex == CHEAT_CODE_NOCLIP.length) {
                noclipCodeIndex = 0;

                // Activate the noclip cheat.
                if (noclipTimer <= 0) {
                    noclipTimer = CHEAT_ACTIVATION_TIME + CHEAT_COOLDOWN_TIME;

                    // Noclip functionality is implemented in the Player class.
                    // Play sound for cheat activation.
                    CHEAT_SOUND.play();
                }
            }
        }

        return true;
    }

    /**
     * Not implemented.
     */
    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    /**
     * Not implemented.
     */
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Not implemented.
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /**
     * Not implemented.
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /**
     * Not implemented.
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    /**
     * Not implemented.
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    /**
     * Not implemented.
     */
    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
