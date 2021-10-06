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
    private PlayerController playerController;
    private Animations animations;
    FitViewport viewport;


    public TiledTestTwo (int scale){
        this.scale = scale;
    }

    @Override
    public void create () {
        View.createInstance(this.scale);

        player = new Player(new TextureAtlas(Gdx.files.internal("Player/standIn/standInz.atlas")),640,640,2);
        playerController = new PlayerController(player);
        viewport = new FitViewport(800, 800, camera);

        animations = new Animations(View.getInstance().getBatch(), player);
        View.getInstance().setAnimations(animations);
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = View.getInstance().createCamera(w, h);
        rounds = new Rounds(scale, w, h);
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
        zombieObserver.playerLocation((int) player.getX(),(int) player.getY());
        rounds.checkNewRound(player);
    }
}