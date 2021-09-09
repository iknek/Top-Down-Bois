package com.mygdx.game.desktop;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.*;

public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "hello-world";
        //cfg.useGL20 = false; //should this really be not commented?....
        cfg.width = 480;
        cfg.height = 320;

        new LwjglApplication(new DesktopLauncher(), cfg);
    }
}