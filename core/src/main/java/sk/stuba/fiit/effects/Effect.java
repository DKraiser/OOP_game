package sk.stuba.fiit.effects;

import sk.stuba.fiit.entities.Entity;
import sk.stuba.fiit.interfaces.Tickable;

import java.io.Serializable;

/**
 * Represents a status effect that can be applied to an {@link Entity}.
 * Effects can be finite (expire after a duration) or infinite (persist indefinitely).
 * This class is abstract and requires concrete subclasses to implement effect logic.
 *
 * Implements {@link Cloneable}, {@link Tickable}, and {@link Serializable}.
 */
public abstract class Effect implements Cloneable, Tickable, Serializable {
    private String name;
    private String description;
    private int level;

    private boolean isFinite;
    private float remainingTime;
    private float duration;

    private Entity target;
    private Object initialValue;

    /**
     * Gets the name of the effect.
     *
     * @return the effect name
     */
    public String getName() { return name; }

    /**
     * Gets the description of the effect.
     *
     * @return the effect description
     */
    public String getDescription() { return description; }

    /**
     * Gets the level of the effect, used for scaling or stacking logic.
     *
     * @return the level of the effect
     */
    public int getLevel() { return level; }

    /**
     * Indicates whether the effect has a finite duration.
     *
     * @return true if finite, false if infinite
     */
    public boolean isFinite() { return isFinite; }

    /**
     * Gets the remaining time before the effect expires.
     *
     * @return the remaining time in seconds
     */
    public float getRemainingTime() { return remainingTime; }

    /**
     * Gets the total duration of the effect.
     *
     * @return the duration in seconds
     */
    public float getDuration() { return duration; }

    /**
     * Gets the target entity the effect is applied to.
     *
     * @return the affected entity
     */
    public Entity getTarget() { return target; }

    /**
     * Gets the value stored when the effect was initially applied (e.g., original state before modification).
     *
     * @return the initial value
     */
    public Object getInitialValue() { return initialValue; }

    /**
     * Sets the name of the effect.
     *
     * @param name the effect name
     */
    public void setName(String name) { this.name = name; }

    /**
     * Sets the description of the effect.
     *
     * @param description the effect description
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * Sets the level of the effect.
     *
     * @param level the level
     */
    public void setLevel(int level) { this.level = level; }

    /**
     * Sets whether the effect is finite.
     *
     * @param isFinite true if finite, false if infinite
     */
    public void setFinite(boolean isFinite) { this.isFinite = isFinite; }

    /**
     * Sets the remaining time for the effect.
     *
     * @param remainingTime the time in seconds
     */
    public void setRemainingTime(float remainingTime) { this.remainingTime = remainingTime; }

    /**
     * Sets the total duration for the effect.
     *
     * @param duration the duration in seconds
     */
    public void setDuration(float duration) { this.duration = duration; }

    /**
     * Sets the entity to which the effect is applied.
     *
     * @param target the target entity
     */
    public void setTarget(Entity target) { this.target = target; }

    /**
     * Sets the initial value stored when the effect is applied.
     * Should be used to preserve the entity's original state if needed.
     *
     * @param initialValue the initial value
     */
    protected void setInitialValue(Object initialValue) { this.initialValue = initialValue; }

    /**
     * Called on each game tick. Reduces the remaining time if the effect is finite.
     * Automatically removes the effect when time runs out.
     *
     * @param deltaTime time passed since the last tick in seconds
     */
    @Override
    public void tick(float deltaTime) {
        if (!isFinite) return;
        remainingTime -= deltaTime;
        if (remainingTime <= 0) {
            removeEffect();
        }
    }

    /**
     * Creates a deep copy of this effect. Concrete subclasses must implement clone logic.
     *
     * @return a cloned instance of the effect
     */
    @Override
    public abstract Effect clone();

    /**
     * Applies the effect to the target entity. Must be implemented by subclasses.
     */
    public abstract void applyEffect();

    /**
     * Removes the effect from the target entity. Must be implemented by subclasses.
     */
    public abstract void removeEffect();

    /**
     * Constructs an effect with specified parameters.
     *
     * @param name the name of the effect
     * @param description the description of the effect
     * @param level the level of the effect
     * @param isFinite whether the effect has a limited duration
     * @param duration the total duration of the effect
     * @param remainingTime the starting time before the effect ends
     * @param target the entity to apply the effect to
     */
    public Effect(String name, String description, int level, boolean isFinite,
                  float duration, float remainingTime, Entity target) {
        this.name = name;
        this.description = description;
        this.level = level;
        this.isFinite = isFinite;
        this.duration = duration;
        this.remainingTime = remainingTime;
        this.target = target;
    }

    /**
     * Constructs an effect with full duration as remaining time.
     *
     * @param name the name of the effect
     * @param description the description of the effect
     * @param level the level of the effect
     * @param isFinite whether the effect has a limited duration
     * @param duration the total and remaining duration of the effect
     * @param target the entity to apply the effect to
     */
    public Effect(String name, String description, int level, boolean isFinite,
                  float duration, Entity target) {
        this(name, description, level, isFinite, duration, duration, target);
    }
}

