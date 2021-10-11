package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
    public static void main(String[] args) {
        int scale = 1;
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Wild West ";
        cfg.width = 640*scale;
        cfg.height = 640*scale;
        new LwjglApplication(new TiledTestTwo(scale), cfg);
    }
}