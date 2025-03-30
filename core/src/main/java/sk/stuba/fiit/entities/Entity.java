package sk.stuba.fiit.entities;

import com.badlogic.gdx.graphics.Texture;
import sk.stuba.fiit.GameObject;

public abstract class Entity extends GameObject {
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
    }
}
