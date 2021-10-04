package com.mygdx.game.desktop;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

//This class has been taken from https://gamefromscratch.com/libgdx-tutorial-11-tiled-maps-part-2-adding-a-sprite-and-dealing-with-layers/
//albeit with certain reworks to fit for this project.

public class TiledTestTwo extends ApplicationAdapter {
    private OrthographicCamera camera;
    private MovableSubject movableSubject = MovableSubject.getInstance();
    private CollisionController collisionController = new CollisionController();
    private Player player;
    private float scale;
    private Rounds rounds;
    FitViewport viewport;


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

        viewport = new FitViewport(800, 800, camera);
        rounds = new Rounds(scale,w, h);
    }

    @Override
    public void resize(int width, int height) {
       viewport.update(width,height);
    }

    @Override
    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        View.getInstance().setView(camera);
        View.getInstance().render();
        movableSubject.notifyUpdate();
        collisionController.checkCollisions(View.getInstance(), player, this.scale);
        ZombieObserver.getInstance().playerLocation((int) player.getX(),(int) player.getY());
        rounds.checkNewRound(player);
    }
}