package sk.stuba.fiit.entities;

import com.badlogic.gdx.graphics.Texture;
import sk.stuba.fiit.Collider;
import sk.stuba.fiit.GameObject;
import sk.stuba.fiit.exceptions.InvalidParameterInitializationException;
import sk.stuba.fiit.interfaces.Damageable;
import sk.stuba.fiit.interfaces.Mortal;

public abstract class Entity extends GameObject implements Damageable, Mortal {
    private int health;
    private int maxHealth;
    private boolean isAlive;
    private Collider collider;

    public int getHealth() { return health; }
    public void setHealth(int health) {
        if (health < 0) {
            throw new IllegalArgumentException("Health cannot be negative");
        }
        if (health > maxHealth) {
            health = maxHealth;
        }
        this.health = health;
    }

    public int getMaxHealth() { return maxHealth; }
    public void setMaxHealth(int maxHealth) {
        if (maxHealth <= 0) {
            throw new IllegalArgumentException("Max health cannot be negative");
        }
        if (maxHealth < health) {
            health = maxHealth;
        }
        this.maxHealth = maxHealth;
    }

    public boolean isAlive() { return isAlive; }
    public void setAlive(boolean alive) {
        this.isAlive = alive;
    }

    public Collider getCollider() {
        return collider;
    }
    public void setCollider(Collider collider) {
        this.collider = collider;
    }

    public void onCollision(Entity collisionEntity) { }

    public Entity(String name, String description, Texture texture, int health, int maxHealth) {
        super(name, description, texture);
        this.health = health;
        this.maxHealth = maxHealth;
        this.isAlive = true;

        if (health > maxHealth || health < 0 || maxHealth < 0 ) {
            throw new InvalidParameterInitializationException();
        }
    }

    public Entity() {
        super();
    }
}
