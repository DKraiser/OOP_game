package sk.stuba.fiit.projectiles;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.stuba.fiit.Collider;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerProjectileTest {

    private PlayerProjectile projectile;

    @BeforeEach
    public void setUp() {
        projectile = new PlayerProjectile("Player Projectile", "Player's special projectile", null, 100, 100, new Vector2(1, 0), 10.0f, 1);
        projectile.setCollider(new Collider(new Circle(1, 1, 1)));
    }

    @Test
    public void testInitialValues() {
        assertEquals("Player Projectile", projectile.getName());
        assertEquals("Player's special projectile", projectile.getDescription());
        assertEquals(100, projectile.getHealth());
        assertEquals(100, projectile.getMaxHealth());
        assertEquals(1, projectile.getDirection().x);
        assertEquals(0, projectile.getDirection().y);
        assertEquals(10.0f, projectile.getSpeed());
    }

    @Test
    public void testClone() {
        Projectile clonedProjectile = projectile.clone();
        assertNotSame(projectile, clonedProjectile);
        assertEquals(projectile.getName(), clonedProjectile.getName());
        assertEquals(projectile.getDescription(), clonedProjectile.getDescription());
        assertEquals(projectile.getHealth(), clonedProjectile.getHealth());
        assertEquals(projectile.getMaxHealth(), clonedProjectile.getMaxHealth());
        assertEquals(projectile.getSpeed(), clonedProjectile.getSpeed());
    }

    @Test
    public void testTakeDamage() {
        assertDoesNotThrow(() -> projectile.takeDamage(1));
    }

    @Test
    public void testDie() {
        assertDoesNotThrow(() -> projectile.die());
    }

    @Test
    public void testMove() {
        float deltaTime = 1.0f;
        Vector2 initialPosition = projectile.getPosition().cpy();
        projectile.move(deltaTime);
        Vector2 expectedPosition = initialPosition.cpy().add(new Vector2(projectile.getDirection().x * projectile.getSpeed() * deltaTime, projectile.getDirection().y * projectile.getSpeed() * deltaTime));
        assertEquals(expectedPosition, projectile.getPosition());
    }

    @Test
    public void testOnCollision() {
        Projectile enemy = new EnemyProjectile("Enemy Projectile", "Enemy's projectile", null, 100, 100, new Vector2(1, 0), 10.0f, 1, 1);
        projectile.onCollision(enemy);
        assertFalse(projectile.isAlive());
        assertEquals(99, enemy.getHealth());
    }
}
