package sk.stuba.fiit.effects;

import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.entities.Entity;
import sk.stuba.fiit.entities.Player;
import sk.stuba.fiit.interfaces.Damageable;
import sk.stuba.fiit.interfaces.attack.RangedAttackingStrategy;
import sk.stuba.fiit.projectiles.Projectile;

import java.util.Collection;

/**
 * ParalyseEffect disables the player's ability to attack with weapons.
 * It temporarily overrides the player's {@link RangedAttackingStrategy} with a no-op strategy.
 */
public class ParalyseEffect extends Effect {
    private final String name;
    private final String description;

    /**
     * Constructs a ParalyseEffect with full control over its parameters.
     *
     * @param isFinite      whether the effect expires after a duration
     * @param duration      how long the effect lasts in total
     * @param remainingTime how much time is left when the effect is applied
     * @param target        the target entity affected by the effect
     */
    public ParalyseEffect(boolean isFinite, float duration, float remainingTime, Entity target) {
        super(null, null, 1, isFinite, duration, remainingTime, target);

        name = "Paralyse";
        description = "Your weapons are broken and cannot shoot";

        setName(name);
        setDescription(description);
    }

    /**
     * Constructs a ParalyseEffect assuming remaining time is equal to full duration.
     *
     * @param isFinite whether the effect expires after a duration
     * @param duration how long the effect lasts
     * @param target   the target entity affected
     */
    public ParalyseEffect(boolean isFinite, float duration, Entity target) {
        super(null, null, 1, isFinite, duration, target);

        name = "Paralyse";
        description = "Your weapons are broken and cannot shoot";

        setName(name);
        setDescription(description);
    }

    /**
     * Creates a clone of this effect with identical configuration, but without a target.
     *
     * @return a new ParalyseEffect instance
     */
    @Override
    public Effect clone() {
        return new ParalyseEffect(isFinite(), getDuration(), getRemainingTime(), null);
    }

    /**
     * Applies the paralysis effect by overriding the player's attacking strategy
     * with a dummy strategy that performs no attack.
     */
    @Override
    public void applyEffect() {
        RangedAttackingStrategy noAttackStrategy = new RangedAttackingStrategy() {
            @Override
            public void attack(Vector2 direction, Collection<Projectile> projectileEnvironment, Weapon weapon) { }

            @Override
            public void attack(Damageable target, Collection<Projectile> projectileEnvironment, Weapon weapon) { }
        };

        if (getTarget() instanceof Player) {
            setInitialValue(((Player) getTarget()).getAttackStrategy());
            ((Player) getTarget()).setAttackStrategy(noAttackStrategy);
        }
    }

    /**
     * Restores the player's original attacking strategy after the effect expires.
     */
    @Override
    public void removeEffect() {
        if (getTarget() instanceof Player) {
            ((Player) getTarget()).setAttackStrategy((RangedAttackingStrategy) getInitialValue());
        }
    }
}
