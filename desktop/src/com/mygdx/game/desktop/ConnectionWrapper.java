package com.mygdx.game.desktop;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.math.Vector2;

public class ConnectionWrapper implements Connection<Movable> {
    Movable playerNode;
    Movable zombieNode;

    float cost;

    public ConnectionWrapper(Movable zombieNode, Movable playerNode){
        this.zombieNode = zombieNode;
        this.playerNode = playerNode;
        cost = Vector2.dst(zombieNode.getX(), zombieNode.getY(), playerNode.getX(), playerNode.getY());
    }

    @Override
    public float getCost() {
        return cost;
    }

    @Override
    public Movable getFromNode() {
        return zombieNode;
    }

    @Override
    public Movable getToNode() {
        return playerNode;
    }
}
