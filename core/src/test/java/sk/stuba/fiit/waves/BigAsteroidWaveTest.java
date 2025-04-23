package sk.stuba.fiit.waves;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.stuba.fiit.entities.Spawner;
import sk.stuba.fiit.enums.WaveRarity;
import sk.stuba.fiit.factories.spawnerfactories.BigAsteroidSpawnerFactory;
import sk.stuba.fiit.factories.spawnerfactories.SpawnerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BigAsteroidWaveTest {
    Wave wave;
    List<Spawner> spawners;

    @BeforeEach
    void setUp() {
        spawners = new ArrayList<>();
        wave = new BigAsteroidWave(1, 1, spawners);
    }

    @Test
    public void test() throws NoSuchFieldException, IllegalAccessException {
        Field spawnerFactoryField = Wave.class.getDeclaredField("spawnerFactory");
        spawnerFactoryField.setAccessible(true);
        assertInstanceOf(BigAsteroidSpawnerFactory.class, spawnerFactoryField.get(wave));
        assertEquals(WaveRarity.COMMON, wave.getWaveRarity());
    }
}
