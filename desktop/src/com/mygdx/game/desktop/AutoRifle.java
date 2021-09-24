package com.mygdx.game.desktop;

import java.util.Timer;
import java.util.TimerTask;

public class AutoRifle implements Firearm{

    int projectileSpeed = 1800;
    int damage = 3;
    String texture = "bullet.png";
    int AmmoInMagazine = 25;
    //rounds per minute
    float rateOfFire = 600;
    private Timer timer;
    private boolean readyToFire = true;

    @Override
    public void fire(int angle, float x, float y) {
        if (AmmoInMagazine > 0 && readyToFire) {
            timer = new Timer();
            new Projectile(projectileSpeed, angle, x,y, damage, texture);
            AmmoInMagazine = AmmoInMagazine - 1;
            timer.schedule(new RemindTask(), (int)(60/rateOfFire*1000));
            readyToFire = false;
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
        AmmoInMagazine = 25;
    }
}
