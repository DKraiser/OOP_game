package sk.stuba.fiit.strategies.takingDamage;

import sk.stuba.fiit.entities.Entity;
import sk.stuba.fiit.interfaces.Damageable;
import sk.stuba.fiit.interfaces.TakingDamageStrategy;

import static java.lang.Math.max;

/**
 * A strategy for handling damage when an entity takes damage.
 * This strategy reduces the health of the entity by the given damage amount,
 * ensuring that the health never goes below zero.
 */
public class StandartTakingDamageStrategy implements TakingDamageStrategy {

    /**
     * Reduces the health of the given damageable entity by the specified amount of damage.
     * The health is clamped to a minimum of zero, ensuring that it cannot go negative.
     *
     * @param damage the amount of damage to inflict
     * @param damageable the entity that takes damage
     */
    @Override
    public void takeDamage(int damage, Damageable damageable) {
        ((Entity) damageable).setHealth(max(((Entity) damageable).getHealth() - damage, 0));
    }
}
