package sk.stuba.fiit.effects;

import sk.stuba.fiit.entities.Entity;
import sk.stuba.fiit.entities.Player;

/**
 * Represents a regeneration effect that increases the player's healing speed.
 * The regeneration rate is increased by reducing the healing timer period by a fixed amount per level.
 */
public class RegenerationEffect extends Effect {
    private StringBuilder nameBuilder;
    private StringBuilder descriptionBuilder;

    /**
     * Constructs a new RegenerationEffect.
     *
     * @param level          the effect level, each level speeds up regeneration by reducing the timer
     * @param isFinite       whether the effect has a limited duration
     * @param duration       the total duration of the effect
     * @param remainingTime  the initial remaining time before expiration
     * @param target         the entity to which the effect is applied
     */
    public RegenerationEffect(int level, boolean isFinite, float duration, float remainingTime, Entity target) {
        super(null, null, level, isFinite, duration, remainingTime, target);

        nameBuilder = new StringBuilder();
        nameBuilder.append("Regeneration ");
        nameBuilder.append("I".repeat(level));
        setName(nameBuilder.toString());

        descriptionBuilder = new StringBuilder();
        descriptionBuilder.append("Your hp are regenerated ");
        descriptionBuilder.append(level * 10);
        descriptionBuilder.append("% faster");
        setDescription(descriptionBuilder.toString());
    }

    /**
     * Creates a copy of this RegenerationEffect with the same level, duration, and time left.
     * Note: the cloned effect will have no target set.
     *
     * @return a cloned instance of this effect
     */
    @Override
    public Effect clone() {
        return new RegenerationEffect(getLevel(), isFinite(), getDuration(), getRemainingTime(), null);
    }

    /**
     * Applies the regeneration effect to the player by modifying their healing timer period.
     * Stores the original period so it can be restored later.
     */
    @Override
    public void applyEffect() {
        setInitialValue(((Player) getTarget()).getHealingTimer().getPeriod());

        if ((float) getInitialValue() > ((Player) getTarget()).getHealingTimer().getCurrent()) {
            ((Player) getTarget()).getHealingTimer().setElapsed();
        }

        ((Player) getTarget()).getHealingTimer().setPeriod((float) getInitialValue() - getLevel());
    }

    /**
     * Removes the regeneration effect and restores the original healing timer period.
     */
    @Override
    public void removeEffect() {
        ((Player) getTarget()).getHealingTimer().setPeriod((float) getInitialValue());
    }
}
