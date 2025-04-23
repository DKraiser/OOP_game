package sk.stuba.fiit.strategies.attacking;

import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.entities.Entity;
import sk.stuba.fiit.interfaces.Damageable;
import sk.stuba.fiit.interfaces.attack.RangedAttackingStrategy;
import sk.stuba.fiit.projectiles.Projectile;

import java.util.Collection;

/**
 * A strategy for performing ranged attacks aimed at a specific target.
 * This strategy calculates the direction towards the target and uses a weapon to shoot a projectile in that direction.
 */
public class TargetRangedAttackingStrategy implements RangedAttackingStrategy {

    /**
     * Performs a ranged attack in the specified direction.
     * This method is not implemented in this class.
     *
     * @param direction the direction to attack
     * @param projectileEnvironment the collection of projectiles in the environment
     * @param weapon the weapon used to perform the attack
     */
    @Override
    public void attack(Vector2 direction, Collection<Projectile> projectileEnvironment, Weapon weapon) { }

    /**
     * Performs a ranged attack towards the given target.
     * This method calculates the direction towards the target and shoots a projectile from the weapon.
     *
     * @param target the target to attack
     * @param projectileEnvironment the collection of projectiles in the environment
     * @param weapon the weapon used to perform the attack
     */
    @Override
    public void attack(Damageable target, Collection<Projectile> projectileEnvironment, Weapon weapon) {
        Vector2 direction = new Vector2(((Entity) target).getPosition());
        direction.add(new Vector2(((Entity) target).getWidth(), ((Entity) target).getHeight()).scl(0.5f));
        direction.sub(weapon.getPosition());
        weapon.shoot(direction, projectileEnvironment);
    }
}
