package com.mygdx.game.desktop;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;

//This class has been taken from https://gamefromscratch.com/libgdx-tutorial-11-tiled-maps-part-2-adding-a-sprite-and-dealing-with-layers/
//albeit with certain reworks to fit for this project.

public class TiledTestTwo extends ApplicationAdapter implements InputProcessor {
    Texture img;
    TiledMap tiledMap;
    OrthographicCamera camera;
    OrthogonalTiledMapRendererWithSprites tiledMapRenderer;
    Texture texture;
    Sprite sprite;

    @Override
    public void create () {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();

        texture = new Texture(Gdx.files.internal("Adam_idle_16x16.png"));
        sprite = new Sprite(texture);
        sprite.scale(1);

        tiledMap = new TmxMapLoader().load("mapOne.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRendererWithSprites(tiledMap, 1);
        tiledMapRenderer.addSprite(sprite);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.LEFT)
            //camera.translate(-32,0);
            sprite.setPosition(sprite.getX()-32,sprite.getY());
            //texture = new Texture(Gdx.files.internal("Adam_left.png")); <-- Needs doing, obviously not like this

        if(keycode == Input.Keys.RIGHT)
            //camera.translate(32,0);
            sprite.setPosition(sprite.getX()+32,sprite.getY());
            //texture = new Texture(Gdx.files.internal("Adam_right.png")); <-- Needs doing, obviously not like this

        if(keycode == Input.Keys.UP)
            //camera.translate(0,32);
            sprite.setPosition(sprite.getX(),sprite.getY()+32);
            //texture = new Texture(Gdx.files.internal("Adam_forward.png")); <-- Needs doing, obviously not like this

        if(keycode == Input.Keys.DOWN)
            //camera.translate(0,-32);
            sprite.setPosition(sprite.getX(),sprite.getY()-32);
            //texture = new Texture(Gdx.files.internal("Adam_back.png")); <-- Needs doing, obviously not like this

        if(keycode == Input.Keys.NUM_1)
            tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());

        if(keycode == Input.Keys.NUM_2)
            tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());
        return false;
    }

    @Override public boolean keyTyped(char character) {

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 clickCoordinates = new Vector3(screenX,screenY,0);
        Vector3 position = camera.unproject(clickCoordinates);
        sprite.setPosition(position.x, position.y);
        return true;
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

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public boolean scrolled(int amount) {
        return false;
    }
}