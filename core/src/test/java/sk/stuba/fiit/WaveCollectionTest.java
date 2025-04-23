package sk.stuba.fiit;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.stuba.fiit.entities.Spawner;
import sk.stuba.fiit.waves.BigAsteroidWave;
import sk.stuba.fiit.waves.SmallAsteroidWave;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class WaveCollectionTest {
    WaveCollection waveCollection;

    @BeforeEach
    void setUp() {
        new HeadlessApplication(new ApplicationAdapter() {});
        waveCollection = new WaveCollection();
    }

    @Test
    public void testAddWave() {
        assertTrue(waveCollection.isEmpty());
        waveCollection.addWave(new BigAsteroidWave(1, 2, new ArrayList<Spawner>()));
        assertFalse(waveCollection.isEmpty());
    }

    @Test
    public void testGetExistingWave() {
        waveCollection.addWave(new BigAsteroidWave(1, 2, new ArrayList<Spawner>()));
        assertInstanceOf(BigAsteroidWave.class, waveCollection.getWave(0.5f));
    }

    @Test
    public void testGetNotExistingWave() {
        assertNull(waveCollection.getWave(0.5f));
    }

    @Test
    public void testCalculateWaveSelectionFloats() {
        waveCollection.addWave(new BigAsteroidWave(1, 2, new ArrayList<Spawner>()));
        waveCollection.addWave(new SmallAsteroidWave(1, 2, new ArrayList<Spawner>()));
        waveCollection.addWave(new SmallAsteroidWave(1, 2, new ArrayList<Spawner>()));
        waveCollection.addWave(new BigAsteroidWave(1, 2, new ArrayList<Spawner>()));

        Set<Float> keys = new HashSet<>();
        keys.add(0.25f);
        keys.add(0.5f);
        keys.add(0.75f);
        keys.add(1f);
        assertEquals(keys, waveCollection.keySet());
    }
}
