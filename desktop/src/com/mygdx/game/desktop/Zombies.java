package com.mygdx.game.desktop;

/**
 * Interafce for all Zombies
 */
public interface Zombies {
    boolean isHitPlayer();
    void setHitPlayer(boolean hitPlayer);
    int getDamage();
}
