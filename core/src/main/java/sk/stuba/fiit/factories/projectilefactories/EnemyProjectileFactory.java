package sk.stuba.fiit.factories.projectilefactories;

import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.projectiles.Projectile;

/**
 * Factory class for creating enemy-specific projectiles. The EnemyProjectileFactory
 * extends the ProjectileFactory and is responsible for creating projectiles with properties
 * specific to enemies, based on the projectile template and the attacker's position and radius.
 */
public class EnemyProjectileFactory extends ProjectileFactory {

    /**
     * Creates a new enemy projectile by invoking the parent class's `create` method.
     * The projectile is customized with the direction vector and other properties defined
     * in the parent class.
     *
     * @param direction The direction in which the enemy projectile will move (as a Vector2).
     * @return A new enemy projectile instance with the specified properties.
     */
    @Override
    public Projectile create(Object... direction) {
        super.create(direction);
        return newProjectile;
    }

    /**
     * Constructs a new EnemyProjectileFactory with the given projectile template, attacker's radius,
     * and position.
     *
     * @param projectileTemplate The template projectile to use for creating new enemy projectiles.
     * @param radiusOfAttacker The radius of the enemy that fires the projectile.
     * @param positionOfAttacker The position of the enemy that fires the projectile.
     */
    public EnemyProjectileFactory(Projectile projectileTemplate, float radiusOfAttacker, Vector2 positionOfAttacker) {
        super(projectileTemplate, radiusOfAttacker, positionOfAttacker);
    }
}
