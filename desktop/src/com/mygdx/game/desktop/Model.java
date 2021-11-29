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
 * This class is used only by {@link Main}.
 * This class uses {@link Player}, {@link Rounds}, {@link MovableSubject},
 * {@link FollowerSubject}, {@link ZombieSubject} and {@link CollisionController}.
 * @author imad
 * @author david
 * @author anders
 * @author samuel
 */
public class Model{
    private CollisionController collisionController = new CollisionController();
    private Player player;
    private float scale;
    private Rounds rounds;

    public Model(float scale){
        this.scale = scale;

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        player = new Player(new TextureAtlas(Gdx.files.internal("Player/standIn/standInz.atlas")),w/2,h/2,scale);

        rounds = new Rounds(scale);
    }

    /**
     * The render method is called on loop. This is the continuous game loop.
     * First the camera is updated and the render method is called in View(which renders the map and sprites)
     * Then, using observer pattern, all movable subjects are told to update. This includes the player, zombies and projectiles.
     * Collision controller is called to check al possible collisions and handle the logic behind that.
     * All zombies are then notified of the players position so that they can find their way.
     * Lastly rounds check if a new round needs to start and does if needed.
     */
    public void render () {
        MovableSubject.getInstance().notifyUpdate();
        collisionController.checkCollisions(player, this.scale);
        FollowerSubject.getInstance().playerLocation((int) player.getX(),(int) player.getY());
        player.getHit(ZombieSubject.getInstance().playerHit());
    }

    public boolean getRoundInfo(){
        return rounds.checkNewRound(player) && (rounds.getRound() > 1);
    }

    public int getRoundNo(){
        return rounds.getRound();
    }

    public Player getPlayer(){
        return player;
    }
}