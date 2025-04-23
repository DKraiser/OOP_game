package sk.stuba.fiit.factories.projectilefactories;

import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.projectiles.Projectile;

/**
 * Factory class for creating player-specific projectiles. The PlayerProjectileFactory
 * extends the ProjectileFactory and is responsible for creating projectiles with properties
 * specific to the player, based on the projectile template and the attacker's position and radius.
 */
public class PlayerProjectileFactory extends ProjectileFactory {

    /**
     * Creates a new player projectile by invoking the parent class's `create` method.
     * The projectile is customized with the direction vector and other properties defined
     * in the parent class.
     *
     * @param direction The direction in which the player projectile will move (as a Vector2).
     * @return A new player projectile instance with the specified properties.
     */
    @Override
    public Projectile create(Object... direction) {
        super.create(direction);
        return newProjectile;
    }

    /**
     * Constructs a new PlayerProjectileFactory with the given projectile template, attacker's radius,
     * and position.
     *
     * @param projectileTemplate The template projectile to use for creating new player projectiles.
     * @param radiusOfAttacker The radius of the player that fires the projectile.
     * @param positionOfAttacker The position of the player that fires the projectile.
     */
    public PlayerProjectileFactory(Projectile projectileTemplate, float radiusOfAttacker, Vector2 positionOfAttacker) {
        super(projectileTemplate, radiusOfAttacker, positionOfAttacker);
    }
}
