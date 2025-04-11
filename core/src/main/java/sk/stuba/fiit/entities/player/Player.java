package sk.stuba.fiit.entities.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;
import sk.stuba.fiit.Collider;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.effects.Effect;
import sk.stuba.fiit.effects.ParalyseEffect;
import sk.stuba.fiit.factories.weaponfactories.WeaponFactory;
import sk.stuba.fiit.interfaces.Effectable;
import sk.stuba.fiit.interfaces.Tickable;
import sk.stuba.fiit.strategies.DirectionRangedAttackingStrategy;
import sk.stuba.fiit.EffectHandler;
import sk.stuba.fiit.entities.Entity;
import sk.stuba.fiit.interfaces.attack.RangedAttacking;
import sk.stuba.fiit.interfaces.Damageable;
import sk.stuba.fiit.projectiles.Projectile;
import sk.stuba.fiit.Timer;

import java.util.Dictionary;
import java.util.List;

import static java.lang.Math.max;

public class Player extends Entity implements Damageable, Tickable, Effectable {
    private int balance;
    private Weapon weapon;

    private EffectHandler effectHandler;
    private RangedAttacking rangedAttacking;
    private Timer healingTimer;
    private Runnable healingMechanism;
    private int healingPerInterval;
    private Logger logger;

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

    public Runnable getHealingMechanism() {
        return healingMechanism;
    }
    public void setHealingMechanism(Runnable healingMechanism) {
        this.healingMechanism = healingMechanism;
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
        if (effectHandler.getEffect(ParalyseEffect.class) == null) {
            rangedAttacking.attack(direction, projectileEnvironment, weapon);
        }
    }

    @Override
    public void die() {
        logger.info("Player was killed");
    }

    @Override
    public void takeDamage(int damage) {
        setHealth(max(getHealth() - damage, 0));
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
        if (healingTimer.isElapsed()) {
            healingMechanism.run();
            logger.info("Current health: " + getHealth());
        }
    }

    @Override
    public void tick(float deltaTime) {
        healingTimer.tick(deltaTime);
        heal();
        updateEffects(deltaTime);
    }

    public Player(String name, String description, Texture texture, float size, Vector2 position, int health, int maxHealth, int healingPerInterval, Timer timer, int balance, WeaponFactory weaponFactory) {
        super(name, description, texture, health, maxHealth);
        setSize(size, size);
        setPosition(new Vector2(position).sub(new Vector2(getWidth(), getHeight()).scl(0.5f)));

        weaponFactory.setPositionOfAttacker(getPosition().cpy().add(getWidth() / 2, getHeight() / 2));
        weaponFactory.setRadiusOfAttacker(getHeight() / 2);
        this.weapon = weaponFactory.create();
        this.effectHandler = new EffectHandler();
        this.balance = balance;

        this.healingTimer = timer;
        this.healingPerInterval = healingPerInterval;
        this.healingMechanism = (() -> this.setHealth(getHealth() + healingPerInterval));

        rangedAttacking = new DirectionRangedAttackingStrategy();
        setCollider(new Collider(new Circle(new Vector2(position), getHeight() / 2)));
        logger = new Logger("Player", Logger.INFO);
    }
}
