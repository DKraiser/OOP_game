package sk.stuba.fiit.waves;

import sk.stuba.fiit.entities.Spawner;
import sk.stuba.fiit.enums.WaveRarity;
import sk.stuba.fiit.factories.enemyfactories.SmallAsteroidSpawnerFactory;

import java.util.Collection;

public class SmallAsteroidWave extends Wave {

    public SmallAsteroidWave(int waveSize, float radius, Collection<Spawner> spawnerEnvironment) {
        super(waveSize, radius, spawnerEnvironment);
        spawnerFactory = new SmallAsteroidSpawnerFactory();
        waveRarity = WaveRarity.COMMON;
    }
}
