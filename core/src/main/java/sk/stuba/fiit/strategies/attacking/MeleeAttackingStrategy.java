package sk.stuba.fiit.strategies.attacking;

import sk.stuba.fiit.interfaces.Damageable;

/**
 * A strategy for performing melee attacks.
 * This strategy deals damage to a {@link Damageable} target when an attack is made.
 */
public class MeleeAttackingStrategy implements sk.stuba.fiit.interfaces.attack.MeleeAttackingStrategy {

    /**
     * Executes a melee attack on the specified target.
     * The target takes the specified amount of damage.
     *
     * @param target the target to receive the damage
     * @param damage the amount of damage to deal to the target
     */
    @Override
    public void attack(Damageable target, int damage) {
        target.takeDamage(damage);
    }
}
