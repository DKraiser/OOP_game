package sk.stuba.fiit.waves;

import sk.stuba.fiit.entities.Spawner;
import sk.stuba.fiit.enums.WaveRarity;
import sk.stuba.fiit.factories.spawnerfactories.UfoSpawnerFactory;

import java.util.Collection;

/**
 * Represents a wave of UFO spawners in the game. This class is a specialization of the {@link Wave} class
 * and overrides certain behavior for UFO-related spawners. The wave is marked as rare in terms of its rarity.
 */
public class UfoWave extends Wave {

    /**
     * Retracts (removes) all summoned UFO spawners. This method is overridden but currently does nothing
     * for UFO waves. In the future, specific retract behavior for UFO spawners can be implemented here.
     */
    @Override
    public void retract() {

    }

    /**
     * Constructs a new UFO wave with the specified parameters. Initializes the spawner factory and sets the wave's rarity.
     *
     * @param waveSize the number of UFO spawners to summon
     * @param radius the radius in which the UFO spawners will be summoned
     * @param spawnerEnvironment the collection of spawners in the environment where the wave will be added
     */
    public UfoWave(int waveSize, float radius, Collection<Spawner> spawnerEnvironment) {
        super(waveSize, radius, spawnerEnvironment);
        spawnerFactory = new UfoSpawnerFactory();
        waveRarity = WaveRarity.RARE;
    }
}
