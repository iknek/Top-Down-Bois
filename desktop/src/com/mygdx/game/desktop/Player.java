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
    boolean leftMove;
    boolean rightMove;
    boolean UpMove;
    boolean DownMove;
    Weapon weapon;

    int speed = 80;

    float x;
    float y;

    public Player(TextureAtlas atlas, float posX, float posY, float scale) {

        super(atlas.getRegions().get(0));
        textureAtlas = atlas;
        this.setPosition(posX, posY);
        this.setScale(scale);
        this.x = posX;
        this.y = posY;
        this.weapon = new Weapon();
        setRegion(textureAtlas.findRegion("Adam_back"));


    }

    public Weapon getWeapon() {return this.weapon;}

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

    public void updateMotion()
    {
        if (leftMove)
        {
            x = getX();
            x -= speed * Gdx.graphics.getDeltaTime();
            setX(x);
        }
        if (rightMove)
        {
            x = getX();
            x += speed * Gdx.graphics.getDeltaTime();
            setX(x);
        }
        if (UpMove)
        {
            y = getY();
            y += speed * Gdx.graphics.getDeltaTime();
            setY(y);
        }
        if (DownMove)
        {
            y = getY();
            y -= speed * Gdx.graphics.getDeltaTime();
            setY(y);
        }
        for (Projectile p: weapon.projectileList) {
            p.updateMotion();
        }

    }

    public void setLeftMove(boolean t)
    {
        if(rightMove && t) rightMove = false;
        leftMove = t;

    }
    public void setRightMove(boolean t)
    {
        if(leftMove && t) leftMove = false;
        rightMove = t;
    }

    public void setUpMove(boolean t)
    {
        if(DownMove && t) DownMove = false;
        UpMove = t;
    }

    public void setDownMove(boolean t)
    {
        if(UpMove && t) UpMove = false;
        DownMove = t;
    }


    private final InputProcessor inputProcessor = new InputAdapter() {
        @Override
        public boolean keyDown(int keycode) {
            switch (keycode)
            {
                case Input.Keys.LEFT:
                    setLeftMove(true);
                    break;
                case Input.Keys.RIGHT:
                    setRightMove(true);
                    break;
                case Input.Keys.UP:
                    setUpMove(true);
                    break;
                case Input.Keys.DOWN:
                    setDownMove(true);
                    break;
                case Input.Keys.G:
                    //Shoot();
                    break;
            }
            return true;
        }

        @Override
        public boolean keyUp(int keycode) {
            switch (keycode)
            {
                case Input.Keys.LEFT:
                    setLeftMove(false);
                    break;
                case Input.Keys.RIGHT:
                    setRightMove(false);
                    break;
                case Input.Keys.UP:
                    setUpMove(false);
                    break;
                case Input.Keys.DOWN:
                    setDownMove(false);
                    break;
            }
            return true;
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

    private void Shoot(float angle) {
        //Mabye trigger a shoot method in a gun class instead to allow for different guns, projectile speed, better oop and such
        weapon.fireWeapon(angle, x, y);
    }

    public InputProcessor getInputProcessor(){
        return inputProcessor;
    }
}
