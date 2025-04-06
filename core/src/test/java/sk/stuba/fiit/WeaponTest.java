package sk.stuba.fiit;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.stuba.fiit.factories.projectilefactories.ProjectileFactory;
import sk.stuba.fiit.projectiles.PlayerProjectile;
import sk.stuba.fiit.projectiles.Projectile;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WeaponTest {
    private Projectile projectile;
    private ProjectileFactory projectileFactory;
    private Weapon weapon;

    @BeforeEach
    void setUp() {
        projectile = new PlayerProjectile();
        projectileFactory = new ProjectileFactory(projectile, 0, new Vector2(0, 0));
        weapon = new Weapon("Mock", "", null, projectileFactory);
    }

    @Test
    void testUpdate() {
        Vector2 position = new Vector2(5, 10);
        float radius = 1.5f;

        weapon.update(position, radius);

        assertEquals(position, weapon.getPositionOfWeapon());
        verify(projectileFactory).setPositionOfAttacker(position);
        verify(projectileFactory).setRadiusOfAttacker(radius);
    }

    @Test
    void testShoot() {
        Vector2 direction = new Vector2(1, 0);
        Projectile projectile = new PlayerProjectile();
        Collection<Projectile> projectiles = new ArrayList<>();

        when(projectileFactory.create(direction)).thenReturn(projectile);

        weapon.shoot(direction, projectiles);

        assertTrue(projectiles.contains(projectile));
        verify(projectileFactory).create(direction);
    }
}
