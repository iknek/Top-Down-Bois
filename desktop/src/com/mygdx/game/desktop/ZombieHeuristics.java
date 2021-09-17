package com.mygdx.game.desktop;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.math.Vector2;

public class ZombieHeuristics implements Heuristic<Movable> {

    @Override
    public float estimate(Movable zombieNode, Movable playerNode) {
        return Vector2.dst(zombieNode.getX(), zombieNode.getY(), playerNode.getX(), playerNode.getY());
    }
}
