package sk.stuba.fiit.factories.weaponfactories;

import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.factories.projectilefactories.ProjectileFactory;
import sk.stuba.fiit.interfaces.Factory;
import sk.stuba.fiit.projectiles.Projectile;

/**
 * Abstract factory class for creating instances of {@link Weapon}.
 * This class provides common functionality for weapon factories, including the setup of projectile templates,
 * attacker radius, and position, allowing for customization of different weapon types.
 */
public abstract class WeaponFactory implements Factory<Weapon> {
    protected Projectile projectileTemplate;
    protected ProjectileFactory projectileFactory;
    protected Weapon weapon;
    protected float radiusOfAttacker;
    protected Vector2 positionOfAttacker;

    /**
     * Sets the radius of the attacker, which is typically used for determining
     * the range or area of effect of the weapon.
     *
     * @param radiusOfAttacker the radius to be set for the attacker
     */
    public void setRadiusOfAttacker(float radiusOfAttacker) {
        this.radiusOfAttacker = radiusOfAttacker;
    }

    /**
     * Sets the position of the attacker, which is the location where the weapon
     * or projectile will be launched from.
     *
     * @param positionOfAttacker the position to be set for the attacker
     */
    public void setPositionOfAttacker(Vector2 positionOfAttacker) {
        this.positionOfAttacker = positionOfAttacker;
    }

    /**
     * Gets the radius of the attacker.
     *
     * @return the radius of the attacker
     */
    public float getRadiusOfAttacker() {
        return radiusOfAttacker;
    }

    /**
     * Gets the position of the attacker.
     *
     * @return the position of the attacker
     */
    public Vector2 getPositionOfAttacker() {
        return positionOfAttacker;
    }

    /**
     * Abstract method to adjust the projectile template, which is implemented by
     * subclasses to define how projectiles should be created or customized.
     */
    public abstract void adjustProjectileTemplate();

    /**
     * Constructor to initialize the weapon factory with specific radius and position of the attacker.
     *
     * @param radiusOfAttacker the radius of the attacker
     * @param positionOfAttacker the position of the attacker
     */
    public WeaponFactory(float radiusOfAttacker, Vector2 positionOfAttacker) {
        this.radiusOfAttacker = radiusOfAttacker;
        this.positionOfAttacker = positionOfAttacker;
        adjustProjectileTemplate();
    }

    /**
     * Default constructor for initializing the weapon factory with default values for radius and position.
     */
    public WeaponFactory() {
        this.radiusOfAttacker = 0;
        this.positionOfAttacker = null;
        adjustProjectileTemplate();
    }
}
