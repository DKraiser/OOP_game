package sk.stuba.fiit.entities;

import com.badlogic.gdx.graphics.Texture;
import sk.stuba.fiit.Collider;
import sk.stuba.fiit.GameObject;
import sk.stuba.fiit.exceptions.InvalidParameterInitializationException;
import sk.stuba.fiit.interfaces.Damageable;
import sk.stuba.fiit.interfaces.Mortal;

/**
 * Abstract class representing an entity in the game world.
 * Entities can be damageable, have health, and may die. They also have a collider for collision detection.
 */
public abstract class Entity extends GameObject implements Damageable, Mortal {
    private int health;
    private int maxHealth;
    private boolean isAlive;
    private Collider collider;

    /**
     * Gets the current health of the entity.
     *
     * @return the current health of the entity.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the health of the entity. If the health is set to a value greater than the maximum health,
     * it will be set to the maximum health. If health is set to a negative value, it will throw an exception.
     *
     * @param health the new health value to set.
     * @throws IllegalArgumentException if the health is negative.
     */
    public void setHealth(int health) {
        if (health < 0) {
            throw new IllegalArgumentException("Health cannot be negative");
        }
        if (health > maxHealth) {
            health = maxHealth;
        }
        this.health = health;
    }

    /**
     * Gets the maximum health of the entity.
     *
     * @return the maximum health of the entity.
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Sets the maximum health of the entity. If the new maximum health is less than the current health,
     * the current health will be set to the new maximum health. If the maximum health is set to a value less than zero,
     * it will throw an exception.
     *
     * @param maxHealth the new maximum health value to set.
     * @throws IllegalArgumentException if the maximum health is less than zero.
     */
    public void setMaxHealth(int maxHealth) {
        if (maxHealth <= 0) {
            throw new IllegalArgumentException("Max health cannot be negative or zero");
        }
        if (maxHealth < health) {
            health = maxHealth;
        }
        this.maxHealth = maxHealth;
    }

    /**
     * Checks if the entity is alive.
     *
     * @return true if the entity is alive, false otherwise.
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Sets the alive status of the entity.
     *
     * @param alive the new alive status of the entity.
     */
    public void setAlive(boolean alive) {
        this.isAlive = alive;
    }

    /**
     * Gets the collider of the entity.
     *
     * @return the collider of the entity.
     */
    public Collider getCollider() {
        return collider;
    }

    /**
     * Sets the collider of the entity.
     *
     * @param collider the new collider to set for the entity.
     */
    public void setCollider(Collider collider) {
        this.collider = collider;
    }

    /**
     * Abstract method to handle the logic when the entity collides with another entity.
     * The concrete implementation of this method should define how the entity reacts to a collision.
     *
     * @param collisionEntity the entity that this entity has collided with.
     */
    public abstract void onCollision(Entity collisionEntity);

    /**
     * Constructs a new entity with the given parameters.
     *
     * @param name the name of the entity.
     * @param description a description of the entity.
     * @param texture the texture to represent the entity.
     * @param health the initial health of the entity.
     * @param maxHealth the maximum health of the entity.
     * @throws InvalidParameterInitializationException if the health or max health parameters are invalid.
     */
    public Entity(String name, String description, Texture texture, int health, int maxHealth) {
        super(name, description, texture);
        this.health = health;
        this.maxHealth = maxHealth;
        this.isAlive = true;

        if (health > maxHealth || health < 0 || maxHealth < 0 ) {
            throw new InvalidParameterInitializationException();
        }
    }
}
