package sk.stuba.fiit.factories.weaponfactories;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.factories.projectilefactories.EnemyProjectileFactory;
import sk.stuba.fiit.projectiles.EnemyProjectile;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class MissileEnemyWeaponFactoryTest {
    WeaponFactory weaponFactory;
    EnemyProjectile projectileTemplate;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        weaponFactory = new MissileEnemyWeaponFactory(1, new Vector2(2, 2));
        Field fieldProjectileTemplate = WeaponFactory.class.getDeclaredField("projectileTemplate");
        fieldProjectileTemplate.setAccessible(true);
        projectileTemplate = (EnemyProjectile) fieldProjectileTemplate.get(weaponFactory);
    }

    @Test
    public void testAdjustProjectileTemplate() {
        assertEquals("Fast missile from UFO's weapon", projectileTemplate.getName());
        assertEquals("Fast, highly damaging piece of... metal", projectileTemplate.getDescription());
        assertEquals(5, projectileTemplate.getHealth());
        assertEquals(5, projectileTemplate.getMaxHealth());
        assertEquals(5, projectileTemplate.getSpeed());
        assertEquals(2, projectileTemplate.getDamage());
        assertEquals(10, projectileTemplate.getPrice());
        assertEquals(0.5f, projectileTemplate.getWidth());
        assertEquals(0.5f, projectileTemplate.getWidth());
        assertEquals(new Vector2(0.25f, 0.25f), projectileTemplate.getOrigin());
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
        MissileEnemyWeaponFactory sf = new MissileEnemyWeaponFactory();
        assertNotNull(sf);
    }
}
