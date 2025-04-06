package sk.stuba.fiit.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.Collider;
import sk.stuba.fiit.GameObject;
import sk.stuba.fiit.exceptions.InvalidParameterInitializationException;
import sk.stuba.fiit.interfaces.Damageable;

public abstract class Entity extends GameObject implements Damageable {
    private int health;
    private int maxHealth;
    private Collider collider;

    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }

    public int getMaxHealth() { return maxHealth; }
    public void setMaxHealth(int maxHealth) { this.maxHealth = maxHealth; }

    public Collider getCollider() {
        return collider;
    }
    public void setCollider(Collider collider) {
        this.collider = collider;
    }

    public Entity(String name, String description, Texture texture, int health, int maxHealth) {
        super(name, description, texture);
        this.health = health;
        this.maxHealth = maxHealth;

        if (health > maxHealth || health < 0 || maxHealth < 0 ) {
            throw new InvalidParameterInitializationException();
        }
    }
}
