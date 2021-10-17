package com.mygdx.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
    public static void main(String[] args) {
        int scale = 2;
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Wild West Coffin Escape";
        cfg.width = 640*scale;
        cfg.height = 640*scale;
        cfg.addIcon("Icon2.png", Files.FileType.Internal);
        new LwjglApplication(new TiledTestTwo(scale), cfg);
    }
}