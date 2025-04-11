package sk.stuba.fiit.entities;

import com.badlogic.gdx.graphics.Texture;
import sk.stuba.fiit.EffectHandler;
import sk.stuba.fiit.MyGame;
import sk.stuba.fiit.Timer;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.events.EnemyKilledEvent;
import sk.stuba.fiit.interfaces.Tickable;
import sk.stuba.fiit.interfaces.attack.RangedAttacking;
import sk.stuba.fiit.projectiles.Projectile;
import sk.stuba.fiit.strategies.TargetRangedAttackingStrategy;

import java.util.Collection;

public class Spawner extends Entity implements Cloneable, Tickable {
    private RangedAttacking rangedAttacking;
    private Weapon weapon;
    private int price;
    private Timer spawnTimer;

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Timer getSpawnTimer() {
        return spawnTimer;
    }

    public void setSpawnTimer(Timer timer) {
        this.spawnTimer = timer;
    }

    public void attack(Entity target, Collection<Projectile> projectileEnvironment) {
        if (spawnTimer.isElapsed()) {
            rangedAttacking.attack(target, projectileEnvironment, weapon);
        }
    }

    @Override
    public void die() {
        EnemyKilledEvent.invokeEvent(getPrice());
        setAlive(false);
    }

    @Override
    public void takeDamage(int damage) {
        setHealth(getHealth() - damage);
        if (getHealth() <= 0) {
            die();
        }
    }

    @Override
    public Spawner clone() {
        Spawner clone = new Spawner(getName(), getDescription(), getTexture(), getHealth(), getMaxHealth(), null, getPrice(), spawnTimer != null ? spawnTimer.clone() : null);
        if (!MyGame.TESTMODE) {
            if (getSprite() != null) {
                clone.getSprite().set(getSprite());
            }
        }
        return clone;
    }

    @Override
    public void tick(float deltaTime) {
        spawnTimer.tick(deltaTime);
    }

    public Spawner(String name, String description, Texture texture, int health, int maxHealth, Weapon weapon, int price, Timer timer) {
        super(name, description, texture, health, maxHealth);
        this.weapon = weapon;
        this.rangedAttacking = new TargetRangedAttackingStrategy();
        this.price = price;
        this.spawnTimer = timer;
    }
}
