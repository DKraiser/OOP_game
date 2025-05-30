package sk.stuba.fiit.entities;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;
import sk.stuba.fiit.Collider;
import sk.stuba.fiit.EffectHandler;
import sk.stuba.fiit.Timer;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.effects.Effect;
import sk.stuba.fiit.effects.ParalyseEffect;
import sk.stuba.fiit.factories.projectilefactories.ProjectileFactory;
import sk.stuba.fiit.factories.weaponfactories.WeaponFactory;
import sk.stuba.fiit.interfaces.TakingDamageStrategy;
import sk.stuba.fiit.projectiles.EnemyProjectile;
import sk.stuba.fiit.projectiles.Projectile;
import sk.stuba.fiit.strategies.attacking.DirectionRangedAttackingStrategy;
import sk.stuba.fiit.strategies.takingDamage.ResistanceTakingDamageStrategy;
import sk.stuba.fiit.strategies.takingDamage.StandartTakingDamageStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PlayerTest {
    private Player player;
    private Timer timer;
    private WeaponFactory weaponFactory;

    @BeforeAll
    static void init() {
        new HeadlessApplication(new ApplicationAdapter() {});
    }

    @BeforeEach
    void setUp() {
        timer = new Timer(1, 1);
        weaponFactory = new WeaponFactory() {
            @Override
            public void adjustProjectileTemplate() {

            }

            @Override
            public Weapon create(Object... args) {
                return new Weapon("", "", null, new ProjectileFactory(new Projectile("", "", null, 1, 1, new Vector2(1, 0), 1, 1) {
                    @Override
                    public Projectile clone() {
                        Projectile clone = new Projectile("", "", null, 1, 1, new Vector2(1, 0), 1, 1) {

                            @Override
                            public void die() {

                            }

                            @Override
                            public void takeDamage(int damage) {

                            }

                            @Override
                            public Projectile clone() {
                                return null;
                            }

                            @Override
                            public void onCollision(Entity collisionEntity) {}
                        };
                        clone.setCollider(new Collider(new Circle(1,1,1)));
                        return clone;
                    }

                    @Override
                    public void die() {

                    }

                    @Override
                    public void takeDamage(int damage) {

                    }

                    @Override
                    public void onCollision(Entity collisionEntity) {}
                }, 0, null));
            }
        };
        player = new Player("TestPlayer", "Test Desc", null, 10f, new Vector2(50, 50), 50, 100, 10, new Timer(1), 0, weaponFactory);
    }

    @Test
    public void testGetBalance() {
        assertEquals(0, player.getBalance());
    }

    @Test
    public void testSetBalance() {
        player.setBalance(10);
        assertEquals(10, player.getBalance());
    }

    @Test
    public void testGetHealingPerInterval() {
        assertEquals(10, player.getHealingPerInterval());
    }

    @Test
    public void testSetValidHealingPerInterval() {
        assertDoesNotThrow(() -> player.setHealingPerInterval(15));
        assertEquals(15, player.getHealingPerInterval());
    }

    @Test
    public void testSetInvalidHealingPerInterval() {
        assertThrows(IllegalArgumentException.class, () -> player.setHealingPerInterval(-1));
    }

    @Test
    public void testGetWeapon() {
        assertNotEquals(null, player.getWeapon());
    }

    @Test
    public void testSetWeapon() {
        Weapon newWeapon = new Weapon("n", "n", null, new ProjectileFactory(new Projectile("", "", null, 1, 1, new Vector2(1, 0), 1, 1) {
            @Override
            public Projectile clone() {
                Projectile clone = new Projectile("", "", null, 1, 1, new Vector2(1, 0), 1, 1) {

                    @Override
                    public void die() {

                    }

                    @Override
                    public void takeDamage(int damage) {

                    }

                    @Override
                    public void onCollision(Entity collisionEntity) {}

                    @Override
                    public Projectile clone() {
                        return null;
                    }
                };
                clone.setCollider(new Collider(new Circle(1,1,1)));
                return clone;
            }

            @Override
            public void die() {

            }

            @Override
            public void takeDamage(int damage) {

            }

            @Override
            public void onCollision(Entity collisionEntity) {}
        }, 1, new Vector2(3,3)));

        assertNotEquals(newWeapon, player.getWeapon());
        player.setWeapon(newWeapon);
        assertEquals(newWeapon, player.getWeapon());
    }

    @Test
    public void testGetHealingTimer() {
        assertNotEquals(null, player.getHealingTimer());
    }

    @Test
    public void testSetHealingTimer() {
        Timer newTimer = new Timer(3, 2);
        assertNotEquals(newTimer, player.getHealingTimer());
        player.setHealingTimer(newTimer);
        assertEquals(newTimer, player.getHealingTimer());
    }

    @Test
    public void testSetEffectHandler() {
        EffectHandler newEffectHandler = new EffectHandler();
        player.setEffectHandler(newEffectHandler);
        assertEquals(newEffectHandler, player.getEffectHandler());
    }

    @Test
    void testTakeDamage() {
        player.takeDamage(30);
        assertEquals(20, player.getHealth());
    }

    @Test
    public void testDie() throws NoSuchFieldException, IllegalAccessException {
        player = new Player("TestPlayer", "Test Desc", null, 10f, new Vector2(50, 50), 50, 100, 10, timer, 0, weaponFactory) {
            public boolean isInvoked = false;
            @Override
            public void die() {
                super.die();
                isInvoked = true;
            }
        };
        player.takeDamage(100);
        assertTrue(((Object)player).getClass().getField("isInvoked").getBoolean(player));
    }

    @Test
    void testHeal() {
        player.takeDamage(20);
        player.heal();
        assertEquals(40, player.getHealth());
    }

    @Test
    void testNoOverHeal() {
        player.setHealth(100);
        player.heal();
        assertEquals(100, player.getHealth());
    }

    @Test
    void testOnEnemyKilled() {
        player.onEnemyKilled(50);
        assertEquals(50, player.getBalance());
    }

    @Test
    public void testGetAttackingStrategy() {
        assertInstanceOf(DirectionRangedAttackingStrategy.class, player.getAttackStrategy());
    }

    @Test
    void testAttackWithParalyseEffect() {
        Effect paralyse = new ParalyseEffect(true, 1, 1, player);
        player.getEffectHandler().getEffects().clear();
        player.takeEffect(paralyse);
        List<Projectile> projectiles = new ArrayList<>();
        player.attack(new Vector2(1,1), projectiles);

        assertEquals(0, projectiles.size());
    }

    @Test
    void testAttackWithNoParalyseEffect() {
        player.getEffectHandler().getEffects().clear();
        player.setPosition(new Vector2(1, 1));
        player.getWeapon().update(player.getPosition(), player.getHeight() / 2);
        List<Projectile> projectiles = new ArrayList<>();
        player.attack(new Vector2(1,1), projectiles);

        assertEquals(1, projectiles.size());
    }

    @Test
    public void testGetTakingDamage() {
        assertInstanceOf(StandartTakingDamageStrategy.class, player.getTakingDamage());
    }

    @Test
    public void testSetTakingDamage() {
        TakingDamageStrategy newStrategy = new ResistanceTakingDamageStrategy(player.getTakingDamage(), 2);
        player.setTakingDamage(newStrategy);
        assertEquals(newStrategy, player.getTakingDamage());
    }

    @Test
    void testTickCallsHeal() {
        player.setHealth(1);
        player.tick(1.5f);
        assertNotEquals(1, player.getHealth());
    }

    @Test
    void testTickCallsEffectUpdate() {
        Effect effect = new ParalyseEffect(true, 2, 2, player);
        player.getEffectHandler().takeEffect(effect);
        player.tick(1f);
        assertNotEquals(2, player.getEffectHandler().getEffect(ParalyseEffect.class).getRemainingTime());
    }

    @Test
    public void testFindDirectionVector() {
        Vector2 endpoint = new Vector2(5, 1);
        Vector2 position = player.getPosition().cpy();
        Vector2 startpoint = position.cpy().add(new Vector2(player.getWidth(), player.getHeight()).scl(0.5f));
        Vector2 direction = ((endpoint.cpy()).sub(startpoint)).nor();
        Vector2 expectedDirection = player.findDirectionVector(endpoint);

        assertEquals(expectedDirection.x, direction.x);
        assertEquals(expectedDirection.y, direction.y);
    }

    @Test
    public void testOnCollision() {
        Random randomMock = Mockito.mock(Random.class);
        Mockito.when(randomMock.nextFloat(0f, 1f)).thenReturn(0.95f);
        player.setRandom(randomMock);
        player.onCollision(new EnemyProjectile("Enemy Projectile", "Enemy's projectile", null, 100, 100, new Vector2(1, 0), 10.0f, 1, 1));
        assertNotNull(player.getEffectHandler().getEffect(ParalyseEffect.class));
    }
}
