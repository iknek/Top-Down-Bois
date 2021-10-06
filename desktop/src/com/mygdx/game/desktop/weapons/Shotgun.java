package com.mygdx.game.desktop.weapons;

import com.mygdx.game.desktop.Projectile;

public class Shotgun extends Firearm {

    String texture = "bullet.png";

    public Shotgun(float scale){
        super(1000, 1, 12, 90, 2000, 10, scale);
    }

    @Override
    public void createBullet(int angle, float x, float y) {
        new Projectile(projectileSpeed, angle, x,y, damage, texture, scale);
        new Projectile(projectileSpeed, angle + 4, x,y, damage, texture, scale);
        new Projectile(projectileSpeed, angle, x,y + 6, damage, texture, scale);
        new Projectile(projectileSpeed, angle - 4, x,y, damage, texture, scale);
        new Projectile(projectileSpeed, angle - 6, x,y, damage, texture, scale);
        ammoInMagazine -= 5;
    }
    
}

