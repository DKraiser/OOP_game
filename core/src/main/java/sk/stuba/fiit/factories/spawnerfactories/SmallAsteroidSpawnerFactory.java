package sk.stuba.fiit.factories.spawnerfactories;

import sk.stuba.fiit.entities.Spawner;
import sk.stuba.fiit.factories.weaponfactories.SmallAsteroidEnemyWeaponFactory;

/**
 * Factory class for creating a Small Asteroid spawner. The Small Asteroid spawner spawns small asteroid enemies
 * equipped with a specific weapon. This class sets up the necessary properties such as the spawner's appearance,
 * size, and weapon factory.
 */
public class SmallAsteroidSpawnerFactory extends SpawnerFactory {

    /**
     * Constructs a new SmallAsteroidSpawnerFactory.
     * This constructor sets the weapon factory to a SmallAsteroidEnemyWeaponFactory
     * and configures the small asteroid spawner template with its appearance and size.
     */
    public SmallAsteroidSpawnerFactory() {
        weaponFactory = new SmallAsteroidEnemyWeaponFactory();
        spawnerTemplate = new Spawner("Small Asteroid Spawner", "", null, 1, 1, null, 0, null);
        spawnerTemplate.setSize(0, 0);
    }
}
