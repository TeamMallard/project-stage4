/**
 * URL for executable: https://drive.google.com/open?id=0B6Dzw1Xbx2OzdkhPY1pQd2ZYODQ
 */

package com.superduckinvaders.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.superduckinvaders.game.DuckGame;

/**
 * Desktop launcher for Super Duck Invaders.
 * Executable URL: https://drive.google.com/open?id=0BwfrsNAvbwjWUmh4aFU1anZoSFk
 */
public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1280;
        config.height = 720;
        config.resizable = true;
        config.title = "SUPER DUCK INVADERS! - Team Mallard";
        new LwjglApplication(new DuckGame(), config);
    }
}