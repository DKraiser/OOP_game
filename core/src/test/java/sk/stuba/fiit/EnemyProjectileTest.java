package sk.stuba.fiit;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sk.stuba.fiit.effects.Effect;
import sk.stuba.fiit.effects.ParalyseEffect;
import sk.stuba.fiit.entities.player.Player;
import sk.stuba.fiit.events.EnemyKilledEvent;
import sk.stuba.fiit.factories.weaponfactories.WeaponFactory;
import sk.stuba.fiit.interfaces.Damageable;
import sk.stuba.fiit.projectiles.EnemyProjectile;

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
    public void testTakeDamage() {
        int initialHealth = enemyProjectile.getHealth();
        int damage = 30;

        enemyProjectile.takeDamage(damage);
        assertEquals(initialHealth - damage, enemyProjectile.getHealth());

        if (enemyProjectile.getHealth() <= 0) {
            assertEquals("Dead Enemy Projectile", enemyProjectile.getName());
        }
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
        EnemyProjectile clonedProjectile = (EnemyProjectile) enemyProjectile.clone();

        assertNotSame(enemyProjectile, clonedProjectile);
        assertEquals(enemyProjectile.getName(), clonedProjectile.getName());
        assertEquals(enemyProjectile.getDescription(), clonedProjectile.getDescription());
        assertEquals(enemyProjectile.getHealth(), clonedProjectile.getHealth());
        assertEquals(enemyProjectile.getMaxHealth(), clonedProjectile.getMaxHealth());
        assertEquals(enemyProjectile.getSpeed(), clonedProjectile.getSpeed());

        assertNotNull(clonedProjectile.getEffectHandler());
        assertEquals(enemyProjectile.getEffectHandler().getEffects().size(), clonedProjectile.getEffectHandler().getEffects().size());
    }

    @Test
    public void testTakeEffect() {
        Effect effect = new ParalyseEffect(true, 1, 1, enemyProjectile);
        enemyProjectile.takeEffect(effect);
        assertDoesNotThrow(() -> enemyProjectile.takeEffect(effect));
    }

    @Test
    public void testUpdateEffects() {
        float deltaTime = 1.0f;
        assertDoesNotThrow(() -> enemyProjectile.updateEffects(deltaTime));
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
}
