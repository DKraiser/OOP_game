package sk.stuba.fiit.projectiles;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sk.stuba.fiit.Collider;
import sk.stuba.fiit.EffectHandler;
import sk.stuba.fiit.Timer;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.effects.ExtraHealthEffect;
import sk.stuba.fiit.entities.Player;
import sk.stuba.fiit.events.EnemyKilledEvent;
import sk.stuba.fiit.factories.weaponfactories.WeaponFactory;
import sk.stuba.fiit.interfaces.Damageable;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyProjectileTest {

    private EnemyProjectile enemyProjectile;
    private Damageable mockTarget;

    @BeforeEach
    public void setUp() {
        enemyProjectile = new EnemyProjectile("Enemy Projectile", "Enemy's projectile", null, 100, 100, new Vector2(1, 0), 10.0f, 1, 1);
        enemyProjectile.setCollider(new Collider(new Circle(1, 1, 1)));
        mockTarget = Mockito.mock(Damageable.class);
    }

    @Test
    public void testInitialValues() {
        assertEquals("Enemy Projectile", enemyProjectile.getName());
        assertEquals("Enemy's projectile", enemyProjectile.getDescription());
        assertEquals(100, enemyProjectile.getHealth());
        assertEquals(100, enemyProjectile.getMaxHealth());
        assertEquals(1, enemyProjectile.getDirection().x);
        assertEquals(0, enemyProjectile.getDirection().y);
        assertEquals(10.0f, enemyProjectile.getSpeed());
        assertNotNull(enemyProjectile.getEffectHandler());
    }

    @Test
    public void testAttack() {
        int damage = 20;
        enemyProjectile.attack(mockTarget, damage);
        Mockito.verify(mockTarget).takeDamage(damage);
    }

    @Test
    public void testTakeSmallDamage() {
        int initialHealth = enemyProjectile.getHealth();
        int damage = 30;

        enemyProjectile.takeDamage(damage);
        assertEquals(initialHealth - damage, enemyProjectile.getHealth());
    }

    @Test
    public void testTakeDamageAndDie() {
        int initialHealth = enemyProjectile.getHealth();
        int damage = 100;

        enemyProjectile.takeDamage(damage);
        assertFalse(enemyProjectile.isAlive());
    }

    @Test
    public void testDie() {
        new HeadlessApplication(new ApplicationAdapter() {});
        EnemyKilledEvent.setPlayer(new Player("", "", null, 1, new Vector2(0, 0), 1, 1, 1, new Timer(1, 1), 1, new WeaponFactory() {
            @Override
            public void adjustProjectileTemplate() {

            }

            @Override
            public Weapon create(Object... args) {
                return null;
            }
        }));
        enemyProjectile.die();
        assertDoesNotThrow(() -> enemyProjectile.die());
    }

    @Test
    public void testClone() {
        enemyProjectile.getEffectHandler().takeEffect(new ExtraHealthEffect(1, false, 1, 1, enemyProjectile));

        EnemyProjectile clonedProjectile = (EnemyProjectile) enemyProjectile.clone();

        assertNotSame(clonedProjectile, enemyProjectile);
        assertEquals(clonedProjectile.getName(), enemyProjectile.getName());
        assertEquals(clonedProjectile.getDescription(), enemyProjectile.getDescription());
        assertEquals(clonedProjectile.getHealth(), enemyProjectile.getHealth());
        assertEquals(clonedProjectile.getMaxHealth(), enemyProjectile.getMaxHealth());
        assertEquals(clonedProjectile.getSpeed(), enemyProjectile.getSpeed());

        assertNotNull(clonedProjectile.getEffectHandler());
        assertEquals(enemyProjectile.getEffectHandler().getEffects().size(), clonedProjectile.getEffectHandler().getEffects().size());
    }

    @Test
    public void testGetPrice() {
        assertEquals(1, enemyProjectile.getPrice());
    }

    @Test
    public void testSetPrice() {
        enemyProjectile.setPrice(3);
        assertEquals(3, enemyProjectile.getPrice());
    }

    @Test
    public void testSetEffectHandler() {
        EffectHandler oldEffectHandler = enemyProjectile.getEffectHandler();
        enemyProjectile.setEffectHandler(new EffectHandler());
        assertNotSame(oldEffectHandler, enemyProjectile.getEffectHandler());
    }

    @Test
    public void testOnCollision() {
        enemyProjectile.onCollision(new Player("P", "Player", null, 1, null, 5, 5, 1, new Timer(10), 10000, new WeaponFactory() {
            @Override
            public void adjustProjectileTemplate() {

            }

            @Override
            public Weapon create(Object... args) {
                return null;
            }
        }));

        assertFalse(enemyProjectile.isAlive());
    }
}
