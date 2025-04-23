package sk.stuba.fiit;

import sk.stuba.fiit.effects.Effect;
import sk.stuba.fiit.interfaces.Effectable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Manages and applies {@link Effect} objects to an {@link Effectable} entity.
 * This class handles the addition, removal, and application of effects, as well as updating them over time.
 */
public class EffectHandler implements Effectable, Cloneable, Serializable {
    private List<Effect> effects;

    /**
     * Gets the list of effects currently applied.
     *
     * @return a list of applied effects
     */
    public List<Effect> getEffects() {
        return effects;
    }

    /**
     * Gets a specific effect based on its class type.
     *
     * @param effectClass the class type of the effect
     * @return the effect of the specified class, or {@code null} if not found
     */
    public Effect getEffect(Class<? extends Effect> effectClass) {
        return effects.stream()
            .filter(e -> e.getClass() == effectClass)
            .findFirst()
            .orElse(null);
    }

    /**
     * Applies an effect to the entity. If the effect already exists, it is removed first before reapplying.
     *
     * @param effect the effect to be applied
     */
    @Override
    public void takeEffect(Effect effect) {
        Effect existing = getEffect(effect.getClass());
        if (existing != null) {
            existing.removeEffect();
            effects.remove(existing);
        }
        effects.add(effect);
        effect.applyEffect();
    }

    /**
     * Applies a collection of effects to the entity.
     *
     * @param effect a collection of effects to be applied
     */
    public void takeEffect(Collection<Effect> effect) {
        for (Effect e : effect) {
            takeEffect(e);
        }
    }

    /**
     * Updates the state of all effects based on the elapsed time.
     *
     * @param deltaTime the time elapsed since the last update
     */
    @Override
    public void updateEffects(float deltaTime) {
        for (Effect effect : getEffects()) {
            effect.tick(deltaTime);
        }
    }

    /**
     * Creates a deep copy of this {@link EffectHandler}, including all applied effects.
     *
     * @return a cloned {@link EffectHandler} with cloned effects
     */
    @Override
    public EffectHandler clone() {
        EffectHandler newEffectHandler = new EffectHandler();
        List<Effect> newEffects = new ArrayList<>();

        if (!effects.isEmpty()) {
            for (Effect effect : getEffects()) {
                newEffects.add(effect.clone());
            }
            newEffectHandler.takeEffect(newEffects);
        }

        return newEffectHandler;
    }

    /**
     * Constructs a new {@code EffectHandler} with an empty list of effects.
     */
    public EffectHandler() {
        effects = new ArrayList<Effect>();
    }
}
