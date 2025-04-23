package sk.stuba.fiit.waves;

import sk.stuba.fiit.entities.Spawner;
import sk.stuba.fiit.enums.WaveRarity;
import sk.stuba.fiit.factories.spawnerfactories.BigAsteroidSpawnerFactory;

import java.util.Collection;

/**
 * Represents a wave of big asteroid spawners in the game. This class is a specialization of the {@link Wave} class
 * and sets the appropriate spawner factory for big asteroids. The wave is marked as common in terms of its rarity.
 */
public class BigAsteroidWave extends Wave {

    /**
     * Constructs a new big asteroid wave with the specified parameters. Initializes the spawner factory and sets the wave's rarity.
     *
     * @param waveSize the number of big asteroid spawners to summon
     * @param radius the radius in which the big asteroid spawners will be summoned
     * @param spawnerEnvironment the collection of spawners in the environment where the wave will be added
     */
    public BigAsteroidWave(int waveSize, float radius, Collection<Spawner> spawnerEnvironment) {
        super(waveSize, radius, spawnerEnvironment);
        spawnerFactory = new BigAsteroidSpawnerFactory();
        waveRarity = WaveRarity.COMMON;
    }
}
