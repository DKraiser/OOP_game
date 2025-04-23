package sk.stuba.fiit.waves;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.stuba.fiit.Collider;
import sk.stuba.fiit.entities.Spawner;
import sk.stuba.fiit.enums.WaveRarity;
import sk.stuba.fiit.factories.spawnerfactories.SpawnerFactory;
import sk.stuba.fiit.factories.weaponfactories.BigAsteroidEnemyWeaponFactory;
import sk.stuba.fiit.screens.GameScreen;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WaveTest {
    List<Spawner> spawners;
    SpawnerFactory spawnerFactory;
    Wave wave;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        new HeadlessApplication(new ApplicationAdapter() {});
        spawnerFactory = new SpawnerFactory() {
            @Override
            public Spawner create(Object... args) {
                return super.create(args);
            }
        };
        Spawner spawner = new Spawner("Test", "Test1", null, 1, 1, null, 1, null);
        spawner.setCollider(new Collider(new Circle(1,1,1)));

        Field spawnerTemplateField = SpawnerFactory.class.getDeclaredField("spawnerTemplate");
        spawnerTemplateField.setAccessible(true);
        spawnerTemplateField.set(spawnerFactory, spawner);
        Field weaponFactoryField = SpawnerFactory.class.getDeclaredField("weaponFactory");
        weaponFactoryField.setAccessible(true);
        weaponFactoryField.set(spawnerFactory, new BigAsteroidEnemyWeaponFactory());

        spawners = new ArrayList<Spawner>();
        wave = new Wave(2, 5, spawners) {};
        Field f = Wave.class.getDeclaredField("spawnerFactory");
        f.setAccessible(true);
        f.set(wave, spawnerFactory);
    }

    @Test
    public void testSummon() {
        wave.summon();
        assertEquals(2, spawners.size());
        assertEquals(5, (spawners.getFirst().getPosition().add(new Vector2(spawners.getFirst().getWidth() / 2, spawners.getFirst().getHeight() / 2))).dst(GameScreen.worldWidth / 2, GameScreen.worldHeight / 2));
    }

    @Test
    public void testRetract() {
        wave.summon();
        wave.retract();
        for (Spawner spawner : spawners) {
            assertFalse(spawner.isAlive());
        }
    }

    @Test
    public void testGetAnd() {
        wave.setWaveRarity(WaveRarity.LEGENDARY);
        assertEquals(WaveRarity.LEGENDARY, wave.getWaveRarity());
    }
}
