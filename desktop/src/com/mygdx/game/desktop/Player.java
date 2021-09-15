package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import static com.badlogic.gdx.Gdx.files;

public class Player extends Sprite {

    TextureAtlas textureAtlas;
    public Player(TextureAtlas atlas) {
        super(atlas.getRegions().get(0));
        textureAtlas = atlas;
        //Gdx.input.setInputProcessor(inputProcessor);
    }

    public void setTexture(String textureName) {
        setRegion(textureAtlas.findRegion(textureName));
    }

   /* private final InputProcessor inputProcessor = new InputAdapter() {
        @Override
        public boolean keyUp(int keycode) {
            if (keycode == Input.Keys.LEFT)
                //camera.translate(-32,0);
                Player.setPosition(Player.getX() - 32, Player.getY());
            //texture = new Texture(Gdx.files.internal("Adam_left.png")); <-- Needs doing, obviously not like this

            if (keycode == Input.Keys.RIGHT)
                //camera.translate(32,0);
                sprite.setPosition(sprite.getX() + 32, sprite.getY());
            //texture = new Texture(Gdx.files.internal("Adam_right.png")); <-- Needs doing, obviously not like this

            if (keycode == Input.Keys.UP)
                //camera.translate(0,32);
                sprite.setPosition(sprite.getX(), sprite.getY() + 32);
            //texture = new Texture(Gdx.files.internal("Adam_forward.png")); <-- Needs doing, obviously not like this

            if (keycode == Input.Keys.DOWN)
                //camera.translate(0,-32);
                sprite.setPosition(sprite.getX(), sprite.getY() - 32);
            //texture = new Texture(Gdx.files.internal("Adam_back.png")); <-- Needs doing, obviously not like this

            if (keycode == Input.Keys.NUM_1)
                tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());

            if (keycode == Input.Keys.NUM_2)
                tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());
            return false;
        }
    };*/

}
