package com.mygdx.game.desktop;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.*;

public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "hello-world";
        //cfg.useGL20 = false; //should this really be commented out?....
        cfg.width = 1920;
        cfg.height = 1080;

       // new LwjglApplication(new DesktopLauncher(), cfg);
        new LwjglApplication(new TiledTestTwo(), cfg);

    }
}
