package sk.stuba.fiit.waves;

import sk.stuba.fiit.entities.Spawner;
import sk.stuba.fiit.enums.WaveRarity;
import sk.stuba.fiit.factories.enemyfactories.BigAsteroidSpawnerFactory;

import java.util.Collection;

public class BigAsteroidWave extends Wave {
    public BigAsteroidWave(int waveSize, float radius, Collection<Spawner> spawnerEnvironment) {
        super(waveSize, radius, spawnerEnvironment);
        spawnerFactory = new BigAsteroidSpawnerFactory();
        waveRarity = WaveRarity.COMMON;
    }
}
