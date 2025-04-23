package sk.stuba.fiit.strategies.attacking;

import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.interfaces.Damageable;
import sk.stuba.fiit.interfaces.attack.RangedAttackingStrategy;
import sk.stuba.fiit.projectiles.Projectile;

import java.util.Collection;

/**
 * A strategy for performing ranged attacks in a specific direction.
 * This strategy uses the {@link Weapon} to shoot projectiles in the given direction.
 * It implements the {@link RangedAttackingStrategy} interface.
 */
public class DirectionRangedAttackingStrategy implements RangedAttackingStrategy {

    /**
     * Executes an attack by shooting projectiles in the specified direction.
     *
     * @param direction the direction in which the projectiles should be shot
     * @param projectileEnvironment the collection of projectiles currently in the game world
     * @param weapon the weapon being used to perform the attack
     */
    @Override
    public void attack(Vector2 direction, Collection<Projectile> projectileEnvironment, Weapon weapon) {
        weapon.shoot(direction, projectileEnvironment);
    }

    /**
     * This method is not implemented in this strategy and does nothing.
     *
     * @param target the target being attacked (not used in this strategy)
     * @param projectileEnvironment the collection of projectiles (not used in this strategy)
     * @param weapon the weapon being used (not used in this strategy)
     */
    @Override
    public void attack(Damageable target, Collection<Projectile> projectileEnvironment, Weapon weapon) { }
}
