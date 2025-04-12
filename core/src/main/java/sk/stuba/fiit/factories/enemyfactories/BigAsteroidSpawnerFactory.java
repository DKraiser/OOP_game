package sk.stuba.fiit.factories.enemyfactories;

import sk.stuba.fiit.entities.Spawner;
import sk.stuba.fiit.factories.weaponfactories.BigAsteroidEnemyWeaponFactory;

public class BigAsteroidSpawnerFactory extends SpawnerFactory{

    public BigAsteroidSpawnerFactory() {
        weaponFactory = new BigAsteroidEnemyWeaponFactory();

        spawnerTemplate = new Spawner("Big Asteroid Spawner", "", null, 1, 1, null, 0, null);
        spawnerTemplate.setSize(0,0);
    }
}
