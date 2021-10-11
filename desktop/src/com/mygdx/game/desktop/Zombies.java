package com.mygdx.game.desktop;

public interface Zombies {
    void playerLocation(int x, int y);
    boolean isHitPlayer();
    void setHitPlayer(boolean hitPlayer);
    int getDamage();
}
