import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.desktop.coins.Coin;
import com.mygdx.game.desktop.sapiens.Player;
import com.mygdx.game.desktop.sapiens.Zombie;
import com.mygdx.game.desktop.sapiens.ZombieSubject;
import com.mygdx.game.desktop.views.View;
import com.mygdx.game.desktop.weapons.*;
import com.mygdx.game.desktop.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(DummyGame.class)
public class OverallTest {

    Firearm firearm;
    Coin coin;
    Player player;

    @Before
    public void setup() {
        View.createInstance(1);
        List<Movable> list = MovableSubject.getInstance().getObservers();
        for (Movable movable : list) {
            MovableSubject.getInstance().detach(movable);
        }
        player  = new Player(new TextureAtlas(Gdx.files.internal("Player/standIn/standInz.atlas")),50,50,1);
    }

    //////////FIREARMS//////////
    @org.junit.Test
    public void testFirearmInitialAmmoInMag(){
        firearm = new Revolver(2);
        assertEquals(6, firearm.getAmmoInMagazine());
    }


    @org.junit.Test
    public void testFirearmInitialTotal(){
        firearm = new Revolver(2);
        assertEquals(54, firearm.getTotalAmmo());
    }

    @org.junit.Test
    public void testFirearmName(){
        firearm = new Revolver(2);
        assertEquals("REVOLVER", firearm.getName());
    }

    @org.junit.Test
    public void testFirearmAfterFire(){
        firearm = new Revolver(2);
        firearm.fire(0,50,50,false);
        assertEquals(5,firearm.getAmmoInMagazine());
    }

    @org.junit.Test
    public void testFirearmAmmoInMagAfterReload(){
        firearm = new Revolver(2);
        firearm.fire(0,50,50,false);
        firearm.reloadFirearm();
        assertEquals(6,firearm.getAmmoInMagazine());
    }

    @org.junit.Test
    public void testFirearmTotalAfterReload() throws InterruptedException {
        firearm = new Revolver(1);
        player.setFirearm(0);
        firearm.fire(0,50,50,false);
        firearm.reloadFirearm();
        assertEquals(53, firearm.getTotalAmmo());
    }

    @org.junit.Test
    public void testFirearmEmptyInMag() {
        Firearm firearm = new Revolver(1);
        firearm = emptyFirearm(firearm);
        assertEquals(0, firearm.getAmmoInMagazine());
    }

    @org.junit.Test
    public void testFirearmEmptyTotal(){
        Firearm firearm = new Revolver(1);
        while (firearm.getTotalAmmo() > 0) {
            firearm = emptyFirearm(firearm);
            firearm.reloadFirearm();
            System.out.println("RELOADING!!! total ammo left: " + firearm.getTotalAmmo());
        }
        System.out.println("Out of ammo");
        assertEquals(0, firearm.getTotalAmmo());
    }

    public Firearm emptyFirearm(Firearm firearm){
        for(int i =0; i <firearm.getAmmoInMagazine()+5; i++){
            int numberOfSprites = View.getInstance().getSprites().size();
            while (View.getInstance().getSprites().size() == numberOfSprites) {
                firearm.fire(0,50,50,false);
                //wait for firearm to fire
            }
            System.out.println("Boom! "+firearm.getAmmoInMagazine());
        }
        return firearm;
    }

    //////////COINS//////////
    @org.junit.Test
    public void testCoinInitialX(){
        coin = new Coin(50, 50, 2);
        assertEquals(50, coin.getX(),1);
    }

    @org.junit.Test
    public void testCoinInitialY(){
        coin = new Coin(50,50,2);
        assertEquals(50, coin.getY(),1);
    }

    @org.junit.Test
    public void testCoinRemoveMovableSubject(){
        coin = new Coin(50,50,2);
        coin.remove();
        assertFalse(MovableSubject.getInstance().getObservers().contains(coin));
    }

    @org.junit.Test
    public void testCoinRemoveView(){
        coin = new Coin(50,50,2);
        coin.remove();
        assertFalse(View.getInstance().getSprites().contains(coin));
    }

    //////////MOVABLESUBJECT//////////
    @org.junit.Test
    public void movableSubjectAttachTest(){
        MovableSubject movableSubject = MovableSubject.getInstance();
        movableSubject.attach(player);
        assertTrue(movableSubject.getObservers().contains(player));
    }

    @org.junit.Test
    public void movableSubjectDetachTest(){
        MovableSubject movableSubject = MovableSubject.getInstance();
        movableSubject.detach(player);
        assertFalse(movableSubject.getObservers().contains(player));
    }

    @org.junit.Test
    public void movableSubjectDetachNotAttachedTest(){
        MovableSubject movableSubject = MovableSubject.getInstance();
        assertTrue(movableSubject.detach(player));
    }

    //////////PLAYER//////////
    @org.junit.Test
    public void playerFirearmTest(){
        assertTrue(player.getWeapon() instanceof Revolver);
    }

    @org.junit.Test
    public void playerGetMovingInitialTest(){
        assertFalse(player.moving());
    }

    @org.junit.Test
    public void playerGetMovingTest(){
        player.setLeft(true);
        assertTrue(player.moving());
    }

    @org.junit.Test
    public void playerGetHealth(){
        assertEquals(3, player.getHealth());
    }

    @org.junit.Test
    public void playerAddHealth(){
        player.getHit(2);
        player.addHealth(5);
        assertEquals(6, player.getHealth());
    }

    @org.junit.Test
    public void playerGetMoneyInitial(){
        assertEquals(0, player.getMoney());
    }

    @org.junit.Test
    public void playerGetCoinTest(){
        player.coinGained();
        player.coinGained();
        player.coinGained();
        assertEquals(3, player.getMoney());
    }

    @org.junit.Test
    public void playerGetHitBooleanTest(){
        assertFalse(player.getPlayerHit());
    }

    @org.junit.Test
    public void playerSetPlayerHitBooleanTest(){
        player.setPlayerHit(true);
        assertTrue(player.getPlayerHit());
    }

    @org.junit.Test
    public void playerFireGun(){
        player.setTriggerPulled(true);
        player.update();
        assertEquals(5, player.getWeapon().getAmmoInMagazine());
    }

    @org.junit.Test
    public void playerReloadGun(){
        player.reload();
        assertEquals(6, player.getWeapon().getAmmoInMagazine());
    }

    @org.junit.Test
    public void playerSwitchToShotgun(){
        player.giveWeapon(new Shotgun(1),0);
        player.setFirearm(1);
        assertTrue(player.getWeapon() instanceof Shotgun);
    }

    @org.junit.Test
    public void playerSwitchToAutoRifle(){
        player.giveWeapon(new AutoRifle(1),0);
        player.setFirearm(1);
        player.setFirearm(2);
        assertTrue(player.getWeapon() instanceof AutoRifle);
    }

    @org.junit.Test
    public void playerSwitchToRevolver(){
        player.setFirearm(0);
        assertTrue(player.getWeapon() instanceof Revolver);
    }

    @org.junit.Test
    public void playerAddedToObserversMovableSubject(){
        assertTrue(MovableSubject.getInstance().getObservers().contains(player));
    }

    @org.junit.Test
    public void playerAddedToObserversView(){
        assertTrue(View.getInstance().getSprites().contains(player));
    }

    @org.junit.Test
    public void playerRemovedFromObserversMovableSubject(){
        player.getHit(100);
        assertFalse(MovableSubject.getInstance().getObservers().contains(player));
    }

    @org.junit.Test
    public void playerRemovedFromObserversView(){
        assertTrue(View.getInstance().getSprites().contains(player));
    }

    //////////PROJECTILE//////////
    @org.junit.Test
    public void projectileCollideTestMovableSubject(){
        Projectile projectile = new Projectile(100, 0, 50, 50, 5, new Texture(Gdx.files.internal("bullet.png")), 2);
        projectile.collide(new Rectangle());
        assertFalse(MovableSubject.getInstance().getObservers().contains(projectile));
    }

    @org.junit.Test
    public void projectileCollideTestView(){
        Projectile projectile = new Projectile(100, 0, 50, 50, 5, new Texture(Gdx.files.internal("bullet.png")), 2);
        projectile.collide(new Rectangle());
        assertFalse(View.getInstance().getSprites().contains(projectile));
    }

    @org.junit.Test
    public void projectileMovesX(){
        Projectile projectile = new Projectile(10, 90, 50, 50, 1, new Texture(Gdx.files.internal("bullet.png")), 2);
        projectile.update();
        assertEquals(50, projectile.getX(),1);
    }

    @org.junit.Test
    public void projectileMovesY(){
        Projectile projectile = new Projectile(10, 0, 50, 50, 1, new Texture(Gdx.files.internal("bullet.png")), 2);
        projectile.update();
        assertEquals(50, projectile.getY(),1);
    }

    //////////ROUNDS//////////
    @org.junit.Test
    public void newRoundsSpawnZombies(){
        Rounds rounds = new Rounds(1);
        rounds.checkNewRound(player);
        rounds.checkNewRound(player);
        for (int i = 0; i < MovableSubject.getInstance().getObservers().size(); i++) {
            System.out.println(MovableSubject.getInstance().getObservers().get(i).getClass());
        }
        // Vi har 3 players attached till movable subject av nån anledning
        assertTrue(MovableSubject.getInstance().getObservers().get(1) instanceof Zombie);
    }

    @org.junit.Test
    public void startsNewRound(){
        Rounds rounds = new Rounds(1);
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

    //////////SPAWNPOINT//////////
    @org.junit.Test
    public void Spawnpoint(){
        RectangleMapObject rectangleMapObject = new RectangleMapObject();
        rectangleMapObject.getRectangle().setX(123);
        rectangleMapObject.getRectangle().setY(456);
        rectangleMapObject.getRectangle().setWidth(20);
        rectangleMapObject.getRectangle().setHeight(30);
        Spawnpoint spawnpoint = new Spawnpoint(rectangleMapObject,1);
        assertEquals(133,spawnpoint.getX(),1);
    }

    @org.junit.Test
    public void Spawnpoint2(){
        RectangleMapObject rectangleMapObject = new RectangleMapObject();
        rectangleMapObject.getRectangle().setX(123);
        rectangleMapObject.getRectangle().setY(456);
        rectangleMapObject.getRectangle().setWidth(20);
        rectangleMapObject.getRectangle().setHeight(30);
        Spawnpoint spawnpoint = new Spawnpoint(rectangleMapObject,1);
        assertEquals(471,spawnpoint.getY(),1);
    }

    //////////ZOMBIEFACTORY//////////
    @org.junit.Test
    public void ZombieFactory(){
        assertTrue(true);
    }

    @org.junit.Test
    public void createZombie(){
        View view = View.getInstance();
        List<Sprite> sprites = new ArrayList<>(view.getSprites());
        for (Sprite value : sprites) {
            view.removeSprite(value);
        }
        ZombieFactory zombieFactory = new ZombieFactory(1);
        zombieFactory.createZombie(5,1, new Spawnpoint(new RectangleMapObject(), 2));
        List<Sprite> list = View.getInstance().getSprites();
        for (Sprite sprite : list) {
            assertTrue(sprite instanceof Zombie);
        }
    }

    //////////ZOMBIESUBJECT//////////
    @org.junit.Test
    public void attach(){
        Zombie zombie = new Zombie(new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas")),0,0,1, 1);
        ZombieSubject zombieSubject = new ZombieSubject();
        zombieSubject.attach(zombie);
        assertTrue(zombieSubject.getObservers().contains(zombie));
    }

    @org.junit.Test
    public void detach(){
        Zombie zombie = new Zombie(new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas")),0,0,1, 1);
        ZombieSubject zombieSubject = new ZombieSubject();
        zombieSubject.attach(zombie);
        zombieSubject.detach(zombie);
        assertTrue(zombieSubject.getObservers().isEmpty());
    }

    @org.junit.Test
    public void playerHit() {
        Zombie zombie = new Zombie(new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas")),15,25,1, 1);
        Zombie zombie2 = new Zombie(new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas")),20,25,1, 1);
        ZombieSubject zombieSubject = new ZombieSubject();
        zombieSubject.attach(zombie);
        zombieSubject.attach(zombie2);
        zombie.setHitPlayer(true);
        zombie2.setHitPlayer(true);
        assertEquals(2, zombieSubject.playerHit());
    }

    //////////ZOMBIE//////////

    @org.junit.Test
    public void nearPlayer() {
        Zombie zombie = new Zombie(new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas")), 10, 10, 1, 1);
        zombie.playerLocation(5, 5);
        //test nearPlayer()
        assertEquals(7, zombie.nearPlayer());
    }

    @org.junit.Test
    public void addedToView(){
        Zombie zombie = new Zombie(new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas")), 10, 10, 1, 1);
        assertTrue(View.getInstance().getSprites().contains(zombie));
    }

    @org.junit.Test
    public void getHit(){
        Zombie zombie = new Zombie(new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas")), 10, 10, 1, 1);
        //test getHit()
        zombie.getHit(1);
        zombie.getHit(1);
        assertFalse(View.getInstance().getSprites().contains(zombie));
    }

    @org.junit.Test
    public void spawnsCoin(){
        List<Sprite> sprites = View.getInstance().getSprites();
        Zombie zombie = new Zombie(new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas")), 10, 10, 1, 1);
        zombie.getHit(10);
        List<Sprite> spritesWithExtraCoin = View.getInstance().getSprites();
        spritesWithExtraCoin.removeAll(sprites);
        assertTrue(spritesWithExtraCoin.get(0) instanceof Coin);
    }

    @org.junit.Test
    public void setMovingTrue(){
        // test setMoving and moving()
        Zombie zombie = new Zombie(new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas")),10,10,1, 1);
        zombie.setMoving(false);
        zombie.setMoving(true);
        assertTrue(zombie.moving());
    }

    @org.junit.Test
    public void setMovingFalse(){
        Zombie zombie = new Zombie(new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas")),10,10,1, 1);
        zombie.setMoving(true);
        zombie.setMoving(false);
        assertFalse(zombie.moving());
    }

    @org.junit.Test
    public void updateAngle(){
        Zombie zombie = new Zombie(new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas")),10,10,1, 1);
        //test updateAngle()
        zombie.playerLocation(40,30);
        zombie.update();
        assertEquals(90, zombie.getRenderAngle());
    }

    @org.junit.Test
    public void setHitPlayer(){
        Zombie zombie = new Zombie(new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas")),10,10,1, 1);
        zombie.setHitPlayer(false);
        zombie.setHitPlayer(true);
        assertTrue(zombie.isHitPlayer());
    }

    @org.junit.Test
    public void setHitPlayerFalse(){
        Zombie zombie = new Zombie(new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas")),10,10,1, 1);
        zombie.setHitPlayer(true);
        zombie.setHitPlayer(false);
        assertFalse(zombie.isHitPlayer());
    }

    //////////FOLLOWERSUBJECT//////////
    @Test
    public void playerLocation() {
        Zombie zombie = new Zombie(new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas")),15,25,1,1);
        Zombie zombie2 = new Zombie(new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas")),20,25,1,1);
        FollowerSubject subject = new FollowerSubject();
        subject.attach(zombie);
        subject.attach(zombie2);
        subject.playerLocation(15,25);
        assertEquals(0,zombie.nearPlayer());
    }

    @Test
    public void playerLocation2() {
        Zombie zombie = new Zombie(new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas")),15,25,1,1);
        Zombie zombie2 = new Zombie(new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas")),20,25,1,1);
        FollowerSubject subject = new FollowerSubject();
        subject.attach(zombie);
        subject.attach(zombie2);
        subject.playerLocation(15,25);
        assertEquals(5,zombie2.nearPlayer());
    }
}