package sk.stuba.fiit.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.entities.Entity;
import sk.stuba.fiit.interfaces.attack.MeleeAttackingStrategy;

public abstract class Projectile extends Entity implements Cloneable{
    private Vector2 direction;
    private float speed;
    private MeleeAttackingStrategy attacker;
    private int damage;

    public Vector2 getDirection() { return direction; }
    public void setDirection(Vector2 direction) { this.direction = direction; }

    public float getSpeed() { return speed; }
    public void setSpeed(float speed) { this.speed = speed; }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public MeleeAttackingStrategy getAttacker() { return attacker; }
    public void setAttacker(MeleeAttackingStrategy attacker) { this.attacker = attacker; }

    public void move(float deltaTime) {
        translate(new Vector2(getDirection()).scl(speed * deltaTime));
        getCollider().move(new Vector2(getDirection()).scl(speed * deltaTime));
    }

    @Override
    public abstract Projectile clone();

    public Projectile(String name, String description, Texture texture, int health, int maxHealth, Vector2 direction, float speed, int damage) {
        super(name, description, texture, health, maxHealth);
        attacker = new sk.stuba.fiit.strategies.attacking.MeleeAttackingStrategy();
        this.damage = damage;
        this.direction = direction;
        this.speed = speed;
    }
}
