package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.badlogic.gdx.Gdx.files;



public class Player extends Sprite {
    TextureAtlas textureAtlas;

    public Player(TextureAtlas atlas, float posX, float posY, float scale) {

        super(atlas.getRegions().get(0));
        textureAtlas = atlas;
        this.setPosition(posX, posY);
        this.setScale(scale);
        setRegion(textureAtlas.findRegion("Adam_back"));


    }

    public float getX() {
        return super.getX();
    }

    public float getY(){
        return super.getY();
    }

    public void setPosition(float posX, float posY){
        this.setX(posX);
        this.setY(posY);
    }


    private final InputProcessor inputProcessor = new InputAdapter() {

        @Override

        public boolean keyDown(int keycode) {
            if (keycode == Input.Keys.LEFT)
                translateX(-16);

            if (keycode == Input.Keys.RIGHT)
                translateX(16);

            if (keycode == Input.Keys.UP)
                translateY(16);

            if (keycode == Input.Keys.DOWN)
                translateY(-16);

            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(float amountX, float amountY) {
            return false;
        }
    };

    public InputProcessor getInputProcessor(){
        return inputProcessor;
    }
}
