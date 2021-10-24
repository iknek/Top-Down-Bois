package com.mygdx.game.desktop;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.desktop.sapiens.Player;
import com.mygdx.game.desktop.sapiens.ZombieSubject;
import com.mygdx.game.desktop.views.View;

/**
 * This is the model of the program. It holds the game-loop and the instance of the player.
 */
public class Model extends ApplicationAdapter {
    private OrthographicCamera camera;
    private CollisionController collisionController = new CollisionController();
    private Player player;
    private float scale;
    private Rounds rounds;
    private FitViewport viewport;
    private Music sound;

    public Model(int scale){
        this.scale = scale;
    }

    /**
     * Creates the bare essentials of the program such as the View the player and rounds
     * Also gives the view the instance of player in order to create animations.
     */
    @Override
    public void create () {
        View.createInstance(this.scale);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        player = new Player(new TextureAtlas(Gdx.files.internal("Player/standIn/standInz.atlas")),w/2,h/2,scale);

        sound = Gdx.audio.newMusic(Gdx.files.internal("Music/LoopedVersions/01.mp3"));

        camera = View.getInstance().createCamera(w, h);
        viewport = new FitViewport(w, h, camera);
        rounds = new Rounds(scale);
    }

    @Override
    public void dispose() {
        View.getInstance().dispose();
    }

    /**
     * When the window gets resized it configures the viewport so that the program does not glitch out.
     * @param width the width of the window
     * @param height the height of the window
     */
    @Override
    public void resize(int width, int height) {
       viewport.update(width,height);
    }

    /**
     * The render method is called on loop. This is the continuous game loop.
     * First the camera is updated and the render method is called in View(which renders the map and sprites)
     * Then, using observer pattern, all movable subjects are told to update. This includes the player, zombies and projectiles.
     * Collision controller is called to check al possible collisions and handle the logic behind that.
     * All zombies are then notified of the players position so that they can find their way.
     * Lastly rounds check if a new round needs to start and does if needed.
     */
    @Override
    public void render () {
        sound.setLooping(true);
        sound.play();
        sound.setVolume(1f/2);
        camera.update();
        View.getInstance().updateHud(rounds.getRound(), player.getHealth(), player.getMoney(), player.getWeapon().getName(),player.getWeapon().getAmmoInMagazine(), player.getWeapon().getTotalAmmo());
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        View.getInstance().setView(camera);
        View.getInstance().render();
        if(!View.getInstance().getShopOpen()){
            MovableSubject.getInstance().notifyUpdate();
            collisionController.checkCollisions(View.getInstance(), player, this.scale);
            FollowerSubject.getInstance().playerLocation((int) player.getX(),(int) player.getY());
            player.getHit(ZombieSubject.getInstance().playerHit());
        }
        if(rounds.checkNewRound(player) && (rounds.getRound() > 1) || View.getInstance().getShopOpen()){
            View.getInstance().openShop(player);
        }
    }
}