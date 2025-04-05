package sk.stuba.fiit.entities;

import com.badlogic.gdx.graphics.Texture;
import sk.stuba.fiit.EffectHandler;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.interfaces.attack.RangedAttacking;
import sk.stuba.fiit.projectiles.Projectile;
import sk.stuba.fiit.strategies.TargetRangedAttackingStrategy;

import java.util.Collection;

public class Spawner extends Entity implements Cloneable {
    EffectHandler effectHandler;
    RangedAttacking rangedAttacking;
    Weapon weapon;

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void attack(Entity target, Collection<Projectile> projectileEnvironment) {
        rangedAttacking.attack(target, projectileEnvironment, weapon);
    }

    @Override
    public void die() {

    }

    @Override
    public void takeDamage(int damage) {

    }

    @Override
    public Spawner clone() {
        Spawner clone = new Spawner(getName(), getDescription(), getTexture(), getHealth(), getMaxHealth(), null);
        clone.getSprite().set(getSprite());
        return clone;
    }

    public Spawner(String name, String description, Texture texture, int health, int maxHealth, Weapon weapon) {
        super(name, description, texture, health, maxHealth);
        this.effectHandler = new EffectHandler();
        this.weapon = weapon;
        this.rangedAttacking = new TargetRangedAttackingStrategy();
    }
}
