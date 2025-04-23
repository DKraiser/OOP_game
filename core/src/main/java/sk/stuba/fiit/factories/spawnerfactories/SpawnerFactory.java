package sk.stuba.fiit.factories.spawnerfactories;

import sk.stuba.fiit.Timer;
import sk.stuba.fiit.entities.Entity;
import sk.stuba.fiit.entities.Spawner;
import sk.stuba.fiit.factories.weaponfactories.WeaponFactory;
import sk.stuba.fiit.interfaces.Factory;

import java.util.Random;

public abstract class SpawnerFactory implements Factory<Entity> {
    protected Spawner spawnerTemplate;
    protected Spawner newSpawner;
    protected WeaponFactory weaponFactory;
    protected Random rand;

    @Override
    public Spawner create(Object... args) {
        newSpawner = spawnerTemplate.clone();
        newSpawner.setWeapon(weaponFactory.create());
        newSpawner.setSpawnTimer(new Timer(rand.nextFloat(3, 5), rand.nextFloat(0, 3)));
        return newSpawner;
    }

    public SpawnerFactory() {
        rand = new Random();
        rand.setSeed(System.currentTimeMillis());
    }
}
