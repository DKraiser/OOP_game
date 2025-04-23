package sk.stuba.fiit.factories.spawnerfactories;

import sk.stuba.fiit.Timer;
import sk.stuba.fiit.entities.Entity;
import sk.stuba.fiit.entities.Spawner;
import sk.stuba.fiit.factories.weaponfactories.WeaponFactory;
import sk.stuba.fiit.interfaces.Factory;

import java.util.Random;

/**
 * Abstract factory class for creating spawners in the game. A spawner is an entity that
 * generates other entities over time. This factory manages the creation of spawners
 * and assigns weapons to them.
 */
public abstract class SpawnerFactory implements Factory<Entity> {
    /**
     * The template used to create new spawners.
     */
    protected Spawner spawnerTemplate;

    /**
     * The newly created spawner.
     */
    protected Spawner newSpawner;

    /**
     * The factory used to create weapons for the spawner.
     */
    protected WeaponFactory weaponFactory;

    /**
     * The random number generator used to set random spawn times for the spawner.
     */
    protected Random rand;

    /**
     * Creates a new spawner by cloning the template, assigning it a weapon,
     * and setting up its spawn timer with random values.
     *
     * @param args optional arguments used for customization (not used in this factory)
     * @return the newly created {@link Spawner} instance
     */
    @Override
    public Spawner create(Object... args) {
        newSpawner = spawnerTemplate.clone();
        newSpawner.setWeapon(weaponFactory.create());
        newSpawner.setSpawnTimer(new Timer(rand.nextFloat(3, 5), rand.nextFloat(0, 3)));

        return newSpawner;
    }

    /**
     * Default constructor. Initializes the random number generator with the current system time.
     */
    public SpawnerFactory() {
        rand = new Random();
        rand.setSeed(System.currentTimeMillis());
    }
}
