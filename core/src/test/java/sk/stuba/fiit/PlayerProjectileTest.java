package sk.stuba.fiit;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.stuba.fiit.projectiles.PlayerProjectile;
import sk.stuba.fiit.projectiles.Projectile;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerProjectileTest {

    private PlayerProjectile playerProjectile;

    @BeforeEach
    public void setUp() {
        Texture mockTexture = new Texture("empty.png");
        playerProjectile = new PlayerProjectile("Player Projectile", "Player's special projectile", mockTexture, 100, 100, new Vector2(1, 0), 10.0f);
    }

    @Test
    public void testInitialValues() {
        assertEquals("Player Projectile", playerProjectile.getName());
        assertEquals("Player's special projectile", playerProjectile.getDescription());
        assertEquals(100, playerProjectile.getHealth());
        assertEquals(100, playerProjectile.getMaxHealth());
        assertEquals(1, playerProjectile.getDirection().x);
        assertEquals(0, playerProjectile.getDirection().y);
        assertEquals(10.0f, playerProjectile.getSpeed());
    }

    @Test
    public void testClone() {
        Projectile clonedProjectile = playerProjectile.clone();
        assertNotSame(playerProjectile, clonedProjectile);
        assertEquals(playerProjectile.getName(), clonedProjectile.getName());
        assertEquals(playerProjectile.getDescription(), clonedProjectile.getDescription());
        assertEquals(playerProjectile.getHealth(), clonedProjectile.getHealth());
        assertEquals(playerProjectile.getMaxHealth(), clonedProjectile.getMaxHealth());
        assertEquals(playerProjectile.getDirection(), clonedProjectile.getDirection());
        assertEquals(playerProjectile.getSpeed(), clonedProjectile.getSpeed());
    }

    @Test
    public void testTakeDamage() {
        playerProjectile.takeDamage(50);
        assertEquals(0, playerProjectile.getHealth());
    }

    @Test
    public void testDie() {
        assertDoesNotThrow(() -> playerProjectile.die());
    }

    @Test
    public void testDefaultConstructor() {
        PlayerProjectile defaultProjectile = new PlayerProjectile();
        assertEquals("Mock", defaultProjectile.getName());
        assertEquals("Mock", defaultProjectile.getDescription());
        assertEquals(1, defaultProjectile.getHealth());
        assertEquals(1, defaultProjectile.getMaxHealth());
        assertEquals(1, defaultProjectile.getDirection().x);
        assertEquals(1, defaultProjectile.getDirection().y);
        assertEquals(1.0f, defaultProjectile.getSpeed());
    }

    @Test
    public void testMove() {
        float deltaTime = 1.0f;
        Vector2 initialPosition = playerProjectile.getSprite().getPosition().cpy();
        playerProjectile.move(deltaTime);
        Vector2 expectedPosition = initialPosition.cpy().add(new Vector2(playerProjectile.getDirection().x * playerProjectile.getSpeed() * deltaTime, playerProjectile.getDirection().y * playerProjectile.getSpeed() * deltaTime));
        assertEquals(expectedPosition, playerProjectile.getSprite().getPosition());
    }
}
