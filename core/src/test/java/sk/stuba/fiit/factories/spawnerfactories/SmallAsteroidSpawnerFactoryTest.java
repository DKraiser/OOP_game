package sk.stuba.fiit.factories.spawnerfactories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.stuba.fiit.entities.Spawner;
import sk.stuba.fiit.factories.weaponfactories.SmallAsteroidEnemyWeaponFactory;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class SmallSpawnerFactoryTest {
    SmallAsteroidSpawnerFactory spawnerFactory;

    @BeforeEach
    void setUp() {
        spawnerFactory = new SmallAsteroidSpawnerFactory();
    }

    @Test
    void testCreate() throws NoSuchFieldException, IllegalAccessException {
        Field fieldSpawnerTemplate = SpawnerFactory.class.getDeclaredField("spawnerTemplate");
        fieldSpawnerTemplate.setAccessible(true);
        Field fieldWeaponFactory = SpawnerFactory.class.getDeclaredField("weaponFactory");
        fieldWeaponFactory.setAccessible(true);
        assertEquals("Small Asteroid Spawner", ((Spawner) fieldSpawnerTemplate.get(spawnerFactory)).getName());
        assertEquals(1, ((Spawner) fieldSpawnerTemplate.get(spawnerFactory)).getHealth());
        assertEquals(1, ((Spawner) fieldSpawnerTemplate.get(spawnerFactory)).getMaxHealth());
        assertEquals(0, ((Spawner) fieldSpawnerTemplate.get(spawnerFactory)).getPrice());
        assertEquals(0, ((Spawner) fieldSpawnerTemplate.get(spawnerFactory)).getWidth());
        assertEquals(0, ((Spawner) fieldSpawnerTemplate.get(spawnerFactory)).getHeight());
        assertInstanceOf(SmallAsteroidEnemyWeaponFactory.class, fieldWeaponFactory.get(spawnerFactory));
    }
}
