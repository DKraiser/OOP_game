package sk.stuba.fiit.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.entities.Entity;
import sk.stuba.fiit.interfaces.attack.MeleeAttackingStrategy;

/**
 * Represents a projectile in the game, which extends the {@link Entity} class.
 * A projectile is an entity that moves in a specific direction and deals damage upon collision.
 * It can be cloned and can be customized with various attacking strategies and movement speeds.
 */
public abstract class Projectile extends Entity implements Cloneable {
    private Vector2 direction;
    private float speed;
    private MeleeAttackingStrategy attacker;
    private int damage;

    /**
     * Gets the direction of the projectile's movement.
     *
     * @return the direction vector
     */
    public Vector2 getDirection() {
        return direction;
    }

    /**
     * Sets the direction of the projectile's movement.
     *
     * @param direction the new direction vector
     */
    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }

    /**
     * Gets the speed of the projectile's movement.
     *
     * @return the speed of the projectile
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * Sets the speed of the projectile's movement.
     *
     * @param speed the new speed of the projectile
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * Gets the damage dealt by the projectile upon collision.
     *
     * @return the damage value
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Sets the damage value of the projectile.
     *
     * @param damage the new damage value
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Gets the attacking strategy used by the projectile.
     *
     * @return the {@link MeleeAttackingStrategy} associated with the projectile
     */
    public MeleeAttackingStrategy getAttacker() {
        return attacker;
    }

    /**
     * Sets the attacking strategy used by the projectile.
     *
     * @param attacker the new {@link MeleeAttackingStrategy} for the projectile
     */
    public void setAttacker(MeleeAttackingStrategy attacker) {
        this.attacker = attacker;
    }

    /**
     * Moves the projectile based on its direction and speed over time.
     * This method updates the position of the projectile and its collider.
     *
     * @param deltaTime the elapsed time in seconds
     */
    public void move(float deltaTime) {
        translate(new Vector2(getDirection()).scl(speed * deltaTime));
        getCollider().move(new Vector2(getDirection()).scl(speed * deltaTime));
    }

    /**
     * Handles what happens when the projectile collides with another entity.
     * This method must be implemented by subclasses.
     *
     * @param collisionEntity the entity that the projectile collides with
     */
    @Override
    public abstract void onCollision(Entity collisionEntity);

    /**
     * Creates a clone of the projectile.
     * This method must be implemented by subclasses.
     *
     * @return a new {@link Projectile} object that is a clone of this one
     */
    @Override
    public abstract Projectile clone();

    /**
     * Constructor for the Projectile class.
     * Initializes the projectile with the specified properties.
     *
     * @param name the name of the projectile
     * @param description the description of the projectile
     * @param texture the texture for the projectile's visual representation
     * @param health the initial health of the projectile
     * @param maxHealth the maximum health of the projectile
     * @param direction the initial direction of the projectile
     * @param speed the speed at which the projectile moves
     * @param damage the damage the projectile will deal upon collision
     */
    public Projectile(String name, String description, Texture texture, int health, int maxHealth, Vector2 direction, float speed, int damage) {
        super(name, description, texture, health, maxHealth);
        attacker = new sk.stuba.fiit.strategies.attacking.MeleeAttackingStrategy();
        this.damage = damage;
        this.direction = direction;
        this.speed = speed;
    }
}
