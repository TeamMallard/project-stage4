package com.superduckinvaders.game.entity.item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.entity.Entity;

/**
 * The items class
 */
public class Item extends Entity {

    /**
     * The texture for this Item.
     */
    protected TextureRegion texture;

    /**
     * Creates a new Item.
     *
     * @param parent  the round this Item belongs to
     * @param x       the x coordinate of this Item
     * @param y       the y coordinate of this Item
     * @param texture the texture to use for this Item
     */
    public Item(Round parent, double x, double y, TextureRegion texture) {
        super(parent, x, y);

        this.texture = texture;
    }

    /**
     * @return the width of this Item
     */
    @Override
    public int getWidth() {
        return texture.getRegionWidth();
    }

    /**
     * @return the height of this Item
     */
    @Override
    public int getHeight() {
        return texture.getRegionHeight();
    }

    /**
     * Updates the state of this Item.
     *
     * @param delta how much time has passed since the last update
     */
    @Override
    public void update(float delta) {
        // Don't do anything...yet.
    }

    /**
     * Renders this Item onto the specified sprite batch.
     *
     * @param spriteBatch the sprite batch on which to render
     */
    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(texture, (int) x, (int) y);
    }

}
