package sk.stuba.fiit.factories.enemyfactories;

import sk.stuba.fiit.entities.Spawner;
import sk.stuba.fiit.factories.weaponfactories.SmallAsteroidEnemyWeaponFactory;

public class SmallAsteroidSpawnerFactory extends SpawnerFactory{

    public SmallAsteroidSpawnerFactory() {
        weaponFactory = new SmallAsteroidEnemyWeaponFactory();

        spawnerTemplate = new Spawner("Small Asteroid Spawner", "", null, 1, 1, null, 0, null);
        spawnerTemplate.setSize(0, 0);
    }
}
