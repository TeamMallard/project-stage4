package com.superduckinvaders.game.entity.item;

import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.entity.Player;

/**
 * Created by olivermcclellan on 16/01/2016.
 */
public class Upgrade extends Item {

    /**
     * The specific upgrade this Upgrade item grants.
     */
    private Player.Upgrade upgrade;


    public Upgrade(Round parent, double x, double y, Player.Upgrade upgrade) {
        super(parent, x, y, Player.Upgrade.getTextureForUpgrade(upgrade));

        this.upgrade = upgrade;
    }

    @Override
    public void update(float delta) {
        Player player = parent.getPlayer();

        if (this.intersects(player.getX(), player.getY(), player.getWidth(), player.getHeight())) {
            player.setUpgrade(upgrade);
            removed = true;
        }
    }
}
