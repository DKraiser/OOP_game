package sk.stuba.fiit.factories.weaponfactories;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sk.stuba.fiit.Collider;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.factories.projectilefactories.PlayerProjectileFactory;
import sk.stuba.fiit.projectiles.Projectile;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class BasicPlayerWeaponFactoryTest {
    WeaponFactory weaponFactory;
    Projectile projectileTemplate;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        weaponFactory = new BasicPlayerWeaponFactory(1, new Vector2(2, 2));
        Field fieldProjectileTemplate = WeaponFactory.class.getDeclaredField("projectileTemplate");
        fieldProjectileTemplate.setAccessible(true);
        projectileTemplate = (Projectile) fieldProjectileTemplate.get(weaponFactory);
    }

    @Test
    public void testAdjustProjectileTemplate() {
        assertEquals("Basic projectile from player's weapon", projectileTemplate.getName());
        assertEquals("Basic projectile from player's weapon", projectileTemplate.getDescription());
        assertEquals(1, projectileTemplate.getHealth());
        assertEquals(1, projectileTemplate.getMaxHealth());
        assertEquals(10, projectileTemplate.getSpeed());
        assertEquals(1, projectileTemplate.getDamage());
        assertEquals(0.5f, projectileTemplate.getWidth());
        assertEquals(0.5f, projectileTemplate.getWidth());
        assertEquals(new Vector2(0.25f, 0.25f), projectileTemplate.getOrigin());
    }

    @Test
    public void testCreate() throws NoSuchFieldException, IllegalAccessException {
        Weapon weapon = weaponFactory.create();
        Field fieldProjectileFactory = Weapon.class.getDeclaredField("projectileFactory");
        fieldProjectileFactory.setAccessible(true);
        assertInstanceOf(PlayerProjectileFactory.class, fieldProjectileFactory.get(weapon));
    }

    @Test
    public void testDefaultConstructor() {
        BasicPlayerWeaponFactory bf = new BasicPlayerWeaponFactory();
        assertNotNull(bf);
    }
}
