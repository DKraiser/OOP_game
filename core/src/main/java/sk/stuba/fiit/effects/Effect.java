package sk.stuba.fiit.effects;

import sk.stuba.fiit.entities.Entity;

public class Effect implements Cloneable{
    private String name;
    private String description;
    private int level;

    private boolean isFinite;
    private float remainingTime;
    private float duration;

    private Entity target;

    private Runnable affect;
    private Runnable disaffect;

    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getLevel() { return level; }
    public boolean isFinite() { return isFinite; }
    public float getRemainingTime() { return remainingTime; }
    public float getDuration() { return duration; }
    public Entity getTarget() { return target; }
    public Runnable getAffect() { return affect; }
    public Runnable getDisaffect() { return disaffect; }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setLevel(int level) { this.level = level; }
    public void setFinite(boolean isFinite) { this.isFinite = isFinite; }
    public void setRemainingTime(float remainingTime) { this.remainingTime = remainingTime; }
    public void setDuration(float duration) { this.duration = duration; }
    public void setTarget(Entity target) { this.target = target; }
    public void setAffect(Runnable affect) { this.affect = affect; }
    public void setDisaffect(Runnable disaffect) { this.disaffect = disaffect; }

    public void tickEffect(float deltaTime) { remainingTime -= deltaTime; }

    public Effect clone() {
        return new Effect(getName(), getDescription(), getLevel(), isFinite(), getDuration(),
            getRemainingTime(), getTarget(), getAffect(), getDisaffect());
    }

    public Effect(String name, String description, int level, boolean isFinite, float duration,
                  Entity target, Runnable affect, Runnable disaffect) {
        this.name = name;
        this.description = description;
        this.level = level;
        this.isFinite = isFinite;
        this.duration = duration;
        this.remainingTime = duration;
        this.target = target;
        this.affect = affect;
        this.disaffect = disaffect;
    }

    public Effect(String name, String description, int level, boolean isFinite, float duration,
                  float remainingTime, Entity target, Runnable affect, Runnable disaffect) {
        this.name = name;
        this.description = description;
        this.level = level;
        this.isFinite = isFinite;
        this.duration = duration;
        this.remainingTime = remainingTime;
        this.target = target;
        this.affect = affect;
        this.disaffect = disaffect;
    }
}
