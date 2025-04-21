package sk.stuba.fiit.waves;

import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.entities.Spawner;
import sk.stuba.fiit.enums.WaveRarity;
import sk.stuba.fiit.factories.enemyfactories.UfoSpawnerFactory;
import sk.stuba.fiit.screens.GameScreen;

import java.util.Collection;

public class UfoWave extends Wave {
    @Override
    public void retract() {

    }

    public UfoWave(int waveSize, float radius, Collection<Spawner> spawnerEnvironment) {
        super(waveSize, radius, spawnerEnvironment);
        spawnerFactory = new UfoSpawnerFactory();
        waveRarity = WaveRarity.RARE;
    }
}
