package sk.stuba.fiit.factories.weaponfactories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.Collider;
import sk.stuba.fiit.MyGame;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.factories.projectilefactories.PlayerProjectileFactory;
import sk.stuba.fiit.projectiles.PlayerProjectile;

/**
 * Factory class for creating a basic weapon for the player, represented by a basic projectile.
 * This class extends {@link WeaponFactory} and provides customization for projectile template
 * and weapon creation.
 */
public class BasicPlayerWeaponFactory extends WeaponFactory {

    /**
     * Creates an instance of a basic player weapon using the provided arguments.
     * This method constructs a weapon with a basic projectile.
     *
     * @param args optional arguments used for customization (not used in this factory)
     * @return the newly created {@link Weapon} instance
     */
    @Override
    public Weapon create(Object... args) {
        projectileFactory = new PlayerProjectileFactory(projectileTemplate, this.radiusOfAttacker, this.positionOfAttacker);

        weapon = new Weapon("Player Gun", "Basic Player Gun", null, projectileFactory);
        return weapon;
    }

    /**
     * Adjusts the projectile template to define the characteristics of the basic projectile,
     * including its texture, size, speed, damage, and collision properties.
     */
    @Override
    public void adjustProjectileTemplate() {
        projectileTemplate = new PlayerProjectile("Basic projectile from player's weapon",
            "Basic projectile from player's weapon",
            MyGame.TESTMODE ? null : new Texture("basic_bullet.png"),
            1, 1, null, 10f, 1);
        projectileTemplate.setSize(0.5f, 0.5f);
        projectileTemplate.setOrigin(projectileTemplate.getWidth() / 2, projectileTemplate.getHeight() / 2);

        projectileTemplate.setCollider(new Collider(new Circle(new Vector2(projectileTemplate.getPosition())
            .add(new Vector2(projectileTemplate.getWidth(), projectileTemplate.getHeight()).scl(0.5f)),
            projectileTemplate.getHeight() / 4)));
    }

    /**
     * Constructor to initialize the basic player weapon factory with a specific radius
     * and position of the attacker.
     *
     * @param radiusOfAttacker the radius of the attacker (weapon's range or area of effect)
     * @param positionOfAttacker the position of the attacker (weapon's firing point)
     */
    public BasicPlayerWeaponFactory(float radiusOfAttacker, Vector2 positionOfAttacker) {
        super(radiusOfAttacker, positionOfAttacker);
    }

    /**
     * Default constructor that initializes the factory with default values
     * for the radius and position of the attacker.
     */
    public BasicPlayerWeaponFactory() {
        super();
    }
}
