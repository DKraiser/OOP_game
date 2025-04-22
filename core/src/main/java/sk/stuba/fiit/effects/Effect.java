package sk.stuba.fiit.effects;

import sk.stuba.fiit.entities.Entity;
import sk.stuba.fiit.interfaces.Tickable;

import java.io.Serializable;

public abstract class Effect implements Cloneable, Tickable, Serializable {
    private String name;
    private String description;
    private int level;

    private boolean isFinite;
    private float remainingTime;
    private float duration;

    private Entity target;
    private Object initialValue;

    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getLevel() { return level; }
    public boolean isFinite() { return isFinite; }
    public float getRemainingTime() { return remainingTime; }
    public float getDuration() { return duration; }
    public Entity getTarget() { return target; }
    public Object getInitialValue() { return initialValue; }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setLevel(int level) { this.level = level; }
    public void setFinite(boolean isFinite) { this.isFinite = isFinite; }
    public void setRemainingTime(float remainingTime) { this.remainingTime = remainingTime; }
    public void setDuration(float duration) { this.duration = duration; }
    public void setTarget(Entity target) { this.target = target; }
    protected void setInitialValue(Object initialValue) { this.initialValue = initialValue; }

    @Override
    public void tick(float deltaTime) {
        if (!isFinite) return;
        remainingTime -= deltaTime;
        if (remainingTime <= 0) {
            removeEffect();
        }
    }

    @Override
    public abstract Effect clone();

    public abstract void applyEffect();
    public abstract void removeEffect();

    public Effect(String name, String description, int level, boolean isFinite, float duration, float remainingTime, Entity target) {
        this.name = name;
        this.description = description;
        this.level = level;
        this.isFinite = isFinite;
        this.duration = duration;
        this.remainingTime = remainingTime;
        this.target = target;
    }

    public Effect(String name, String description, int level, boolean isFinite, float duration, Entity target) {
        this(name, description, level, isFinite, duration, duration, target);
    }
}
