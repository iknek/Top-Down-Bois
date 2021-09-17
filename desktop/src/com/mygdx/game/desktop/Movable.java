package com.mygdx.game.desktop;

import com.badlogic.gdx.math.Rectangle;

public interface Movable {
    void update();
    void collide(Rectangle rectangle);
    Rectangle getBoundingRectangle();
    float getX();
    float getY();
}
