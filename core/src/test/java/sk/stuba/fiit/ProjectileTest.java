package sk.stuba.fiit;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sk.stuba.fiit.interfaces.Damageable;
import sk.stuba.fiit.interfaces.attack.MeleeAttacking;
import sk.stuba.fiit.projectiles.Projectile;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectileTest {

    private class TestProjectile extends Projectile implements Damageable {
        public TestProjectile(String name, String description, Texture texture, int health, int maxHealth, Vector2 direction, float speed) {
            super(name, description, texture, health, maxHealth, direction, speed);
        }

        public TestProjectile() {
            super();
        }

        @Override
        public Projectile clone() {
            return new TestProjectile(getName(), getDescription(), getTexture(), getHealth(), getMaxHealth(), getDirection(), getSpeed());
        }

        @Override
        public void die() {

        }

        @Override
        public void takeDamage(int damage) {

        }
    }

    private TestProjectile projectile;

    @BeforeEach
    public void setUp() {
        Texture mockTexture = new Texture("empty.png");
        projectile = new TestProjectile("Test Projectile", "Test description", mockTexture, 100, 100, new Vector2(1, 1), 5.0f);
    }

    @Test
    public void testInitialValues() {
        assertEquals("Test Projectile", projectile.getName());
        assertEquals("Test description", projectile.getDescription());
        assertEquals(100, projectile.getHealth());
        assertEquals(100, projectile.getMaxHealth());
        assertEquals(1, projectile.getDirection().x);
        assertEquals(1, projectile.getDirection().y);
        assertEquals(5.0f, projectile.getSpeed());
    }

    @Test
    public void testMove() {
        float deltaTime = 1.0f;
        Vector2 initialPosition = projectile.getSprite().getPosition().cpy(); // Assuming getSprite().getPosition() gives the position.

        // Move the projectile
        projectile.move(deltaTime);

        Vector2 expectedPosition = initialPosition.cpy().add(new Vector2(projectile.getDirection().x * projectile.getSpeed() * deltaTime,
            projectile.getDirection().y * projectile.getSpeed() * deltaTime));
        assertEquals(expectedPosition, projectile.getSprite().getPosition());
    }

    @Test
    public void testClone() {
        // Create a clone and check if it's a different instance but with the same properties
        Projectile clonedProjectile = projectile.clone();
        assertNotSame(projectile, clonedProjectile);
        assertEquals(projectile.getName(), clonedProjectile.getName());
        assertEquals(projectile.getDescription(), clonedProjectile.getDescription());
        assertEquals(projectile.getHealth(), clonedProjectile.getHealth());
        assertEquals(projectile.getMaxHealth(), clonedProjectile.getMaxHealth());
        assertEquals(projectile.getDirection(), clonedProjectile.getDirection());
        assertEquals(projectile.getSpeed(), clonedProjectile.getSpeed());
    }

    @Test
    public void testSetAndGetDirection() {
        Vector2 newDirection = new Vector2(0, 1);
        projectile.setDirection(newDirection);
        assertEquals(newDirection, projectile.getDirection());
    }

    @Test
    public void testSetAndGetSpeed() {
        float newSpeed = 10.0f;
        projectile.setSpeed(newSpeed);
        assertEquals(newSpeed, projectile.getSpeed());
    }

    @Test
    public void testSetAndGetAttacker() {
        MeleeAttacking mockAttacker = Mockito.mock(MeleeAttacking.class);
        projectile.setAttacker(mockAttacker);
        assertEquals(mockAttacker, projectile.getAttacker());
    }

    @Test
    public void testDefaultConstructor() {
        Projectile defaultProjectile = new TestProjectile();
        assertEquals("Mock", defaultProjectile.getName());
        assertEquals("Mock", defaultProjectile.getDescription());
        assertEquals(1, defaultProjectile.getHealth());
        assertEquals(1, defaultProjectile.getMaxHealth());
        assertEquals(1, defaultProjectile.getDirection().x);
        assertEquals(1, defaultProjectile.getDirection().y);
        assertEquals(1.0f, defaultProjectile.getSpeed());
    }
}
