package com.superduckinvaders.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.superduckinvaders.game.entity.Player;

import java.util.HashMap;

/**
 * Contains methods needed to draw the minimap.
 */
public class Minimap {

    /**
     * Width of the minimap border.
     */
    private static final int BORDER_SIZE = 2;

    /**
     * The round this Minimap looks over.
     */
    private Round round;

    /**
     * The sprite batch to use for rendering this Minimap.
     */
    private SpriteBatch spriteBatch;

    /**
     * Each enumerable represents an object or obstacle in the game world.
     * Each has a specific defining colour.
     *
     * @author Matthew
     */
    private enum MinimapColors {
        GRASS,
        WATER,
        PATH,
        BUSH,
        BUILDING,
        TREE_BOTTOM,
        TREE_TOP,
        OBJECTIVE,
        PLAYER
    }

    /**
     * Maps a named enum to an integer representing a colour.
     */
    private static HashMap<MinimapColors, Integer> colorDictionary = new HashMap<MinimapColors, Integer>();

    static {
        colorDictionary.put(MinimapColors.GRASS, 0x7DC847FF);
        colorDictionary.put(MinimapColors.WATER, 0x6983E8FF);
        colorDictionary.put(MinimapColors.PATH, 0xE8B969FF);
        colorDictionary.put(MinimapColors.BUSH, 0x42AA52FF);
        colorDictionary.put(MinimapColors.BUILDING, 0xA2693EFF);
        colorDictionary.put(MinimapColors.TREE_BOTTOM, 0x4E3A21FF);
        colorDictionary.put(MinimapColors.TREE_TOP, 0x108239FF);
        colorDictionary.put(MinimapColors.OBJECTIVE, 0xFF0000FF);
        colorDictionary.put(MinimapColors.PLAYER, 0xFFFFFFFF);
    }

    /**
     * Initialises the minimap with the current round and the round SpriteBatch.
     *
     * @param round       the current round of the game.
     * @param spriteBatch the spritebatch used in the current round of the game.
     */
    public Minimap(Round round, SpriteBatch spriteBatch) {
        this.round = round;
        this.spriteBatch = spriteBatch;
    }

    /**
     * Draws a minimap in the top right using coloured cells.
     * @param prevWindowWidth the previous window width, used for positioning
     * @param prevWindowHeight the previous window height, used for positioning
     */
    public void drawMinimap(int prevWindowWidth, int prevWindowHeight) {
        Player player = round.getPlayer();
        MapLayers layers = round.getMap().getLayers();

        int tileWidth = round.getTileWidth();
        int tileHeight = round.getTileHeight();
        int mapWidth = round.getMapWidth();
        int mapHeight = round.getMapHeight();
        int playerX = (int) player.getX() / tileWidth;
        int playerY = (int) player.getY() / tileHeight;

        // Odd numbers so player is centred
        int minimapWidth = 51;
        int minimapHeight = 51;
        int minimapScale = 4;

        //calculate offset for positioning of minimap
        int resizeOffsetX = (Gdx.graphics.getWidth() - prevWindowWidth) == 0 ? minimapWidth * minimapScale : (minimapWidth * minimapScale + (Gdx.graphics.getWidth() - prevWindowWidth));
        int resizeOffsetY = (Gdx.graphics.getHeight() - prevWindowHeight) == 0 ? minimapHeight * minimapScale : (minimapHeight * minimapScale + (Gdx.graphics.getHeight() - prevWindowHeight));

        int minimapX = Gdx.graphics.getWidth() - resizeOffsetX - 88;
        int minimapY = Gdx.graphics.getHeight() - resizeOffsetY - 8;
        int minimapXOffset = playerX - minimapWidth / 2;
        int minimapYOffset = playerY - minimapHeight / 2;
        // +2 pixels for the border
        Pixmap minimapData = new Pixmap(minimapWidth * minimapScale + 2 * BORDER_SIZE, minimapHeight * minimapScale + 2 * BORDER_SIZE, Pixmap.Format.RGBA8888);
        TiledMapTileLayer waterLayer = (TiledMapTileLayer) layers.get("Water");
        TiledMapTileLayer baseLayer = (TiledMapTileLayer) layers.get("Base");
        TiledMapTileLayer collisionLayer = (TiledMapTileLayer) layers.get("Collision");
        TiledMapTileLayer obstaclesLayer = round.getObstaclesLayer();
        TiledMapTileLayer overhangLayer = (TiledMapTileLayer) layers.get("Overhang");


        if (playerX < minimapWidth / 2) {
            minimapXOffset = 0;
        }
        if (playerY < minimapHeight / 2) {
            minimapYOffset = 0;
        }
        if (playerX >= mapWidth / tileWidth - minimapWidth / 2) {
            minimapXOffset = mapWidth / tileWidth - minimapWidth;
        }
        if (playerY >= mapHeight / tileHeight - minimapHeight / 2) {
            minimapYOffset = mapHeight / tileHeight - minimapHeight;
        }

        // Defines the colour for each cell
        for (int i = 0; i < minimapWidth; i++) {
            for (int j = 0; j < minimapHeight; j++) {
                // Default green grass colour
                int cellColor = colorDictionary.get(MinimapColors.GRASS);
                int absoluteX = i + minimapXOffset;
                int absoluteY = j + minimapYOffset;

                // Cell is on the base layer
                if (baseLayer.getCell(absoluteX, absoluteY) != null) {
                    int cellID = baseLayer.getCell(absoluteX, absoluteY).getTile().getId();
                    //if the cell is not one of the grass cells then it must be a path cell
                    if (cellID != 1 && cellID != 2 && cellID != 11 && cellID != 12 && cellID != 13 && cellID != 21 && cellID != 22 && cellID != 26 && cellID != 31 && cellID != 33 && cellID != 51 && cellID != 52 && cellID != 53 && cellID != 228) {
                        cellColor = colorDictionary.get(MinimapColors.PATH);
                    }
                }
                // Cell is on the water layer
                if (waterLayer.getCell(absoluteX, absoluteY) != null) {
                    cellColor = colorDictionary.get(MinimapColors.WATER);
                }
                // Cell is in the collision layer
                if (collisionLayer.getCell(absoluteX, absoluteY) != null) {
                    int cellID = collisionLayer.getCell(absoluteX, absoluteY).getTile().getId();
                    if (cellID == 8) {
                        cellColor = colorDictionary.get(MinimapColors.BUSH);
                    } else {
                        cellColor = 0xA2693EFF;
                    }
                }
                // Cell is on the obstacles layer (bushes)
                if (obstaclesLayer.getCell(absoluteX, absoluteY) != null) {
                    int cellID = obstaclesLayer.getCell(absoluteX, absoluteY).getTile().getId();
                    if (cellID == 8) {
                        cellColor = colorDictionary.get(MinimapColors.BUSH);
                    } else if (cellID == 49 || cellID == 50) {
                        cellColor = colorDictionary.get(MinimapColors.TREE_BOTTOM);
                    } else {
                        cellColor = colorDictionary.get(MinimapColors.BUILDING);
                    }
                }
                // Cell contains objective
                if (Integer.parseInt(round.getMap().getProperties().get("ObjectiveX").toString()) == i + minimapXOffset + 1
                        && Integer.parseInt(round.getMap().getProperties().get("ObjectiveY").toString()) == j + minimapYOffset) {
                    cellColor = colorDictionary.get(MinimapColors.OBJECTIVE);
                }

                // Cell contains an overhang
                if (overhangLayer.getCell(absoluteX, absoluteY) != null) {
                    int cellID = overhangLayer.getCell(absoluteX, absoluteY).getTile().getId();
                    if (cellID == 9 || cellID == 10 || cellID == 29 || cellID == 30) {
                        cellColor = colorDictionary.get(MinimapColors.TREE_TOP);
                    }
                }

                // Cell contains player
                if (playerX - minimapXOffset == i && playerY - minimapYOffset == j) {
                    cellColor = colorDictionary.get(MinimapColors.PLAYER);
                }

                for (int k = 0; k < minimapScale; k++) {
                    for (int l = 0; l < minimapScale; l++) {
                        minimapData.drawPixel(i * minimapScale + k + BORDER_SIZE, -(j * minimapScale + l) + minimapHeight * minimapScale + BORDER_SIZE - 1, cellColor);
                    }
                }
            }
        }

        // Draw minimap border
        minimapData.setColor(Color.BLACK);
        minimapData.fillRectangle(0, 0, minimapData.getWidth(), BORDER_SIZE);
        ;
        minimapData.fillRectangle(0, 0, BORDER_SIZE, minimapData.getHeight());
        minimapData.fillRectangle(0, minimapData.getHeight() - BORDER_SIZE, minimapData.getWidth(), BORDER_SIZE);
        minimapData.fillRectangle(minimapData.getWidth() - BORDER_SIZE, 0, BORDER_SIZE, minimapData.getHeight());

        Texture minimapTexture = new Texture(minimapData);
        spriteBatch.draw(minimapTexture, minimapX, minimapY);

        // Need to flush because we're about to dispose the texture
        spriteBatch.flush();
        minimapData.dispose();
        minimapTexture.dispose();
    }
}
