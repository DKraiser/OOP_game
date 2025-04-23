package sk.stuba.fiit.factories.weaponfactories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.Collider;
import sk.stuba.fiit.MyGame;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.factories.projectilefactories.EnemyProjectileFactory;
import sk.stuba.fiit.projectiles.EnemyProjectile;

/**
 * Factory class for creating a missile weapon for an enemy, represented by a missile projectile.
 * This class extends {@link WeaponFactory} and provides customization for projectile template
 * and weapon creation.
 */
public class MissileEnemyWeaponFactory extends WeaponFactory {

    /**
     * Creates an instance of a missile weapon using the provided arguments.
     * This method constructs a missile weapon with the specified missile projectile.
     *
     * @param args optional arguments used for customization (not used in this factory)
     * @return the newly created {@link Weapon} instance
     */
    @Override
    public Weapon create(Object... args) {
        projectileFactory = new EnemyProjectileFactory(projectileTemplate, this.radiusOfAttacker, this.positionOfAttacker);

        weapon = new Weapon("Missile Weapon", "", null, projectileFactory);
        return weapon;
    }

    /**
     * Adjusts the projectile template to define the characteristics of
     * the missile projectile, including its texture, size, speed,
     * damage, and collision properties.
     */
    @Override
    public void adjustProjectileTemplate() {
        // Create and configure the projectile template
        projectileTemplate = new EnemyProjectile("Fast missile from UFO's weapon",
            "Fast, highly damaging piece of... metal",
            MyGame.TESTMODE ? null : new Texture("missile.png"),
            5, 5, null, 5f, 2, 10);
        projectileTemplate.setSize(0.5f, 0.5f);
        projectileTemplate.setOrigin(projectileTemplate.getWidth() / 2, projectileTemplate.getHeight() / 2);

        projectileTemplate.setCollider(new Collider(new Circle(new Vector2(projectileTemplate.getPosition()),
            projectileTemplate.getHeight() / 4)));
    }

    /**
     * Constructor to initialize the missile weapon factory with a specific radius
     * and position of the attacker.
     *
     * @param radiusOfAttacker the radius of the attacker (weapon's range or area of effect)
     * @param positionOfAttacker the position of the attacker (weapon's firing point)
     */
    public MissileEnemyWeaponFactory(float radiusOfAttacker, Vector2 positionOfAttacker) {
        super(radiusOfAttacker, positionOfAttacker);
    }

    /**
     * Default constructor that initializes the factory with default values
     * for the radius and position of the attacker.
     */
    public MissileEnemyWeaponFactory() {
        super();
    }
}
