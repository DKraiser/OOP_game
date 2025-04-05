package sk.stuba.fiit.entities;

import com.badlogic.gdx.graphics.Texture;
import sk.stuba.fiit.GameObject;
import sk.stuba.fiit.exceptions.InvalidParameterInitializationException;
import sk.stuba.fiit.interfaces.Damageable;

public abstract class Entity extends GameObject implements Damageable {
    private int health;
    private int maxHealth;

    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }

    public int getMaxHealth() { return maxHealth; }
    public void setMaxHealth(int maxHealth) { this.maxHealth = maxHealth; }

    public Entity(String name, String description, Texture texture, int health, int maxHealth) {
        super(name, description, texture);
        this.health = health;
        this.maxHealth = maxHealth;

        if (health > maxHealth || health < 0 || maxHealth < 0 ) {
            throw new InvalidParameterInitializationException();
        }
    }
}
