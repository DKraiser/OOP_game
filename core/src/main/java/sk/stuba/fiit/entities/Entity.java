package sk.stuba.fiit.entities;

import com.badlogic.gdx.graphics.Texture;
import sk.stuba.fiit.effects.Effect;
import sk.stuba.fiit.GameObject;
import sk.stuba.fiit.interfaces.Effectable;

import java.util.*;

public abstract class Entity extends GameObject {
    private int health;
    private int maxHealth;
    private List<Effect> effects;

    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }

    public int getMaxHealth() { return maxHealth; }
    public void setMaxHealth(int maxHealth) { this.maxHealth = maxHealth; }

    public List<Effect> getEffects() { return effects; }
    public Effect getEffect(Class effectClass) {
        for (Effect e : effects) {
            if (e.getClass() == effectClass) return e;
        }
        return null;
    }
    public void setEffects(List<Effect> effects) { this.effects = effects; }

    public Entity(String name, String description, Texture texture, int health, int maxHealth, List<Effect> effects) {
        super(name, description, texture);
        this.health = health;
        this.maxHealth = maxHealth;
        this.effects = effects;
    }
}
