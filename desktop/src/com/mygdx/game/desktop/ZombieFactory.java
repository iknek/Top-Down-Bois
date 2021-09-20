package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ZombieFactory {

    public ArrayList<Zombie> createZombie(int amount, float W, float H, int scale){
        TextureAtlas atlasEric = new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas"));
        ArrayList<Zombie> zombies = new ArrayList<>();
        for (int i = 0; i <= amount+1; i++) {
            Random random = new Random();
            int randomInt = random.nextInt(8);
            zombies.add(new Zombie(atlasEric, W/randomInt, H/randomInt, scale));
        }
        return zombies;
    }
}
