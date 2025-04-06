package sk.stuba.fiit.entities;

import com.badlogic.gdx.graphics.Texture;
import sk.stuba.fiit.EffectHandler;
import sk.stuba.fiit.Timer;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.events.EnemyKilledEvent;
import sk.stuba.fiit.interfaces.attack.RangedAttacking;
import sk.stuba.fiit.projectiles.Projectile;
import sk.stuba.fiit.strategies.TargetRangedAttackingStrategy;

import java.util.Collection;

public class Spawner extends Entity implements Cloneable {
    private EffectHandler effectHandler;
    private RangedAttacking rangedAttacking;
    private Weapon weapon;
    private int price;
    private Timer timer;

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

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public void attack(Entity target, Collection<Projectile> projectileEnvironment) {
        if (timer.isElapsed()) {
            rangedAttacking.attack(target, projectileEnvironment, weapon);
        }
    }

    @Override
    public void die() {
        EnemyKilledEvent.invokeEvent(getPrice());
    }

    @Override
    public void takeDamage(int damage) {

    }

    @Override
    public Spawner clone() {
        Spawner clone = new Spawner(getName(), getDescription(), getTexture(), getHealth(), getMaxHealth(), null, getPrice(), timer != null ? timer.clone() : null);
        clone.getSprite().set(getSprite());
        return clone;
    }

    public Spawner(String name, String description, Texture texture, int health, int maxHealth, Weapon weapon, int price, Timer timer) {
        super(name, description, texture, health, maxHealth);
        this.effectHandler = new EffectHandler();
        this.weapon = weapon;
        this.rangedAttacking = new TargetRangedAttackingStrategy();
        this.price = price;
        this.timer = timer;
    }
}
