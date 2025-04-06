package sk.stuba.fiit;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sk.stuba.fiit.effects.Effect;
import sk.stuba.fiit.interfaces.Damageable;
import sk.stuba.fiit.projectiles.EnemyProjectile;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyProjectileTest {

    private EnemyProjectile enemyProjectile;
    private Damageable mockTarget;

    @BeforeEach
    public void setUp() {
        Texture mockTexture = new Texture("empty.png");
        enemyProjectile = new EnemyProjectile("Enemy Projectile", "Enemy's projectile", mockTexture, 100, 100, new Vector2(1, 0), 10.0f, 1, 1);
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
        assertEquals(enemyProjectile.getDirection(), clonedProjectile.getDirection());
        assertEquals(enemyProjectile.getSpeed(), clonedProjectile.getSpeed());

        assertNotNull(clonedProjectile.getEffectHandler());
        assertEquals(enemyProjectile.getEffectHandler().getEffects().size(), clonedProjectile.getEffectHandler().getEffects().size());
    }

    @Test
    public void testDefaultConstructor() {
        EnemyProjectile defaultProjectile = new EnemyProjectile();

        assertEquals("Mock", defaultProjectile.getName());
        assertEquals("Mock", defaultProjectile.getDescription());
        assertEquals(1, defaultProjectile.getHealth());
        assertEquals(1, defaultProjectile.getMaxHealth());
        assertEquals(1, defaultProjectile.getDirection().x);
        assertEquals(1, defaultProjectile.getDirection().y);
        assertEquals(1.0f, defaultProjectile.getSpeed());
    }

    @Test
    public void testTakeEffect() {
        Effect mockEffect = Mockito.mock(Effect.class);
        enemyProjectile.takeEffect(mockEffect);
        assertDoesNotThrow(() -> enemyProjectile.takeEffect(mockEffect));
    }

    @Test
    public void testUpdateEffects() {
        float deltaTime = 1.0f;
        assertDoesNotThrow(() -> enemyProjectile.updateEffects(deltaTime));
    }
}
