package sk.stuba.fiit.factories.weaponfactories;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.stuba.fiit.Weapon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class WeaponFactoryTest {
    WeaponFactory weaponFactory;
    @BeforeEach
    public void setUp() {
        weaponFactory = new WeaponFactory(1, new Vector2(2, 2)) {
            @Override
            public void adjustProjectileTemplate() {

            }

            @Override
            public Weapon create(Object... args) {
                return null;
            }
        };
    }

    @Test
    public void testGetRadiusOfAttacker() {
        assertEquals(1, weaponFactory.getRadiusOfAttacker());
    }

    @Test
    public void testSetRadiusOfAttacker() {
        weaponFactory.setRadiusOfAttacker(4);
        assertEquals(4, weaponFactory.getRadiusOfAttacker());
    }

    @Test
    public void testGetPositionOfAttacker() {
        assertEquals(new Vector2(2, 2), weaponFactory.getPositionOfAttacker());
    }

    @Test
    public void testSetPositionOfAttacker() {
        weaponFactory.setPositionOfAttacker(new Vector2(5, 5));
        assertEquals(new Vector2(5, 5), weaponFactory.getPositionOfAttacker());
    }

    @Test
    public void testGetDefaultConstructor() {
        weaponFactory = new WeaponFactory() {
            @Override
            public void adjustProjectileTemplate() {

            }

            @Override
            public Weapon create(Object... args) {
                return null;
            }
        };
        assertEquals(0, weaponFactory.getRadiusOfAttacker());
        assertNull(weaponFactory.getPositionOfAttacker());
    }
}
