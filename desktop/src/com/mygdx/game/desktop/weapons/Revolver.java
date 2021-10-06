package com.mygdx.game.desktop.weapons;

import com.mygdx.game.desktop.Projectile;
import com.mygdx.game.desktop.weapons.Firearm;

import java.util.Timer;
import java.util.TimerTask;

public class Revolver extends Firearm {

    String texture = "bullet.png";

    public Revolver(float scale){
        super(1000, 1, 6, 200, 2000, 6, scale);
    }

    @Override
    public void createBullet(int angle, float x, float y) {
        new Projectile(projectileSpeed, angle, x,y, damage, texture, scale);
        ammoInMagazine -= 1;
    }
}
