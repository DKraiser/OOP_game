package sk.stuba.fiit.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.MyGame;
import sk.stuba.fiit.effects.Effect;
import sk.stuba.fiit.entities.Entity;
import sk.stuba.fiit.entities.Spawner;

import java.io.Serializable;

/**
 * Represents a player projectile in the game. It extends the {@link Projectile} class
 * and implements {@link Cloneable} and {@link Serializable} interfaces. This class defines
 * behavior for player projectiles, including how they interact with other entities,
 * such as taking damage and being destroyed on collision.
 */
public class PlayerProjectile extends Projectile implements Cloneable, Serializable {

    /**
     * Creates a clone of the player projectile. The clone will inherit the properties
     * and effects of the original projectile, including its sprite and collider.
     *
     * @return a new cloned {@link PlayerProjectile} object
     */
    @Override
    public Projectile clone() {
        Projectile clone = new PlayerProjectile(this.getName(), this.getDescription(), this.getTexture(), this.getHealth(), this.getMaxHealth(), null, this.getSpeed(), this.getDamage());
        if (!MyGame.TESTMODE)
        {
            clone.getSprite().set(this.getSprite());
        }
        clone.setCollider(this.getCollider().clone());

        return clone;
    }

    /**
     * Constructor for creating a new player projectile with the specified properties.
     *
     * @param name the name of the player projectile
     * @param description the description of the player projectile
     * @param texture the texture for the player projectile
     * @param health the initial health of the player projectile
     * @param maxHealth the maximum health of the player projectile
     * @param direction the direction in which the player projectile moves
     * @param speed the speed at which the player projectile moves
     * @param damage the amount of damage the player projectile deals
     */
    public PlayerProjectile(String name, String description, Texture texture, int health, int maxHealth, Vector2 direction, float speed, int damage) {
        super(name, description, texture, health, maxHealth, direction, speed, damage);
    }

    /**
     * Method that handles taking damage. For player projectiles, this method does nothing
     * as they do not take damage.
     *
     * @param damage the amount of damage to take
     */
    @Override
    public void takeDamage(int damage) { }

    /**
     * Method that handles the death of the player projectile. For player projectiles,
     * this method does nothing as they do not die in the traditional sense.
     */
    @Override
    public void die() { }

    /**
     * Handles the collision of the player projectile with another entity.
     * If the projectile collides with an {@link EnemyProjectile} or a {@link Spawner},
     * it will deal damage to the entity and destroy itself.
     *
     * @param collisionEntity the entity with which the player projectile collides
     */
    @Override
    public void onCollision(Entity collisionEntity) {
        if (collisionEntity instanceof EnemyProjectile || collisionEntity instanceof Spawner) {
            getAttacker().attack(collisionEntity, getDamage());
            setAlive(false);
        }
    }
}
