package sk.stuba.fiit.factories.spawnerfactories;

import sk.stuba.fiit.entities.Spawner;
import sk.stuba.fiit.factories.weaponfactories.BigAsteroidEnemyWeaponFactory;

/**
 * Factory class for creating a Big Asteroid spawner. The Big Asteroid spawner spawns large asteroid enemies
 * equipped with a specific weapon. This class sets up the necessary properties such as the spawner's appearance,
 * size, and weapon factory.
 */
public class BigAsteroidSpawnerFactory extends SpawnerFactory {

    /**
     * Constructs a new BigAsteroidSpawnerFactory.
     * This constructor sets the weapon factory to a BigAsteroidEnemyWeaponFactory
     * and configures the big asteroid spawner template with its appearance and size.
     */
    public BigAsteroidSpawnerFactory() {
        weaponFactory = new BigAsteroidEnemyWeaponFactory();
        spawnerTemplate = new Spawner("Big Asteroid Spawner", "", null, 1, 1, null, 0, null);
        spawnerTemplate.setSize(0, 0);
    }
}
