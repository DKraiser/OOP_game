package sk.stuba.fiit.entities;

import com.badlogic.gdx.graphics.Texture;
import sk.stuba.fiit.MyGame;
import sk.stuba.fiit.Timer;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.events.EnemyKilledEvent;
import sk.stuba.fiit.interfaces.Damageable;
import sk.stuba.fiit.interfaces.Mortal;
import sk.stuba.fiit.interfaces.Tickable;
import sk.stuba.fiit.interfaces.attack.RangedAttackingStrategy;
import sk.stuba.fiit.projectiles.PlayerProjectile;
import sk.stuba.fiit.projectiles.Projectile;
import sk.stuba.fiit.strategies.attacking.TargetRangedAttackingStrategy;

import java.util.Collection;

/**
 * Represents a spawner entity in the game that can attack using ranged weapons, be damaged, and die.
 * Implements {@link Cloneable}, {@link Tickable}, {@link Damageable}, and {@link Mortal}.
 */
public class Spawner extends Entity implements Cloneable, Tickable, Damageable, Mortal {
    private RangedAttackingStrategy rangedAttacking;
    private Weapon weapon;
    private int price;
    private Timer spawnTimer;

    /**
     * Gets the weapon used by this spawner.
     *
     * @return the current weapon
     */
    public Weapon getWeapon() {
        return weapon;
    }

    /**
     * Sets the weapon used by this spawner.
     *
     * @param weapon the new weapon
     */
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    /**
     * Gets the price (reward value) of this spawner when destroyed.
     *
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Sets the price (reward value) of this spawner when destroyed.
     *
     * @param price the new price
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Gets the timer used to control spawn/attack intervals.
     *
     * @return the spawn timer
     */
    public Timer getSpawnTimer() {
        return spawnTimer;
    }

    /**
     * Sets the spawn timer.
     *
     * @param timer the new spawn timer
     */
    public void setSpawnTimer(Timer timer) {
        this.spawnTimer = timer;
    }

    /**
     * Attacks a target entity if the spawn timer has elapsed, using the current weapon and ranged strategy.
     *
     * @param target the entity to attack
     * @param projectileEnvironment the environment to which projectiles are added
     */
    public void attack(Entity target, Collection<Projectile> projectileEnvironment) {
        if (spawnTimer.isElapsed()) {
            rangedAttacking.attack(target, projectileEnvironment, weapon);
        }
    }

    /**
     * Kills this spawner. Triggers the {@link EnemyKilledEvent} and sets the entity as not alive.
     */
    @Override
    public void die() {
        EnemyKilledEvent.invokeEvent(getPrice());
        setAlive(false);
    }

    /**
     * Applies damage to the spawner. If health drops to 0, it dies.
     *
     * @param damage the amount of damage to apply
     */
    @Override
    public void takeDamage(int damage) {
        setHealth(Math.max(getHealth() - damage, 0));
        if (getHealth() == 0) {
            die();
        }
    }

    /**
     * Creates a copy of this spawner, optionally cloning its timer and visual properties.
     *
     * @return the cloned {@code Spawner}
     */
    @Override
    public Spawner clone() {
        Spawner clone = new Spawner(getName(), getDescription(), getTexture(), getHealth(), getMaxHealth(),
            null, getPrice(), spawnTimer != null ? spawnTimer.clone() : null);
        if (!MyGame.TESTMODE) {
            if (getSprite() != null) {
                clone.getSprite().set(getSprite());
            }
            clone.setSize(getWidth(), getHeight());
        }
        return clone;
    }

    /**
     * Handles collision with another entity. If the collider is a {@link PlayerProjectile}, takes damage from it.
     *
     * @param collisionEntity the entity collided with
     */
    @Override
    public void onCollision(Entity collisionEntity) {
        if (collisionEntity instanceof PlayerProjectile) {
            takeDamage(((PlayerProjectile) collisionEntity).getDamage());
        }
    }

    /**
     * Called on each game tick. Advances the internal spawn timer.
     *
     * @param deltaTime the time since last update in seconds
     */
    @Override
    public void tick(float deltaTime) {
        spawnTimer.tick(deltaTime);
    }

    /**
     * Constructs a new {@code Spawner} with the specified parameters.
     *
     * @param name the name of the spawner
     * @param description the description of the spawner
     * @param texture the texture of the spawner
     * @param health the current health
     * @param maxHealth the maximum health
     * @param weapon the weapon used by this spawner
     * @param price the price (reward) when this spawner is killed
     * @param timer the spawn/attack timer
     */
    public Spawner(String name, String description, Texture texture, int health, int maxHealth,
                   Weapon weapon, int price, Timer timer) {
        super(name, description, texture, health, maxHealth);
        this.weapon = weapon;
        this.rangedAttacking = new TargetRangedAttackingStrategy();
        this.price = price;
        this.spawnTimer = timer;
    }
}
