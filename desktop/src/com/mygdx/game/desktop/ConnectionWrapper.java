package com.mygdx.game.desktop;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.math.Vector2;

public class ConnectionWrapper implements Connection<Movable> {
    Movable player;
    Movable zombie;

    float cost;

    public ConnectionWrapper(Movable zombie, Movable player){
        this.zombie = zombie;
        this.player = player;
        cost = Vector2.dst(zombie.getX(), zombie.getY(), player.getX(), player.getY());
    }

    @Override
    public float getCost() {
        return 0;
    }

    @Override
    public Movable getFromNode() {
        return null;
    }

    @Override
    public Movable getToNode() {
        return null;
    }
}
