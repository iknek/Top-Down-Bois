package com.mygdx.game.desktop;

import java.awt.*;
import java.util.ArrayList;

public class Weapon {
    public ArrayList<Projectile> projectileList;


    public Weapon()
    {
        this.projectileList = new ArrayList<>();
    }

    public void fireWeapon(float angle, float x, float y){
        Projectile p = new Projectile(200, angle, x, y);
        projectileList.add(p);
    }
}
