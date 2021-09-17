package com.mygdx.game.desktop;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;

//This class has been taken from https://gamefromscratch.com/libgdx-tutorial-11-tiled-maps-part-2-adding-a-sprite-and-dealing-with-layers/
//albeit with certain reworks to fit for this project.

public class TiledTestTwo extends ApplicationAdapter implements InputProcessor {
    Texture img;
    OrthographicCamera camera;
    Renderer renderer;
    Player player;
    private SpriteBatch batch;
    private int ispressed = 0;
    MovableSubject movableSubject = MovableSubject.getInstance();


    @Override
    public void create () {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        batch = new SpriteBatch();
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("sprites.atlas"));
        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();
        player = new Player(atlas,w/2,h/2,3);
        renderer = Renderer.getInstance();
        renderer.addSprite(player);
        Gdx.input.setInputProcessor(this);
    }

    public void checkCollisionRectangle(){

        int objectLayerId = 2;
        MapLayer collisionObjectLayer = renderer.getMap().getLayers().get(objectLayerId);
        MapObjects objects = collisionObjectLayer.getObjects();
        // there are several other types, Rectangle is probably the most common one
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();
            //System.out.println(rectangle.y);
            //System.out.println(player.getBoundingRectangle().y);
            rectangle = scaleRectangle(rectangle);
            for (Movable observer : movableSubject.getObservers()){
                if (Intersector.overlaps(rectangle, observer.getBoundingRectangle())){
                    observer.collide(rectangle);
                }
            }

            rectangle = scaleBackRectangle(rectangle);
        }
    }

    private Rectangle scaleRectangle(Rectangle rect){
        rect.x = rect.x*2;
        rect.y = rect.y*2;
        rect.width = rect.width*2;
        rect.height = rect.height*2;
        return rect;
    }

    private Rectangle scaleBackRectangle(Rectangle rect){
        rect.x = rect.x/2;
        rect.y = rect.y/2;
        rect.width = rect.width/2;
        rect.height = rect.height/2;
        return rect;
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        renderer.setView(camera);
        renderer.render();
        //Notifies all movable objects about next render
        movableSubject.notifyUpdate();

        //Det här ska definitivt vara i player klassen!
        // Sets sprite for player (add diagonal sprites?)

        player.changePlayerSprite(ispressed);

        checkCollisionRectangle();
    }



    @Override
    public boolean keyDown(int keycode) {
        ispressed = keycode;
        return player.getInputProcessor().keyDown(keycode);
    }

    @Override public boolean keyUp(int keycode) {
        return player.getInputProcessor().keyUp(keycode);
    }

    @Override public boolean keyTyped(char character) {

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 clickCoordinates = new Vector3(screenX,screenY,0);
        Vector3 position = camera.unproject(clickCoordinates);
        player.setPosition(position.x, position.y);
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