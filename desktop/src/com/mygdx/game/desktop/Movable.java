package com.mygdx.game.desktop;

import com.badlogic.gdx.math.Rectangle;

/**
 * Interface for Objects that move
 * @Author Anders
 */
public interface Movable {
    void update();
    void collide(Rectangle rectangle);
    Rectangle getBoundingRectangle();
    float getX();
    float getY();
}
