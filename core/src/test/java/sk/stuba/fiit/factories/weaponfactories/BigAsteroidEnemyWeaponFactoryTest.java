package sk.stuba.fiit.factories.weaponfactories;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.factories.projectilefactories.EnemyProjectileFactory;
import sk.stuba.fiit.projectiles.EnemyProjectile;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class BigAsteroidEnemyWeaponFactoryTest {
    WeaponFactory weaponFactory;
    EnemyProjectile projectileTemplate;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        weaponFactory = new BigAsteroidEnemyWeaponFactory(1, new Vector2(2, 2));
        Field fieldProjectileTemplate = WeaponFactory.class.getDeclaredField("projectileTemplate");
        fieldProjectileTemplate.setAccessible(true);
        projectileTemplate = (EnemyProjectile) fieldProjectileTemplate.get(weaponFactory);
    }

    @Test
    public void testAdjustProjectileTemplate() {
        assertEquals("Big asteroid", projectileTemplate.getName());
        assertEquals("Just a big slow asteroid", projectileTemplate.getDescription());
        assertEquals(1, projectileTemplate.getHealth());
        assertEquals(1, projectileTemplate.getMaxHealth());
        assertEquals(2.5f, projectileTemplate.getSpeed());
        assertEquals(2, projectileTemplate.getDamage());
        assertEquals(5, projectileTemplate.getPrice());
        assertEquals(0.8f, projectileTemplate.getWidth());
        assertEquals(0.8f, projectileTemplate.getWidth());
        assertEquals(new Vector2(0.4f, 0.4f), projectileTemplate.getOrigin());
    }

    @Test
    public void testCreate() throws NoSuchFieldException, IllegalAccessException {
        Weapon weapon = weaponFactory.create();
        Field fieldProjectileFactory = Weapon.class.getDeclaredField("projectileFactory");
        fieldProjectileFactory.setAccessible(true);
        assertInstanceOf(EnemyProjectileFactory.class, fieldProjectileFactory.get(weapon));
    }

    @Test
    public void testDefaultConstructor() {
        BigAsteroidEnemyWeaponFactory bf = new BigAsteroidEnemyWeaponFactory();
        assertNotNull(bf);
    }
}
