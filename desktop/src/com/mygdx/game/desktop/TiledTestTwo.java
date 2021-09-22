package com.mygdx.game.desktop;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.ArrayList;

//This class has been taken from https://gamefromscratch.com/libgdx-tutorial-11-tiled-maps-part-2-adding-a-sprite-and-dealing-with-layers/
//albeit with certain reworks to fit for this project.

public class TiledTestTwo extends ApplicationAdapter implements InputProcessor {
    private OrthographicCamera camera;
    private MovableSubject movableSubject = MovableSubject.getInstance();
    private CollisionController collisionController = new CollisionController();
    private ZombieFactory zombiefactory = new ZombieFactory();
    private Player player;
    private int scale;

    public TiledTestTwo (int scale){
        this.scale = scale;
    }

    @Override
    public void create () {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        TextureAtlas playerAtlas = new TextureAtlas(Gdx.files.internal("sprites.atlas"));

        camera = View.createInstance(this.scale).createCamera(w, h);

        player = new Player(playerAtlas,w/2,h/2,this.scale);
        ArrayList<Zombie> zombieList = zombiefactory.createZombie(10, w, h, this.scale);

        View.getInstance().addSprite(zombieList);
        View.getInstance().addSprite(player);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        View.getInstance().setView(camera);
        View.getInstance().render();

        //Notifies all movable objects about next render
        movableSubject.notifyUpdate();
        collisionController.checkCollisionRectangle(View.getInstance(), player, this.scale);
        movableSubject.playerLocation((int) player.getX(),(int) player.getY());
    }

    @Override
    public boolean keyDown(int keycode) {
        return player.getInputProcessor().keyDown(keycode);
    }

    @Override public boolean keyUp(int keycode) {
        return player.getInputProcessor().keyUp(keycode);
    }

    @Override public boolean keyTyped(char character) {
        return false;
    }

    @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override public boolean scrolled(float amountX, float amountY) {
        return false;
    }

}