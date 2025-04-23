package sk.stuba.fiit.factories.spawnerfactories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.stuba.fiit.Timer;
import sk.stuba.fiit.entities.Spawner;
import sk.stuba.fiit.factories.weaponfactories.BigAsteroidEnemyWeaponFactory;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class SpawnerFactoryTest {
    SpawnerFactory spawnerFactory;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        spawnerFactory = new SpawnerFactory() {
            @Override
            public Spawner create(Object... args) {
                return super.create(args);
            }
        };

        Field spawnerTemplateField = SpawnerFactory.class.getDeclaredField("spawnerTemplate");
        spawnerTemplateField.setAccessible(true);
        spawnerTemplateField.set(spawnerFactory, new Spawner("Test", "Test1", null, 1, 1, null, 1, null));
        Field weaponFactoryField = SpawnerFactory.class.getDeclaredField("weaponFactory");
        weaponFactoryField.setAccessible(true);
        weaponFactoryField.set(spawnerFactory, new BigAsteroidEnemyWeaponFactory());
    }

    @Test
    void testCreate(){
        Spawner spawner = spawnerFactory.create();
        assertNotNull(spawner);
        assertEquals("Test", spawner.getName());
        assertEquals("Test1", spawner.getDescription());
        assertEquals(1, spawner.getHealth());
        assertEquals(1, spawner.getMaxHealth());
        assertEquals("Big asteroid Weapon", spawner.getWeapon().getName());
        assertEquals(1, spawner.getPrice());
        assertNotNull(spawner.getSpawnTimer());
    }
}
