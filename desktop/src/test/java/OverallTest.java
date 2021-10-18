import static org.junit.jupiter.api.Assertions.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.desktop.sapiens.Player;
import com.mygdx.game.desktop.sapiens.Zombie;
import com.mygdx.game.desktop.views.View;
import com.mygdx.game.desktop.weapons.*;
import com.mygdx.game.desktop.*;
import org.junit.Before;
import org.junit.jupiter.api.Test;

public class OverallTest {

    Firearm firearm;
    Coin coin;
    Player player;

    @Before
    public void setup(){
        player  = new Player(new TextureAtlas(Gdx.files.internal("Player/standIn/standInz.atlas")),50,50,2);
        View.createInstance(2);
    }

    //////////FIREARMS//////////
    @Test
    void testFirearmInitialAmmoInMag(){
        firearm = new Revolver(2);
        assertEquals(6, firearm.getAmmoInMagazine());
    }

    @Test
    void testFirearmInitialTotal(){
        firearm = new Revolver(2);
        assertEquals(36, firearm.getTotalAmmo());
    }

    @Test
    void testFirearmName(){
        firearm = new Revolver(2);
        assertEquals("REVOLVER", firearm.getName());
    }

    @Test
    void testFirearmAfterFire(){
        firearm = new Revolver(2);
        firearm.fire(0,50,50);
        assertEquals(5,firearm.getAmmoInMagazine());
    }

    @Test
    void testFirearmAmmoInMagAfterReload(){
        firearm = new Revolver(2);
        firearm.fire(0,50,50);
        firearm.reloadFirearm();
        assertEquals(6,firearm.getAmmoInMagazine());
    }

    @Test
    void testFirearmTotalAfterReload(){
        firearm = new Revolver(2);
        firearm.fire(0,50,50);
        firearm.reloadFirearm();
        assertEquals(35, firearm.getTotalAmmo());
    }

    @Test
    void testFirearmEmptyInMag(){
        firearm = emptyFirearm();
        assertEquals(0, firearm.getAmmoInMagazine());
    }

    @Test
    void testFirearmEmptyTotal(){
        firearm = emptyFirearm();
        assertEquals(0, firearm.getTotalAmmo());
    }

    public Firearm emptyFirearm(){
        firearm = new Revolver(2);
        for(int i =0; i <36; i++){
            firearm.fire(0,50,50);
            firearm.reloadFirearm();
        }
        firearm.fire(0,50,50);
        firearm.reloadFirearm();
        return firearm;
    }

    //////////COINS//////////
    @Test
    void testCoinInitialX(){
        coin = new Coin(50, 50, 2);
        assertEquals(50, coin.getX());
    }

    @Test
    void testCoinInitialY(){
        coin = new Coin(50,50,2);
        assertEquals(50, coin.getY());
    }

    @Test
    void testCoinRemoveMovableSubject(){
        coin = new Coin(50,50,2);
        coin.remove();
        assertEquals(0, MovableSubject.getInstance().getObservers().size());
    }

    @Test
    void testCoinRemoveView(){
        coin = new Coin(50,50,2);
        coin.remove();
        assertEquals(0, View.getInstance().getSprites().size());
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

        assertTrue(player.getWeapon() instanceof Revolver);
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
        assertEquals(5, player.getWeapon().getAmmoInMagazine());
    }

    @Test
    void playerReloadGun(){
        player.reload();
        assertEquals(6, player.getWeapon().getAmmoInMagazine());
    }

    @Test
    void playerSwitchToShotgun(){
        player.setFirearm(1);
        assertTrue(player.getWeapon() instanceof Shotgun);
    }

    @Test
    void playerSwitchToAutoRifle(){
        player.setFirearm(2);
        assertTrue(player.getWeapon() instanceof AutoRifle);
    }

    @Test
    void playerSwitchToRevolver(){
        player.setFirearm(0);
        assertTrue(player.getWeapon() instanceof Revolver);
    }

    @Test
    void playerAddedToObserversMovableSubject(){
        assertTrue(MovableSubject.getInstance().getObservers().contains(player));
    }

    @Test
    void playerAddedToObserversView(){
        assertTrue(View.getInstance().getSprites().contains(player));
    }

    @Test
    void playerRemovedFromObserversMovableSubject(){
        player.getHit(100);
        assertFalse(MovableSubject.getInstance().getObservers().contains(player));
    }

    @Test
    void playerRemovedFromObserversView(){
        assertFalse(View.getInstance().getSprites().contains(player));
    }

    //////////PROJECTILE//////////
    @Test
    void projectileCollideTestMovableSubject(){
        Projectile projectile = new Projectile(100, 0, 50, 50, 5, new Texture(Gdx.files.internal("bullet.png")), 2);
        projectile.collide(new Rectangle());
        assertFalse(MovableSubject.getInstance().getObservers().contains(projectile));
    }

    @Test
    void projectileCollideTestView(){
        Projectile projectile = new Projectile(100, 0, 50, 50, 5, new Texture(Gdx.files.internal("bullet.png")), 2);
        projectile.collide(new Rectangle());
        assertFalse(View.getInstance().getSprites().contains(projectile));
    }

    @Test
    void projectileMovesX(){
        Projectile projectile = new Projectile(10, 90, 50, 50, 1, new Texture(Gdx.files.internal("bullet.png")), 2);
        projectile.update();
        assertEquals(60, projectile.getX());
    }

    @Test
    void projectileMovesY(){
        Projectile projectile = new Projectile(10, 0, 50, 50, 1, new Texture(Gdx.files.internal("bullet.png")), 2);
        projectile.update();
        assertEquals(60, projectile.getY());
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


    //ZombieFactory
    @Test
    void ZombieFactory(){
        assertTrue(true);
    }

    @Test
    void createZombie(){
        ZombieFactory zombieFactory = new ZombieFactory(1);
        zombieFactory.createZombie(5,1);

    }

    //ZombieObserver
    @Test
    void attachZombieObserver(){
        Zombie zombie = new Zombie(new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas")),0,0,1, 1);
        ZombieSubject zombieSubject = new ZombieSubject();
        zombieSubject.attach(zombie);
        assertTrue(zombieSubject.getObservers().contains(zombie));
    }

    @Test
    void detachZombieObserver(){
        Zombie zombie = new Zombie(new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas")),0,0,1, 1);
        ZombieSubject zombieSubject = new ZombieSubject();
        zombieSubject.attach(zombie);
        zombieSubject.detach(zombie);
        assertTrue(zombieSubject.getObservers().isEmpty());
    }

    //TODO make this a test for FollowerObserver instead
    /*
    @Test
    void playerLocation() {
        Zombie zombie = new Zombie(new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas")),20,25,1);
        Zombie zombie2 = new Zombie(new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas")),20,25,1);
        ZombieObserver zombieObserver = new ZombieObserver();
        zombieObserver.attach(zombie);
        zombieObserver.attach(zombie2);
        zombieObserver.playerLocation(15,25);
        assertTrue(zombie.nearPlayer()==0 && zombie2.nearPlayer()==5);
    }
    */

    @Test
    void playerHit() {
        Zombie zombie = new Zombie(new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas")),15,25,1, 1);
        Zombie zombie2 = new Zombie(new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas")),20,25,1, 1);
        ZombieSubject zombieSubject = new ZombieSubject();
        zombieSubject.attach(zombie);
        zombieSubject.attach(zombie2);
        zombie.setHitPlayer(true);
        zombie2.setHitPlayer(true);
        assertEquals(2, zombieSubject.playerHit());
    }

    //Zombie

    @Test
    void nearPlayer() {
        Zombie zombie = new Zombie(new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas")), 10, 10, 1, 1);
        zombie.playerLocation(5, 5);
        //test nearPlayer()
        assertEquals(7, zombie.nearPlayer());
    }

    @Test
    void addedToView(){
        Zombie zombie = new Zombie(new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas")), 10, 10, 1, 1);
        assertTrue(View.getInstance().getSprites().contains(zombie));
    }

    @Test
    void getHit(){
        Zombie zombie = new Zombie(new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas")), 10, 10, 1, 1);
        //test getHit()
        zombie.getHit(1);
        zombie.getHit(1);
        assertFalse(View.getInstance().getSprites().contains(zombie));
    }

    @Test
    void spawnsCoin(){
        Zombie zombie = new Zombie(new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas")), 10, 10, 1, 1);
        zombie.getHit(1);
        zombie.getHit(1);
        assertTrue(View.getInstance().getSprites().get(0) instanceof Coin);
    }

    @Test
    void setMovingTrue(){
        // test setMoving and moving()
        Zombie zombie = new Zombie(new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas")),10,10,1, 1);
        zombie.setMoving(false);
        zombie.setMoving(true);
        assertTrue(zombie.moving());
    }

    @Test
    void setMovingFalse(){
        Zombie zombie = new Zombie(new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas")),10,10,1, 1);
        zombie.setMoving(true);
        zombie.setMoving(false);
        assertFalse(zombie.moving());
    }

    @Test
        void updateAngle(){
        Zombie zombie = new Zombie(new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas")),10,10,1, 1);
        //test updateAngle()
        zombie.playerLocation(40,30);
        zombie.update();
        assertEquals(90, zombie.getRenderAngle());
    }

    @Test
    void setHitPlayer(){
        Zombie zombie = new Zombie(new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas")),10,10,1, 1);
        zombie.setHitPlayer(false);
        zombie.setHitPlayer(true);
        assertTrue(zombie.isHitPlayer());
    }

    @Test
    void setHitPlayerFalse(){
        Zombie zombie = new Zombie(new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas")),10,10,1, 1);
        zombie.setHitPlayer(true);
        zombie.setHitPlayer(false);
        assertFalse(zombie.isHitPlayer());
    }
}