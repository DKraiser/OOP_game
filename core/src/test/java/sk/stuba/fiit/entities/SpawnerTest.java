package sk.stuba.fiit.entities;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.stuba.fiit.Collider;
import sk.stuba.fiit.Timer;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.factories.projectilefactories.ProjectileFactory;
import sk.stuba.fiit.projectiles.PlayerProjectile;
import sk.stuba.fiit.projectiles.Projectile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SpawnerTest {
    Projectile projectileTemplate;
    ProjectileFactory projectileFactory;
    Spawner spawner;

    @BeforeEach
    public void setUp() {
        projectileTemplate = new PlayerProjectile("Mock", "Mock", null, 1, 2, null, 5, 10);
        projectileTemplate.setCollider(new Collider(new Circle(1, 1, 1)));
        projectileFactory = new ProjectileFactory(projectileTemplate, 1, new Vector2(0, 0));

        spawner = new Spawner("", "", null, 5, 10, new Weapon("Mock", "", null, projectileFactory), 1, new Timer(5, 3));
    }

    @Test
    public void testGetWeapon() {
        assertNotNull(spawner.getWeapon());
    }

    @Test
    public void testSetWeapon() {
        Projectile newProjectileTemplate = new PlayerProjectile("Mock", "Mock", null, 1, 2, null, 5, 1);
        newProjectileTemplate.setCollider(new Collider(new Circle(1, 1, 1)));
        ProjectileFactory newProjectileFactory = new ProjectileFactory(projectileTemplate, 2, new Vector2(0, 0));
        Weapon newWeapon = new Weapon("new", "new", null, newProjectileFactory);

        spawner.setWeapon(newWeapon);
        assertEquals(newWeapon, spawner.getWeapon());
    }

    @Test
    public void testGetPrice() {
        assertEquals(1, spawner.getPrice());
    }

    @Test
    public void testSetPrice() {
        spawner.setPrice(5);
        assertEquals(5, spawner.getPrice());
    }

    @Test
    public void testGetSpawnerTimer() {
        assertEquals(new Timer(5,3), spawner.getSpawnTimer());
    }

    @Test
    public void testSetSpawnTimer() {
        spawner.setSpawnTimer(new Timer(10, 9));
        assertEquals(new Timer(10,9), spawner.getSpawnTimer());
    }

    @Test
    public void testAttack() {
        List<Projectile> projectiles = new ArrayList<>();
        Entity target = projectileTemplate.clone();
        target.setPosition(new Vector2(0, 0));
        spawner.getSpawnTimer().setElapsed();
        spawner.attack(target, projectiles);
        assertFalse(projectiles.isEmpty());
    }

    @Test
    public void testDie() {
        spawner.die();
        assertFalse(spawner.isAlive());
    }

    @Test
    public void testTakeDamage() {
        spawner.takeDamage(10);
        assertEquals(0, spawner.getHealth());
    }

    @Test
    public void testOnCollision() {
        spawner.onCollision(projectileTemplate);
        assertEquals(0, spawner.getHealth());
    }

    @Test
    public void testTick() {
        spawner.tick(1);
        assertEquals(4, spawner.getSpawnTimer().getCurrent());
    }

    @Test
    public void testClone() {
        Spawner clone = spawner.clone();
        assertNotSame(spawner, clone);
        assertEquals(spawner.getName(), clone.getName());
        assertEquals(spawner.getDescription(), clone.getDescription());
        assertEquals(spawner.getHealth(), clone.getHealth());
        assertEquals(spawner.getMaxHealth(), clone.getMaxHealth());
        assertEquals(spawner.getTexture(), clone.getTexture());
        assertEquals(spawner.getPrice(), clone.getPrice());
        assertEquals(spawner.getSpawnTimer(), clone.getSpawnTimer());
    }
}
