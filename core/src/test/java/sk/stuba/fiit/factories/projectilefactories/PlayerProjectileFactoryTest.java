package sk.stuba.fiit.factories.projectilefactories;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.stuba.fiit.Collider;
import sk.stuba.fiit.projectiles.PlayerProjectile;
import sk.stuba.fiit.projectiles.Projectile;

import static org.junit.jupiter.api.Assertions.*;

class PlayerProjectileFactoryTest {
    private PlayerProjectile projectileTemplate;
    private PlayerProjectileFactory projectileFactory;

    @BeforeEach
    void setUp() {
        projectileTemplate = new PlayerProjectile("Test Projectile", "Test description", null, 100, 100, new Vector2(1, 1), 5.0f, 1);
        projectileTemplate.setCollider(new Collider(new Circle(1, 1, 1)));
        projectileFactory = new PlayerProjectileFactory(projectileTemplate, 1, new Vector2(2, 2));
    }

    @Test
    public void testCreate() {
        Projectile projectile = projectileFactory.create(new Vector2(1, 1).nor());
        assertEquals(new Vector2(1, 1).nor(), projectile.getDirection());
        assertEquals(-45, projectile.getRotation());
    }
}
