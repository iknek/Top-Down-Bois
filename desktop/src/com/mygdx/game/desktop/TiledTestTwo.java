package com.mygdx.game.desktop;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class TiledTestTwo extends ApplicationAdapter {
    private OrthographicCamera camera;
    private MovableSubject movableSubject = MovableSubject.getInstance();
    private CollisionController collisionController = new CollisionController();
    private Player player;
    private float scale;
    private Rounds rounds;
    private PlayerController playerController;
    AnimationTest animationTest = new AnimationTest();

    public TiledTestTwo (int scale){
        this.scale = scale;
    }

    @Override
    public void create () {
        View.createInstance(this.scale);
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        //textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Angle1/running/running.atlas"));

        player = new Player(animationTest.getTextureAtlas(),400,400,2);
        playerController = new PlayerController(player);
        camera = View.getInstance().createCamera(w, h);
        animationTest.create(View.getInstance().getBatch(), "Player/Angle1/running/running.atlas");

        rounds = new Rounds(scale,w, h);
    }

    @Override
    public void render () {
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //animationTest.render();
        camera.update();
        View.getInstance().setView(camera);

        View.getInstance().render();
        animationTest.render();
        movableSubject.notifyUpdate();
        collisionController.checkCollisions(View.getInstance(), player, this.scale);

        ZombieObserver.getInstance().playerLocation((int) player.getX(),(int) player.getY());
        rounds.checkNewRound(player);
    }

}