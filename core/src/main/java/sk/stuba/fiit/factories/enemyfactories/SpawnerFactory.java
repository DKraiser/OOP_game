package sk.stuba.fiit.factories.enemyfactories;

import sk.stuba.fiit.entities.Entity;
import sk.stuba.fiit.entities.Spawner;
import sk.stuba.fiit.factories.weaponfactories.WeaponFactory;
import sk.stuba.fiit.interfaces.Factory;

public abstract class SpawnerFactory implements Factory<Entity> {
    protected Spawner spawnerTemplate;
    protected Spawner newSpawner;
    protected WeaponFactory weaponFactory;

    @Override
    public Spawner create(Object... args) {
        newSpawner = spawnerTemplate.clone();
        newSpawner.setWeapon(weaponFactory.create());
        return newSpawner;
    }
}
