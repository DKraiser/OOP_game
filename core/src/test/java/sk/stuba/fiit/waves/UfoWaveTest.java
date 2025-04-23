package sk.stuba.fiit.waves;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.stuba.fiit.entities.Spawner;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UfoWaveTest {
    Wave wave;
    List<Spawner> spawners;

    @BeforeEach
    void setUp() {
        new HeadlessApplication(new ApplicationAdapter() {});
        spawners = new ArrayList<>();
        wave = new UfoWave(1, 2, spawners);
    }

    @Test
    public void testRetract() {
        wave.summon();
        wave.retract();
        assertFalse(spawners.isEmpty());
    }
}
