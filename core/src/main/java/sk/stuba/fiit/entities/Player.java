package sk.stuba.fiit.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;
import sk.stuba.fiit.Collider;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.effects.Effect;
import sk.stuba.fiit.effects.ParalyseEffect;
import sk.stuba.fiit.events.PlayerIsParalysedEvent;
import sk.stuba.fiit.factories.weaponfactories.BasicPlayerWeaponFactory;
import sk.stuba.fiit.factories.weaponfactories.WeaponFactory;
import sk.stuba.fiit.interfaces.*;
import sk.stuba.fiit.projectiles.EnemyProjectile;
import sk.stuba.fiit.strategies.attacking.DirectionRangedAttackingStrategy;
import sk.stuba.fiit.EffectHandler;
import sk.stuba.fiit.interfaces.attack.RangedAttackingStrategy;
import sk.stuba.fiit.projectiles.Projectile;
import sk.stuba.fiit.Timer;
import sk.stuba.fiit.strategies.takingDamage.StandartTakingDamageStrategy;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

/**
 * Represents a player character in the game, with properties for health, balance, weapon, and effects.
 * The Player class allows for actions such as attacking, taking damage, healing, and handling status effects.
 * It also includes functionality for movement, collision detection, and ticking updates.
 */
public class Player extends Entity implements Damageable, Mortal, Tickable, Effectable, Serializable, Cloneable {

    private int balance;
    private Weapon weapon;
    private EffectHandler effectHandler;
    private RangedAttackingStrategy rangedAttacking;
    private TakingDamageStrategy takingDamage;
    private Timer healingTimer;
    private int healingPerInterval;
    private transient Logger logger;
    private transient Random random;

    /**
     * Gets the player's current weapon.
     * @return The player's weapon.
     */
    public Weapon getWeapon() {
        return weapon;
    }

    /**
     * Sets the player's weapon.
     * @param weapon The new weapon to equip.
     */
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    /**
     * Gets the player's effect handler.
     * @return The effect handler.
     */
    public EffectHandler getEffectHandler() {
        return effectHandler;
    }

    /**
     * Sets the player's effect handler.
     * @param effectHandler The effect handler to set.
     */
    public void setEffectHandler(EffectHandler effectHandler) {
        this.effectHandler = effectHandler;
    }

    /**
     * Gets the player's current balance.
     * @return The player's balance.
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Sets the player's balance.
     * @param balance The new balance to set.
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }

    /**
     * Gets the healing amount per interval.
     * @return The healing amount.
     */
    public int getHealingPerInterval() {
        return healingPerInterval;
    }

    /**
     * Sets the healing amount per interval.
     * @param healingPerInterval The new healing amount.
     */
    public void setHealingPerInterval(int healingPerInterval) {
        if (healingPerInterval <= 0) {
            throw new IllegalArgumentException("healingPerInterval must be greater than 0");
        }
        this.healingPerInterval = healingPerInterval;
    }

    /**
     * Gets the healing timer.
     * @return The healing timer.
     */
    public Timer getHealingTimer() {
        return healingTimer;
    }

    /**
     * Sets the healing timer.
     * @param timer The healing timer to set.
     */
    public void setHealingTimer(Timer timer) {
        this.healingTimer = timer;
    }

    /**
     * Gets the player's ranged attacking strategy.
     * @return The ranged attacking strategy.
     */
    public RangedAttackingStrategy getAttackStrategy() {
        return rangedAttacking;
    }

    /**
     * Sets the player's ranged attacking strategy.
     * @param rangedAttacking The new ranged attacking strategy.
     */
    public void setAttackStrategy(RangedAttackingStrategy rangedAttacking) {
        this.rangedAttacking = rangedAttacking;
    }

    /**
     * Gets the player's damage taking strategy.
     * @return The damage taking strategy.
     */
    public TakingDamageStrategy getTakingDamage() {
        return takingDamage;
    }

    /**
     * Sets the player's damage taking strategy.
     * @param takingDamage The new damage taking strategy.
     */
    public void setTakingDamage(TakingDamageStrategy takingDamage) {
        this.takingDamage = takingDamage;
    }

    /**
     * Sets the random number generator used in the player.
     * @param random The random number generator to set.
     */
    public void setRandom(Random random) {
        this.random = random;
    }

    /**
     * Applies an effect to the player.
     * @param effect The effect to apply.
     */
    @Override
    public void takeEffect(Effect effect) {
        effectHandler.takeEffect(effect);
    }

    /**
     * Updates the player's effects based on the time elapsed.
     * @param deltaTime The time that has passed since the last update.
     */
    @Override
    public void updateEffects(float deltaTime) {
        effectHandler.updateEffects(deltaTime);
    }

    /**
     * Executes an attack in a specified direction.
     * @param direction The direction in which to attack.
     * @param projectileEnvironment The environment where projectiles will be fired.
     */
    public void attack(Vector2 direction, List<Projectile> projectileEnvironment) {
        rangedAttacking.attack(direction, projectileEnvironment, weapon);
    }

    /**
     * Handles the player's death.
     * Marks the player as dead and logs the event.
     */
    @Override
    public void die() {
        logger.info("Player was killed");
        setAlive(false);
    }

    /**
     * Takes damage and updates the player's health accordingly.
     * If health reaches 0, the player dies.
     * @param damage The amount of damage to take.
     */
    @Override
    public void takeDamage(int damage) {
        takingDamage.takeDamage(damage, this);
        logger.info("Got " + damage + " damage\n" + getHealth() + " health points left");
        if (getHealth() == 0) {
            die();
        }
    }

    /**
     * Finds the direction vector towards a given endpoint.
     * @param endpoint The target endpoint to calculate the direction.
     * @return The normalized direction vector.
     */
    public Vector2 findDirectionVector(Vector2 endpoint) {
        Vector2 directionStartPoint = new Vector2(getPosition()).add(new Vector2(getWidth(), getHeight()).scl(0.5f));
        return new Vector2(endpoint.sub(directionStartPoint)).nor();
    }

    /**
     * Updates the player's balance after killing an enemy.
     * @param killedEnemyPrice The price of the killed enemy.
     */
    public void onEnemyKilled(int killedEnemyPrice) {
        setBalance(getBalance() + killedEnemyPrice);
        logger.info("Current balance: " + getBalance());
    }

    /**
     * Heals the player based on the healing interval.
     */
    public void heal() {
        setHealth(getHealth() + healingPerInterval);
    }

    /**
     * Ticks the player, updating healing and effects.
     * @param deltaTime The time that has passed since the last update.
     */
    @Override
    public void tick(float deltaTime) {
        healingTimer.tick(deltaTime);
        if (healingTimer.isElapsed()) {
            heal();
        }
        updateEffects(deltaTime);
    }

    /**
     * Handles collisions with other entities, such as enemy projectiles.
     * Applies effects like paralysis based on the collision.
     * @param collisionTarget The entity that the player collided with.
     */
    @Override
    public void onCollision(Entity collisionTarget) {
        if (collisionTarget instanceof EnemyProjectile) {
            if (random.nextFloat(0f, 1f) > 0.9f) {
                takeEffect(new ParalyseEffect(true, 1.5f, this));
                PlayerIsParalysedEvent.invokeEvent();
                logger.info("Paralysed");
            }
        }
    }

    /**
     * Updates the player's position and collider based on a new position.
     * @param position The new position of the player.
     */
    public void updatePosition(Vector2 position) {
        setPosition(new Vector2(position).sub(new Vector2(getWidth(), getHeight()).scl(0.5f)));
        setCollider(new Collider(new Circle(new Vector2(position), getHeight() / 2)));
        weapon.update(getPosition().cpy().add(getWidth() / 2, getHeight() / 2), getHeight() / 2);
    }

    /**
     * Sets the logger for the player.
     * @param logger The logger to set.
     */
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    /**
     * Creates a new player instance with the provided properties.
     * @param name The name of the player.
     * @param description The description of the player.
     * @param texture The texture to represent the player.
     * @param size The size of the player.
     * @param position The position of the player.
     * @param health The initial health of the player.
     * @param maxHealth The maximum health of the player.
     * @param healingPerInterval The healing amount per interval.
     * @param timer The healing timer.
     * @param balance The initial balance of the player.
     * @param weaponFactory The factory used to create the player's weapon.
     */
    public Player(String name, String description, Texture texture, float size, Vector2 position, int health,
                  int maxHealth, int healingPerInterval, Timer timer, int balance, WeaponFactory weaponFactory) {
        super(name, description, texture, health, maxHealth);
        setSize(size, size);
        if (position != null) {
            setPosition(new Vector2(position).sub(new Vector2(getWidth(), getHeight()).scl(0.5f)));
            setCollider(new Collider(new Circle(new Vector2(position), getHeight() / 2)));
            weaponFactory.setPositionOfAttacker(getPosition().cpy().add(getWidth() / 2, getHeight() / 2));
        }
        weaponFactory.setRadiusOfAttacker(getHeight() / 2);
        this.weapon = weaponFactory.create();
        this.effectHandler = new EffectHandler();
        this.balance = balance;

        this.healingTimer = timer;
        this.healingPerInterval = healingPerInterval;

        rangedAttacking = new DirectionRangedAttackingStrategy();
        takingDamage = new StandartTakingDamageStrategy();

        logger = new Logger("Player", Logger.DEBUG);

        random = new Random();
        random.setSeed(System.currentTimeMillis());
    }

    @Override
    public Player clone() {
        return new Player(getName(), getDescription(), getTexture(), getHeight(), getPosition().cpy(), getHealth(),
            getMaxHealth(), getHealingPerInterval(), getHealingTimer().clone(), getBalance(), new BasicPlayerWeaponFactory());
    }
}
