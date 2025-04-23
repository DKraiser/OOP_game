package sk.stuba.fiit.factories.spawnerfactories;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.stuba.fiit.Collider;
import sk.stuba.fiit.entities.Spawner;
import sk.stuba.fiit.factories.weaponfactories.MissileEnemyWeaponFactory;
import sk.stuba.fiit.factories.weaponfactories.MissileEnemyWeaponFactoryTest;
import sk.stuba.fiit.factories.weaponfactories.SmallAsteroidEnemyWeaponFactory;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class UfoSpawnerFactoryTest {
    UfoSpawnerFactory spawnerFactory;

    @BeforeEach
    void setUp() {
        spawnerFactory = new UfoSpawnerFactory();
    }

    @Test
    void testCreate() throws NoSuchFieldException, IllegalAccessException {
        Field fieldSpawnerTemplate = SpawnerFactory.class.getDeclaredField("spawnerTemplate");
        fieldSpawnerTemplate.setAccessible(true);
        Field fieldWeaponFactory = SpawnerFactory.class.getDeclaredField("weaponFactory");
        fieldWeaponFactory.setAccessible(true);
        assertEquals("Ufo Spawner", ((Spawner) fieldSpawnerTemplate.get(spawnerFactory)).getName());
        assertEquals(10, ((Spawner) fieldSpawnerTemplate.get(spawnerFactory)).getHealth());
        assertEquals(10, ((Spawner) fieldSpawnerTemplate.get(spawnerFactory)).getMaxHealth());
        assertEquals(15, ((Spawner) fieldSpawnerTemplate.get(spawnerFactory)).getPrice());
        assertEquals(0.8f, ((Spawner) fieldSpawnerTemplate.get(spawnerFactory)).getWidth());
        assertEquals(0.8f, ((Spawner) fieldSpawnerTemplate.get(spawnerFactory)).getHeight());
        assertInstanceOf(MissileEnemyWeaponFactory.class, fieldWeaponFactory.get(spawnerFactory));
        assertEquals(new Vector2(0.4f, 0.4f), ((Spawner) fieldSpawnerTemplate.get(spawnerFactory)).getOrigin());
        assertEquals(new Collider(new Circle(new Vector2(0, 0), 0.4f)), ((Spawner) fieldSpawnerTemplate.get(spawnerFactory)).getCollider());

        Spawner spawner = spawnerFactory.create();
        assertNotNull(spawner);
    }
}
