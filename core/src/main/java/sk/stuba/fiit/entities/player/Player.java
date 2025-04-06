package sk.stuba.fiit.entities.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;
import sk.stuba.fiit.Collider;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.effects.Effect;
import sk.stuba.fiit.factories.weaponfactories.WeaponFactory;
import sk.stuba.fiit.strategies.DirectionRangedAttackingStrategy;
import sk.stuba.fiit.EffectHandler;
import sk.stuba.fiit.entities.Entity;
import sk.stuba.fiit.interfaces.attack.RangedAttacking;
import sk.stuba.fiit.interfaces.Damageable;
import sk.stuba.fiit.projectiles.Projectile;

import java.util.List;

public class Player extends Entity implements Damageable {
    private int balance;
    private Weapon weapon;
    private EffectHandler effectHandler;
    private RangedAttacking rangedAttacking;

    private Logger logger;

    public Weapon getWeapon() {
        return weapon;
    }
    public EffectHandler getEffectHandler() { return effectHandler; }
    public int getBalance() { return balance; }

    public void setWeapon(Weapon weapon) {this.weapon = weapon; }
    public void setEffectHandler(EffectHandler effectHandler) { this.effectHandler = effectHandler; }
    public void setBalance(int balance) { this.balance = balance; }

    public void takeEffect(Effect effect) {
        effectHandler.getEffects().add(effect);
    }

    public void updateEffects(float delta) {
        for (Effect effect : effectHandler.getEffects())
            effect.tickEffect(delta);
    }

    public Player(String name, String description, Texture texture, float size, Vector2 position, int health, int maxHealth, int balance, WeaponFactory weaponFactory) {
        super(name, description, texture, health, maxHealth);
        getSprite().setSize(size, size);
        getSprite().setPosition(new Vector2(position).sub(new Vector2(getSprite().getWidth(), getSprite().getHeight()).scl(0.5f)));

        weaponFactory.setPositionOfAttacker(position);
        weaponFactory.setRadiusOfAttacker(getSprite().getHeight() / 2);
        this.weapon = weaponFactory.create();

        this.effectHandler = new EffectHandler();
        this.balance = balance;

        rangedAttacking = new DirectionRangedAttackingStrategy();
        setCollider(new Collider(new Circle(new Vector2(position), getSprite().getHeight() / 2)));
        logger = new Logger("Player", Logger.INFO);
    }

    @Override
    public void die() {
        logger.info("Killed");
    }

    @Override
    public void takeDamage(int damage) {
        setHealth(getHealth() - damage);
        logger.info("Get " + damage + " damage\n" + getHealth() + " health points left");
        if (getHealth() <= 0) {
            die();
        }
    }

    public Vector2 findDirectionVector(Vector2 endpoint) {
        Vector2 directionStartPoint = new Vector2(getSprite().getPosition()).add(new Vector2(getSprite().getWidth() / 2, getSprite().getHeight() / 2));
        return new Vector2(endpoint.sub(directionStartPoint)).nor();
    }

    public void attack(Vector2 direction, List<Projectile> projectileEnvironment) {
        rangedAttacking.attack(direction, projectileEnvironment, weapon);
    }

    public void onEnemyKilled(int killedEnemyPrice) {
        setBalance(getBalance() + killedEnemyPrice);
    }
}
