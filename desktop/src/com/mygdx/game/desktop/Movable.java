package com.mygdx.game.desktop;

import com.badlogic.gdx.math.Rectangle;

public interface Movable {
    void update();
    void collide(Rectangle rectangle);
    //kanske ska lösa det här på ett annat sätt, typ med en till observerpattern endast för zombies som behöver veta player location
    void playerLocation(int x, int y);
    Rectangle getBoundingRectangle();
    float getX();
    float getY();
}
