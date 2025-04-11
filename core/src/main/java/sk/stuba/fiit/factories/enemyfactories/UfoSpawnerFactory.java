package sk.stuba.fiit.factories.enemyfactories;

import com.badlogic.gdx.graphics.Texture;
import sk.stuba.fiit.entities.Spawner;
import sk.stuba.fiit.factories.weaponfactories.MissileEnemyWeaponFactory;

public class UfoSpawnerFactory extends SpawnerFactory{

    public UfoSpawnerFactory() {
        weaponFactory = new MissileEnemyWeaponFactory();

        spawnerTemplate = new Spawner("Ufo Spawner", "", new Texture("ufo.png"), 10, 10, null, 0, null);
        spawnerTemplate.setSize(.8f, .8f);
        spawnerTemplate.setOrigin(spawnerTemplate.getWidth() / 2, spawnerTemplate.getHeight() / 2);
    }
}
