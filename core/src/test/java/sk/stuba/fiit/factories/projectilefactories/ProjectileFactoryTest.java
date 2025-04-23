package sk.stuba.fiit.factories.projectilefactories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.stuba.fiit.Collider;
import sk.stuba.fiit.entities.Entity;
import sk.stuba.fiit.projectiles.Projectile;

import static org.junit.jupiter.api.Assertions.*;

class ProjectileFactoryTest {
    private class TestProjectile extends Projectile {
        public TestProjectile(String name, String description, Texture texture, int health, int maxHealth, Vector2 direction, float speed, int damage) {
            super(name, description, texture, health, maxHealth, direction, speed, damage);
        }

        @Override
        public Projectile clone() {
            Projectile clone = new TestProjectile(getName(), getDescription(), getTexture(), getHealth(), getMaxHealth(), getDirection(), getSpeed(), getDamage());
            clone.setCollider(getCollider().clone());
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

    }

    private Projectile projectileTemplate;
    private ProjectileFactory projectileFactory;

    @BeforeEach
    void setUp() {
        projectileTemplate = new TestProjectile("Test Projectile", "Test description", null, 100, 100, new Vector2(1, 1), 5.0f, 1);
        projectileTemplate.setCollider(new Collider(new Circle(1, 1, 1)));
        projectileFactory = new ProjectileFactory(projectileTemplate, 1, new Vector2(2, 2));
    }

    @Test
    public void testGetPositionOfAttacker() {
        assertEquals(new Vector2(2, 2), projectileFactory.getPositionOfAttacker());
    }

    @Test
    public void testSetPositionOfAttacker() {
        projectileFactory.setPositionOfAttacker(new Vector2(1, 1));
        assertEquals(new Vector2(1, 1), projectileFactory.getPositionOfAttacker());
    }

    @Test
    public void testGetRadiusOfAttacker() {
        assertEquals(1, projectileFactory.getRadiusOfAttacker());
    }

    @Test
    public void testSetRadiusOfAttacker() {
        projectileFactory.setRadiusOfAttacker(10);
        assertEquals(10, projectileFactory.getRadiusOfAttacker());
    }

    @Test
    public void testCreate() {
        Projectile projectile = projectileFactory.create(new Vector2(1, 1).nor());
        assertEquals(new Vector2(1, 1).nor(), projectile.getDirection());
        assertEquals(-45, projectile.getRotation());
    }
}
