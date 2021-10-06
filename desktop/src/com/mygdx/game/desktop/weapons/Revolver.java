package com.mygdx.game.desktop.weapons;

import com.mygdx.game.desktop.Projectile;
import com.mygdx.game.desktop.weapons.Firearm;

import java.util.Timer;
import java.util.TimerTask;

public class Revolver implements Firearm {

    int projectileSpeed = 1000;
    int damage = 1;
    String texture = "bullet.png";
    int AmmoInMagazine = 6;
    //rounds per minute
    float rateOfFire = 200;
    private Timer timer;
    private boolean readyToFire = true;

    @Override
    public void fire(int angle, float x, float y) {
        if (readyToFire && AmmoInMagazine > 0) {
            readyToFire = false;
            timer = new Timer();
            new Projectile(projectileSpeed, angle, x,y, damage, texture);
            AmmoInMagazine = AmmoInMagazine - 1;
            timer.schedule(new RemindTask(), (int)(60/rateOfFire*1000));
        }
    }

    class RemindTask extends TimerTask {
        public void run(){
            readyToFire = true;
            timer.cancel(); //Terminate the timer thread
        }
    }

    @Override
    public void reloadFirearm() {
        AmmoInMagazine = 6;
    }
}
