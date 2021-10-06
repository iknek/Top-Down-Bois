package com.mygdx.game.desktop;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TiledTestTwo extends ApplicationAdapter {
    private OrthographicCamera camera;
    private MovableSubject movableSubject = MovableSubject.getInstance();
    private ZombieObserver zombieObserver = ZombieObserver.getInstance();
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

        player = new Player(new TextureAtlas(Gdx.files.internal("Player/standIn/standInz.atlas")),w/2,h/2,scale);

        View.getInstance().setAnimations(player);

        camera = View.getInstance().createCamera(w, h);
        viewport = new FitViewport(w, h, camera);
        rounds = new Rounds(scale, w, h);
    }

    @Override
    public void resize(int width, int height) {
       viewport.update(width,height);
    }

    @Override
    public void render () {
        camera.update();
        View.getInstance().setView(camera);
        View.getInstance().render();
        movableSubject.notifyUpdate();
        collisionController.checkCollisions(View.getInstance(), player, this.scale);
        zombieObserver.playerLocation((int) player.getX(),(int) player.getY());
        rounds.checkNewRound(player);
    }
}