package sk.stuba.fiit.factories.spawnerfactories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.Collider;
import sk.stuba.fiit.MyGame;
import sk.stuba.fiit.Timer;
import sk.stuba.fiit.entities.Spawner;
import sk.stuba.fiit.factories.weaponfactories.MissileEnemyWeaponFactory;

/**
 * Factory class for creating a UFO spawner. The UFO spawner spawns UFO enemies with missile weapons.
 * This class sets up the necessary properties such as the spawner's appearance, size, collider,
 * and spawn timer intervals.
 */
public class UfoSpawnerFactory extends SpawnerFactory {

    /**
     * Constructs a new UfoSpawnerFactory.
     * This constructor sets the weapon factory to a MissileEnemyWeaponFactory
     * and configures the UFO spawner template with its appearance, size, collider, etc.
     */
    public UfoSpawnerFactory() {
        weaponFactory = new MissileEnemyWeaponFactory();
        spawnerTemplate = new Spawner("Ufo Spawner", "", MyGame.TESTMODE ? null : new Texture("ufo.png"), 10, 10, null, 15, null);
        spawnerTemplate.setSize(.8f, .8f);
        spawnerTemplate.setOrigin(spawnerTemplate.getWidth() / 2, spawnerTemplate.getHeight() / 2);
        spawnerTemplate.setCollider(new Collider(new Circle(new Vector2(0, 0), spawnerTemplate.getHeight() / 2)));
    }

    /**
     * Creates a new UFO spawner by cloning the spawner template, assigning it a weapon,
     * and setting up the spawn timer with random intervals.
     *
     * @param args optional arguments for further customization (not used in this case)
     * @return the newly created {@link Spawner} instance for the UFO
     */
    @Override
    public Spawner create(Object... args) {
        newSpawner = spawnerTemplate.clone();
        newSpawner.setWeapon(weaponFactory.create());
        newSpawner.setSpawnTimer(new Timer(rand.nextFloat(5, 7), rand.nextFloat(0, 3)));
        newSpawner.setCollider(new Collider(new Circle(new Vector2(0, 0), spawnerTemplate.getHeight() / 2)));

        return newSpawner;
    }
}
