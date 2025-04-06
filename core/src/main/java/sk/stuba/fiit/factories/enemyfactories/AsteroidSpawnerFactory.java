package sk.stuba.fiit.factories.enemyfactories;

import com.badlogic.gdx.graphics.Texture;
import sk.stuba.fiit.Timer;
import sk.stuba.fiit.entities.Spawner;
import sk.stuba.fiit.factories.weaponfactories.AsteroidEnemyWeaponFactory;

import java.util.Random;

public class AsteroidSpawnerFactory extends SpawnerFactory{

    public AsteroidSpawnerFactory() {
        weaponFactory = new AsteroidEnemyWeaponFactory();

        spawnerTemplate = new Spawner("Asteroid Spawner", "", new Texture("empty.png"), 1, 1, null, 0, null);
        spawnerTemplate.getSprite().setSize(.8f, .8f);
        spawnerTemplate.getSprite().setOrigin(spawnerTemplate.getSprite().getWidth() / 2, spawnerTemplate.getSprite().getHeight() / 2);
    }
}
