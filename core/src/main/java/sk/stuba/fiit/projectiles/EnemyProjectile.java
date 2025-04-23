package sk.stuba.fiit.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.MyGame;
import sk.stuba.fiit.effects.Effect;
import sk.stuba.fiit.EffectHandler;
import sk.stuba.fiit.entities.Entity;
import sk.stuba.fiit.entities.Player;
import sk.stuba.fiit.events.EnemyKilledEvent;
import sk.stuba.fiit.interfaces.Damageable;
import sk.stuba.fiit.interfaces.Mortal;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an enemy projectile in the game. It extends the {@link Projectile} class
 * and implements {@link Damageable} and {@link Mortal} interfaces. The class handles
 * the behavior of enemy projectiles, including applying effects, dealing damage to targets,
 * and handling the death of the projectile.
 */
public class EnemyProjectile extends Projectile implements Damageable, Mortal {
    private EffectHandler effectHandler;
    private int price;

    /**
     * Gets the effect handler associated with the enemy projectile.
     *
     * @return the {@link EffectHandler} managing the projectile's effects
     */
    public EffectHandler getEffectHandler() {
        return effectHandler;
    }

    /**
     * Sets the effect handler for the enemy projectile.
     *
     * @param effectHandler the new {@link EffectHandler} to associate with the projectile
     */
    public void setEffectHandler(EffectHandler effectHandler) {
        this.effectHandler = effectHandler;
    }

    /**
     * Gets the price of the enemy projectile.
     *
     * @return the price of the projectile
     */
    public int getPrice() {
        return price;
    }

    /**
     * Sets the price of the enemy projectile.
     *
     * @param price the new price of the projectile
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Attacks a target with the given damage using the projectile's attacker.
     *
     * @param target the target to attack
     * @param damage the amount of damage to deal
     */
    public void attack(Damageable target, int damage) {
        getAttacker().attack(target, damage);
    }

    /**
     * Handles the death of the enemy projectile. It marks the projectile as not alive
     * and invokes an event for the enemy's death.
     */
    @Override
    public void die() {
        setAlive(false);
        EnemyKilledEvent.invokeEvent(getPrice());
    }

    /**
     * Takes damage and reduces the health of the enemy projectile. If health reaches zero,
     * the projectile dies.
     *
     * @param damage the amount of damage to take
     */
    @Override
    public void takeDamage(int damage) {
        setHealth(Math.max(getHealth() - damage, 0));
        if (getHealth() <= 0)
            die();
    }

    /**
     * Creates a clone of the enemy projectile, including its effects and properties.
     *
     * @return a cloned {@link EnemyProjectile} object
     */
    @Override
    public Projectile clone() {
        List<Effect> thisEffects = getEffectHandler().getEffects();
        for (Effect effect : thisEffects) {
            effect.removeEffect();
        }

        EnemyProjectile clone = new EnemyProjectile(this.getName(), this.getDescription(), this.getTexture(), this.getHealth(), this.getMaxHealth(), null, this.getSpeed(), this.getDamage(), this.getPrice());

        getEffectHandler().takeEffect(thisEffects);
        if (!MyGame.TESTMODE)
        {
            if (getSprite() != null) {
                clone.getSprite().set(this.getSprite());
            }
        }
        clone.setCollider(this.getCollider().clone());

        List<Effect> clonedEffects = new ArrayList<>();
        if (!this.getEffectHandler().getEffects().isEmpty()) {
            for (Effect effect : this.getEffectHandler().getEffects()) {
                clonedEffects.add(effect.clone());
                clonedEffects.getLast().setTarget(clone);
            }
            clone.getEffectHandler().takeEffect(clonedEffects);
        }
        return clone;
    }

    /**
     * Constructor for creating a new enemy projectile with the specified properties.
     *
     * @param name the name of the enemy projectile
     * @param description the description of the enemy projectile
     * @param texture the texture for the enemy projectile
     * @param health the initial health of the enemy projectile
     * @param maxHealth the maximum health of the enemy projectile
     * @param direction the direction in which the enemy projectile moves
     * @param speed the speed at which the enemy projectile moves
     * @param damage the amount of damage the enemy projectile deals
     * @param price the price of the enemy projectile
     */
    public EnemyProjectile(String name, String description, Texture texture, int health, int maxHealth, Vector2 direction, float speed, int damage, int price)
    {
        super(name, description, texture, health, maxHealth, direction, speed, damage);
        effectHandler = new EffectHandler();
        this.price = price;
    }

    /**
     * Handles the collision of the enemy projectile with another entity.
     * If the projectile collides with a {@link Player}, it will deal damage to the player
     * and then destroy the projectile.
     *
     * @param collisionEntity the entity with which the projectile collides
     */
    @Override
    public void onCollision(Entity collisionEntity) {
        if (collisionEntity instanceof Player) {
            attack(collisionEntity, this.getDamage());
            setAlive(false);
        }
    }
}
