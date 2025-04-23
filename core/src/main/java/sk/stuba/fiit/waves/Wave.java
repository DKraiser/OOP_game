package sk.stuba.fiit.waves;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;
import sk.stuba.fiit.entities.Spawner;
import sk.stuba.fiit.enums.WaveRarity;
import sk.stuba.fiit.factories.spawnerfactories.SpawnerFactory;
import sk.stuba.fiit.screens.GameScreen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * Represents a wave of spawners in the game. The wave consists of multiple spawners that are summoned in a circular area
 * and can be retracted (removed) when needed. The wave's rarity, size, and spawn radius are defined by the parameters.
 */
public abstract class Wave {
    protected int waveSize;
    protected float radius;
    protected Vector2 spawnPosition;
    protected WaveRarity waveRarity;
    protected Spawner newSpawner;
    protected Collection<Spawner> summons;
    protected Collection<Spawner> spawnerEnvironment;
    protected SpawnerFactory spawnerFactory;
    protected Random random;
    protected Logger logger;

    /**
     * Summons a new set of spawners within a defined radius and adds them to the wave's environment.
     * Each spawner is positioned randomly within the radius.
     */
    public void summon() {
        float x;
        for (int i = 0; i < waveSize; i++) {
            newSpawner = spawnerFactory.create();

            x = random.nextFloat(-1, 1) * radius;
            spawnPosition = new Vector2(x, (float)(Math.sqrt(radius * radius - x * x) * Math.pow(-1, random.nextInt(0, 3))));
            spawnPosition.add(new Vector2(GameScreen.worldWidth, GameScreen.worldHeight).scl(0.5f));

            newSpawner.setPosition(spawnPosition.cpy());
            newSpawner.getWeapon().update(spawnPosition, newSpawner.getHeight() / 2);
            newSpawner.translate(new Vector2(newSpawner.getWidth(), newSpawner.getHeight()).scl(-0.5f));

            if (newSpawner.getCollider() != null) {
                newSpawner.getCollider().setPosition(spawnPosition.cpy());
            }

            summons.add(newSpawner);
            logger.debug(newSpawner.getName() + " " + i + " position: " + newSpawner.getPosition());
        }
        spawnerEnvironment.addAll(summons);
    }

    /**
     * Retracts (removes) all summoned spawners by setting their "alive" status to false and clearing the summons collection.
     */
    public void retract() {
        for (Spawner spawner : summons) {
            spawner.setAlive(false);
        }
        summons.clear();
    }

    /**
     * Gets the rarity of the wave.
     *
     * @return the {@link WaveRarity} of the wave
     */
    public WaveRarity getWaveRarity() {
        return waveRarity;
    }

    /**
     * Sets the rarity of the wave.
     *
     * @param waveRarity the {@link WaveRarity} to set for the wave
     */
    public void setWaveRarity(WaveRarity waveRarity) {
        this.waveRarity = waveRarity;
    }

    /**
     * Constructs a new wave with the specified parameters. Initializes the random generator, summons collection, and logger.
     *
     * @param waveSize the number of spawners to summon
     * @param radius the radius in which the spawners will be summoned
     * @param spawnerEnvironment the collection of spawners in the environment where the wave will be added
     */
    public Wave(int waveSize, float radius, Collection<Spawner> spawnerEnvironment) {
        this.waveSize = waveSize;
        this.radius = radius;
        this.spawnerEnvironment = spawnerEnvironment;

        summons = new ArrayList<>();
        random = new Random();
        random.setSeed(System.currentTimeMillis());
        logger = new Logger(this.getClass().getName(), Logger.DEBUG);
    }
}
