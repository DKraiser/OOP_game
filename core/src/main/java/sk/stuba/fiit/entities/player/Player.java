package sk.stuba.fiit.entities.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;
import sk.stuba.fiit.Collider;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.effects.Effect;
import sk.stuba.fiit.effects.ParalyseEffect;
import sk.stuba.fiit.effects.ResistanceEffect;
import sk.stuba.fiit.events.PlayerIsParalysedEvent;
import sk.stuba.fiit.factories.weaponfactories.BasicPlayerWeaponFactory;
import sk.stuba.fiit.factories.weaponfactories.WeaponFactory;
import sk.stuba.fiit.interfaces.*;
import sk.stuba.fiit.projectiles.EnemyProjectile;
import sk.stuba.fiit.strategies.attacking.DirectionRangedAttackingStrategy;
import sk.stuba.fiit.EffectHandler;
import sk.stuba.fiit.entities.Entity;
import sk.stuba.fiit.interfaces.attack.RangedAttackingStrategy;
import sk.stuba.fiit.projectiles.Projectile;
import sk.stuba.fiit.Timer;
import sk.stuba.fiit.strategies.takingDamage.StandartTakingDamageStrategy;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

import static java.lang.Math.max;

public class Player extends Entity implements Damageable, Mortal, Tickable, Effectable , Serializable, Cloneable{
    private int balance;
    private Weapon weapon;

    private EffectHandler effectHandler;
    private RangedAttackingStrategy rangedAttacking;
    private TakingDamageStrategy takingDamage;
    private Timer healingTimer;
    private int healingPerInterval;
    private transient Logger logger;

    public Weapon getWeapon() {
        return weapon;
    }
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public EffectHandler getEffectHandler() { return effectHandler; }
    public void setEffectHandler(EffectHandler effectHandler) {
        this.effectHandler = effectHandler;
    }

    public int getBalance() { return balance; }
    public void setBalance(int balance) { this.balance = balance; }

    public int getHealingPerInterval() { return healingPerInterval; }
    public void setHealingPerInterval(int healingPerInterval) {
        if (healingPerInterval <= 0) {
            throw new IllegalArgumentException("healingPerInterval must be greater than 0");
        }
        this.healingPerInterval = healingPerInterval;
    }

    public Timer getHealingTimer() {
        return healingTimer;
    }
    public void setHealingTimer(Timer timer) {
        this.healingTimer = timer;
    }

    public RangedAttackingStrategy getAttackStrategy() {
        return rangedAttacking;
    }

    public void setRangedAttacking(RangedAttackingStrategy rangedAttacking) {
        this.rangedAttacking = rangedAttacking;
    }

    public TakingDamageStrategy getTakingDamage() {
        return takingDamage;
    }

    public void setTakingDamage(TakingDamageStrategy takingDamage) {
        this.takingDamage = takingDamage;
    }

    @Override
    public void takeEffect(Effect effect) {
        effectHandler.takeEffect(effect);
    }

    @Override
    public void updateEffects(float deltaTime) {
        effectHandler.updateEffects(deltaTime);
    }

    public void attack(Vector2 direction, List<Projectile> projectileEnvironment) {
        rangedAttacking.attack(direction, projectileEnvironment, weapon);
    }

    @Override
    public void die() {
        logger.info("Player was killed");
        setAlive(false);
    }

    @Override
    public void takeDamage(int damage) {
        takingDamage.takeDamage(damage, this);
        logger.info("Get " + damage + " damage\n" + getHealth() + " health points left");
        if (getHealth() == 0) {
            die();
        }
    }

    public Vector2 findDirectionVector(Vector2 endpoint) {
        Vector2 directionStartPoint = new Vector2(getPosition()).add(new Vector2(getWidth(), getHeight()).scl(0.5f));
        return new Vector2(endpoint.sub(directionStartPoint)).nor();
    }

    public void onEnemyKilled(int killedEnemyPrice) {
        setBalance(getBalance() + killedEnemyPrice);
        logger.info("Current balance: " + getBalance());
    }

    public void heal() {
        setHealth(getHealth() + healingPerInterval);
    }

    @Override
    public void tick(float deltaTime) {
        healingTimer.tick(deltaTime);
        if (healingTimer.isElapsed()) {
            heal();
        }
        updateEffects(deltaTime);
    }

    @Override
    public void onCollision(Entity collisionTarget) {
        if (collisionTarget instanceof EnemyProjectile) {
            Random random = new Random();
            random.setSeed(System.currentTimeMillis());
            if (random.nextFloat(0f, 1f) > 0.9f) {
                PlayerIsParalysedEvent.invokeEvent();
                takeEffect(new ParalyseEffect(true, 1.5f, this));
                logger.info("Paralyse");
            }
        }
    }

    public void updatePosition(Vector2 position) {
        setPosition(new Vector2(position).sub(new Vector2(getWidth(), getHeight()).scl(0.5f)));
        setCollider(new Collider(new Circle(new Vector2(position), getHeight() / 2)));
        weapon.update(getPosition().cpy().add(getWidth() / 2, getHeight() / 2), getHeight() / 2);
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public Player(String name, String description, Texture texture, float size, Vector2 position, int health, int maxHealth, int healingPerInterval, Timer timer, int balance, WeaponFactory weaponFactory) {
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
    }

    @Override
    public Player clone() {
        return new Player(getName(), getDescription(), getTexture(), getHeight(), getPosition().cpy(), getHealth(), getMaxHealth(), getHealingPerInterval(), getHealingTimer().clone(), getBalance(), new BasicPlayerWeaponFactory());
    }

    public Player() {
        super();
    }
}
