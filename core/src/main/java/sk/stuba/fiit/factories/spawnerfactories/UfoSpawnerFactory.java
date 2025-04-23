package sk.stuba.fiit.factories.spawnerfactories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.Collider;
import sk.stuba.fiit.MyGame;
import sk.stuba.fiit.Timer;
import sk.stuba.fiit.entities.Spawner;
import sk.stuba.fiit.factories.weaponfactories.MissileEnemyWeaponFactory;

public class UfoSpawnerFactory extends SpawnerFactory{

    public UfoSpawnerFactory() {
        weaponFactory = new MissileEnemyWeaponFactory();

        spawnerTemplate = new Spawner("Ufo Spawner", "", MyGame.TESTMODE ? null : new Texture("ufo.png"), 10, 10, null, 15, null);
        spawnerTemplate.setSize(.8f, .8f);
        spawnerTemplate.setOrigin(spawnerTemplate.getWidth() / 2, spawnerTemplate.getHeight() / 2);
        spawnerTemplate.setCollider(new Collider(new Circle(new Vector2(0, 0), spawnerTemplate.getHeight() / 2)));
    }

    @Override
    public Spawner create(Object... args) {
        newSpawner = spawnerTemplate.clone();
        newSpawner.setWeapon(weaponFactory.create());
        newSpawner.setSpawnTimer(new Timer(rand.nextFloat(5, 7), rand.nextFloat(0, 3)));
        return newSpawner;
    }
}
