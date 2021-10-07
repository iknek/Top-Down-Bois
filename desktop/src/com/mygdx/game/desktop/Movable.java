package com.mygdx.game.desktop;

import com.badlogic.gdx.math.Rectangle;

/**
 * Interface for Objects that move
 * @Author Anders
 */
public interface Movable {
    void update();
    void collide(Rectangle rectangle);
    //kanske ska lösa det här på ett annat sätt, typ med en till observerpattern endast för zombies som behöver veta player location
    Rectangle getBoundingRectangle();
    float getX();
    float getY();
}
