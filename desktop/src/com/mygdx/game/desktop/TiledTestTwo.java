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

public class TiledTestTwo extends ApplicationAdapter {
    private OrthographicCamera camera;
    private MovableSubject movableSubject = MovableSubject.getInstance();
    private CollisionController collisionController = new CollisionController();
    private Player player;
    private float scale;
    private Rounds rounds;
    private PlayerController playerController;


    public TiledTestTwo (int scale){
        this.scale = scale;
    }

    @Override
    public void create () {
        View.createInstance(this.scale);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        TextureAtlas playerAtlas = new TextureAtlas(Gdx.files.internal("sprites.atlas"));

        camera = View.getInstance().createCamera(w, h);

        player = new Player(playerAtlas,w/2,h/2,this.scale);
        playerController = new PlayerController(player);

        rounds = new Rounds(scale,w, h);

        SectorGrid grid = new SectorGrid(w, h, scale);
    }

    @Override
    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        View.getInstance().setView(camera);
        View.getInstance().render();

        ZombieObserver.getInstance().playerLocation(player.getX(), player.getY());
        ZombieObserver.getInstance().updatePath();

        movableSubject.notifyUpdate();
        collisionController.checkCollisions(View.getInstance(), player, this.scale);

        rounds.checkNewRound(player);
    }

}