package com.mygdx.game.desktop;

import com.badlogic.gdx.ai.pfa.Heuristic;

public class ZombieHeuristics implements Heuristic<MovableSubject> {

    @Override
    public float estimate(MovableSubject zombie, MovableSubject player) {
        return 0;
    }
}
