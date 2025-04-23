package sk.stuba.fiit.effects;

import sk.stuba.fiit.entities.Entity;

/**
 * ExtraHealthEffect temporarily increases the maximum health of an entity.
 * Once removed, the entity's health is restored to its original value.
 */
public class ExtraHealthEffect extends Effect {
    private final StringBuilder nameBuilder;
    private final StringBuilder descriptionBuilder;

    /**
     * Constructs a new ExtraHealthEffect with specified parameters.
     *
     * @param level         the effect level; each level adds one extra health point
     * @param isFinite      whether the effect has a limited duration
     * @param duration      the total duration of the effect
     * @param remainingTime the remaining time until the effect expires
     * @param target        the entity that receives the effect
     */
    public ExtraHealthEffect(int level, boolean isFinite, float duration, float remainingTime, Entity target) {
        super(null, null, level, isFinite, duration, remainingTime, target);

        nameBuilder = new StringBuilder();
        nameBuilder.append("Extra health ");
        nameBuilder.append("I".repeat(level));
        setName(nameBuilder.toString());

        descriptionBuilder = new StringBuilder();
        descriptionBuilder.append("Now you have ");
        descriptionBuilder.append(level);
        descriptionBuilder.append(" extra health points");
        setDescription(descriptionBuilder.toString());
    }

    /**
     * Creates a copy of the current ExtraHealthEffect with the same configuration but no target.
     *
     * @return a cloned ExtraHealthEffect
     */
    @Override
    public Effect clone() {
        return new ExtraHealthEffect(getLevel(), isFinite(), getDuration(), getRemainingTime(), null);
    }

    /**
     * Applies the extra health effect by increasing the target's maximum health.
     */
    @Override
    public void applyEffect() {
        setInitialValue(getTarget().getMaxHealth());
        getTarget().setMaxHealth((int) getInitialValue() + getLevel());
    }

    /**
     * Removes the effect, restoring the target's maximum health to its original value.
     */
    @Override
    public void removeEffect() {
        getTarget().setMaxHealth((int) getInitialValue());
    }
}
