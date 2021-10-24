package com.mygdx.game.desktop.coins;

/**
 * Interface for {@link Coin} class so that all instances of coins can be updated
 * This interface is used by {@link Coin}
 * @author anders
 */
public interface Coins {
    void updateMagnet(int distance,int speed);
}
