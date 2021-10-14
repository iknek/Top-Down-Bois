import static org.junit.jupiter.api.Assertions.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.bullet.collision.ICollide;
import com.mygdx.game.desktop.weapons.*;
import com.mygdx.game.desktop.*;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class OverallTest {

    Firearm firearm;
    Coin coin;
    Player player;

    @Before
    void setup(){
        firearm = new Revolver(2);
        player  = new Player(new TextureAtlas(Gdx.files.internal("Player/standIn/standInz.atlas")),50,50,2);
        View.createInstance(2);
    }

    //////////FIREARMS//////////
    @Test
    void testFirearm(){
        assertAll("Revolver test",
                () -> assertEquals(6, firearm.getAmmoInMagazine()),
                () -> assertEquals(36, firearm.getTotalAmmo()),
                () -> assertEquals("REVOLVER", firearm.getName())
        );
    }

    @Test
    void testFirearmAfterFire(){
        firearm.fire(0,50,50);
        assertEquals(5,firearm.getAmmoInMagazine());
    }

    @Test
    void testFirearmAfterReload(){
        firearm.reloadFirearm();
        assertAll("Reload test",
                () -> assertEquals(6,firearm.getAmmoInMagazine()),
                () -> assertEquals(35,firearm.getTotalAmmo())
        );
    }

    @Test
    void testFirearmEmpty(){
        firearm = new Revolver(2);
        for(int i =0; i <36; i++){
            firearm.fire(0,50,50);
            firearm.reloadFirearm();
        }
        firearm.fire(0,50,50);
        firearm.reloadFirearm();
        assertAll("Firearm empty",
                () -> assertEquals(0, firearm.getAmmoInMagazine()),
                () -> assertEquals(0, firearm.getTotalAmmo())
        );
    }

    //////////COINS//////////
    @Test
    void testCoin(){
        coin = new Coin(50, 50, 2);
        assertAll("Make sure the coin spawns where it should",
                () -> assertEquals(50, coin.getX()),
                () -> assertEquals(50, coin.getY())
        );
    }

    @Test
    void testCoinRemove(){
        MovableSubject.getInstance().attach(coin);
        View.getInstance().addSprite(coin);
        coin.remove();
        assertAll("Coin removes itself",
                () -> assertEquals(0, MovableSubject.getInstance().getObservers().size()),
                () -> assertEquals(0, View.getInstance().getSprites().size())
       );
    }

    //////////MOVABLESUBJECT//////////
    @Test
    void movableSubjectAttachTest(){
        MovableSubject movableSubject = MovableSubject.getInstance();
        movableSubject.attach(player);
        assertTrue(movableSubject.getObservers().contains(player));
    }

    @Test
    void movableSubjectDetachTest(){
        MovableSubject movableSubject = MovableSubject.getInstance();
        movableSubject.detach(player);
        assertFalse(movableSubject.getObservers().contains(player));
    }

    @Test
    void movableSubjectDetachNotAttachedTest(){
        MovableSubject movableSubject = MovableSubject.getInstance();
        assertFalse(movableSubject.detach(player));
    }

    //////////PLAYER//////////
    @Test
    void playerFirearmTest(){
        player = new Player(new TextureAtlas(Gdx.files.internal("Player/standIn/standInz.atlas")),50,50,2);

        assertTrue(player.getWeapon() instanceof AutoRifle);
        //TODO test for changing firearm
    }

    @Test
    void playerGetMovingInitialTest(){
        assertFalse(player.moving());
    }

    @Test
    void playerGetMovingTest(){
        player.setLeft(true);
        assertTrue(player.moving());
    }

    @Test
    void playerGetHealth(){
        assertEquals(100, player.getHealth());
    }

    @Test
    void playerAddHealth(){
        player.getHit(10);
        player.addHealth(5);
        assertEquals(95, player.getHealth());
    }

    @Test
    void playerGetMoneyInitial(){
        assertEquals(0, player.getMoney());
    }

    @Test
    void playerGetCoinTest(){
        player.coinGained();
        player.coinGained();
        player.coinGained();
        assertEquals(3, player.getMoney());
    }

    @Test
    void playerGetHitBooleanTest(){
        assertFalse(player.getPlayerHit());
    }

    @Test
    void playerSetPlayerHitBooleanTest(){
        player.setPlayerHit(true);
        assertTrue(player.getPlayerHit());
    }

    @Test
    void playerFireGun(){
        player.setTriggerPulled(true);
        player.update();
        assertEquals(49, player.getWeapon().getAmmoInMagazine());
    }

    @Test
    void playerReloadGun(){
        player.reload();
        assertAll("Player reload test",
                () -> assertEquals(50, player.getWeapon().getAmmoInMagazine()),
                () -> assertEquals(199, player.getWeapon().getTotalAmmo())
        );
    }

    @Test
    void playerAddedToObservers(){
        assertAll("Player added to observers",
                () -> assertTrue(MovableSubject.getInstance().getObservers().contains(player)),
                () -> assertTrue(View.getInstance().getSprites().contains(player))
        );
    }

    @Test
    void playerRemovedfromObservers(){
        player.getHit(100);
        assertAll("Player added to observers",
                () -> assertFalse(MovableSubject.getInstance().getObservers().contains(player)),
                () -> assertFalse(View.getInstance().getSprites().contains(player))
        );
    }

    //////////PROJECTILE//////////
    @Test
    void projectileCollideTest(){
        Projectile projectile = new Projectile(100, 0, 50, 50, 5, "bullet.png", 2);
        projectile.collide(new Rectangle());
        assertAll("Does projectile get removed when collision happens",
                () -> assertFalse(MovableSubject.getInstance().getObservers().contains(projectile)),
                () -> assertFalse(View.getInstance().getSprites().contains(projectile))
        );
    }

    @Test
    void projectileMoves(){
        Projectile projectile = new Projectile(10, 0, 50, 50, 1, "bullet.png", 2);
        projectile.update();
        assertAll("Does bullet move",
                () -> assertEquals(50, projectile.getX()),
                () -> assertEquals(60, projectile.getY())
        );
    }

    //////////ROUNDS//////////
    @Test
    void newRoundsSpawnZombies(){
        Rounds rounds = new Rounds(2);
        player = new Player(new TextureAtlas(Gdx.files.internal("Player/standIn/standInz.atlas")),50,50,2);
        rounds.checkNewRound(player);
        rounds.checkNewRound(player);
        assertTrue(MovableSubject.getInstance().getObservers().get(1) instanceof Zombie);
    }

    @Test
    void startsNewRound(){
        Rounds rounds = new Rounds(2);
        //Starts first round then spawns in all zombies
        rounds.checkNewRound(player);
        rounds.checkNewRound(player);
        rounds.checkNewRound(player);
        rounds.checkNewRound(player);
        rounds.checkNewRound(player);
        rounds.checkNewRound(player);
        //kills all zombies
        for(Movable o : MovableSubject.getInstance().getObservers()){
            if(o instanceof Zombie){
                ((Zombie) o).getHit(10);
            }
        }
        //checks for new round
        rounds.checkNewRound(player);
        assertEquals(2, rounds.getRound());
    }
}
