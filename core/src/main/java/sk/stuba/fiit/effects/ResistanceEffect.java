package sk.stuba.fiit.effects;

import sk.stuba.fiit.entities.Entity;
import sk.stuba.fiit.entities.Player;
import sk.stuba.fiit.interfaces.TakingDamageStrategy;
import sk.stuba.fiit.strategies.takingDamage.ResistanceTakingDamageStrategy;

/**
 * Represents a resistance effect that provides the player with a chance to block incoming damage.
 * The block chance is determined by the effect's level (10% per level).
 * This effect replaces the player's current {@link TakingDamageStrategy} with a {@link ResistanceTakingDamageStrategy}.
 * When removed, it restores the original strategy.
 */
public class ResistanceEffect extends Effect {
    private final StringBuilder nameBuilder;
    private final StringBuilder descriptionBuilder;

    /**
     * Constructs a new ResistanceEffect.
     *
     * @param level          the level of resistance, each level gives +10% chance to block damage
     * @param isFinite       whether the effect has a limited duration
     * @param duration       the total duration of the effect
     * @param remainingTime  the initial remaining time before expiration
     * @param target         the entity to which the effect is applied
     */
    public ResistanceEffect(int level, boolean isFinite, float duration, float remainingTime, Entity target) {
        super(null, null, level, isFinite, duration, remainingTime, target);

        nameBuilder = new StringBuilder();
        nameBuilder.append("Resistance ");
        nameBuilder.append("I".repeat(level));
        setName(nameBuilder.toString());

        descriptionBuilder = new StringBuilder();
        descriptionBuilder.append("You have ");
        descriptionBuilder.append(level * 10);
        descriptionBuilder.append("% chance to block the damage");
        setDescription(descriptionBuilder.toString());
    }

    /**
     * Creates a copy of this ResistanceEffect with the same level, duration, and time left.
     * Note: the cloned effect will have no target set.
     *
     * @return a cloned instance of this effect
     */
    @Override
    public Effect clone() {
        return new ResistanceEffect(getLevel(), isFinite(), getDuration(), getRemainingTime(), null);
    }

    /**
     * Applies the resistance effect to the player by modifying their damage-taking strategy.
     * Stores the current strategy to allow restoration later.
     */
    @Override
    public void applyEffect() {
        setInitialValue(((Player) getTarget()).getTakingDamage());
        ((Player) getTarget()).setTakingDamage(
            new ResistanceTakingDamageStrategy((TakingDamageStrategy) getInitialValue(), getLevel() * 10)
        );
    }

    /**
     * Removes the resistance effect and restores the player's original damage-taking strategy.
     */
    @Override
    public void removeEffect() {
        ((Player) getTarget()).setTakingDamage((TakingDamageStrategy) getInitialValue());
    }
}
