package sk.stuba.fiit.waves;

import sk.stuba.fiit.entities.Spawner;
import sk.stuba.fiit.enums.WaveRarity;
import sk.stuba.fiit.factories.spawnerfactories.SmallAsteroidSpawnerFactory;

import java.util.Collection;

/**
 * Represents a wave of small asteroid spawners in the game. This class is a specialization of the {@link Wave} class
 * and sets the appropriate spawner factory for small asteroids. The wave is marked as common in terms of its rarity.
 */
public class SmallAsteroidWave extends Wave {

    /**
     * Constructs a new small asteroid wave with the specified parameters. Initializes the spawner factory and sets the wave's rarity.
     *
     * @param waveSize the number of small asteroid spawners to summon
     * @param radius the radius in which the small asteroid spawners will be summoned
     * @param spawnerEnvironment the collection of spawners in the environment where the wave will be added
     */
    public SmallAsteroidWave(int waveSize, float radius, Collection<Spawner> spawnerEnvironment) {
        super(waveSize, radius, spawnerEnvironment);
        spawnerFactory = new SmallAsteroidSpawnerFactory();
        waveRarity = WaveRarity.COMMON;
    }
}
