package sk.stuba.fiit;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.stuba.fiit.factories.projectilefactories.ProjectileFactory;
import sk.stuba.fiit.projectiles.PlayerProjectile;
import sk.stuba.fiit.projectiles.Projectile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WeaponTest {
    private Projectile projectile;
    private ProjectileFactory projectileFactory;
    private Weapon weapon;

    @BeforeAll
    public static void init() {
        new HeadlessApplication(new ApplicationAdapter() {});
    }

    @BeforeEach
    void setUp() {
        projectile = new PlayerProjectile("Mock", "Mock", null, 1, 2, null, 5, 10);
        projectile.setCollider(new Collider(new Circle(1, 1, 1)));
        projectileFactory = new ProjectileFactory(projectile, 1, new Vector2(0, 0));

        weapon = new Weapon("Mock", "", null, projectileFactory);
    }

    @Test
    void testUpdate() {
        Vector2 position = new Vector2(5, 10);
        float radius = 1.5f;
        weapon.update(position, radius);
        assertEquals(position, weapon.getPosition());
    }

    @Test
    public void testShoot() {
        List<Projectile> projectiles = new ArrayList<>();

        weapon.shoot(new Vector2(5, 10), projectiles);

        assertFalse(projectiles.isEmpty());
    }
}
