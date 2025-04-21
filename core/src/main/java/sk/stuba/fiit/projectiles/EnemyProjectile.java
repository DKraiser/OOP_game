package sk.stuba.fiit.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.MyGame;
import sk.stuba.fiit.effects.Effect;
import sk.stuba.fiit.EffectHandler;
import sk.stuba.fiit.entities.Entity;
import sk.stuba.fiit.entities.player.Player;
import sk.stuba.fiit.events.EnemyKilledEvent;
import sk.stuba.fiit.interfaces.Damageable;
import sk.stuba.fiit.interfaces.Mortal;

import java.util.ArrayList;
import java.util.List;

public class EnemyProjectile extends Projectile implements Damageable, Mortal {
    private EffectHandler effectHandler;
    private int price;

    public EffectHandler getEffectHandler() {
        return effectHandler;
    }

    public void setEffectHandler(EffectHandler effectHandler) {
        this.effectHandler = effectHandler;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void attack(Damageable target, int damage) {
        getAttacker().attack(target, damage);
    }

    @Override
    public void die() {
        setAlive(false);
        EnemyKilledEvent.invokeEvent(getPrice());
    }

    @Override
    public void takeDamage(int damage) {
        setHealth(Math.max(getHealth() - damage, 0));
        if (getHealth() <= 0)
            die();
    }

    @Override
    public Projectile clone() {
        EnemyProjectile clone = new EnemyProjectile(this.getName(), this.getDescription(), this.getTexture(), this.getHealth(), this.getMaxHealth(), null, this.getSpeed(), this.getDamage(), this.getPrice());
        if (!MyGame.TESTMODE)
        {
            if (getSprite() != null) {
                clone.getSprite().set(this.getSprite());
            }
        }
        clone.setCollider(this.getCollider().clone());

        List<Effect> clonedEffects = new ArrayList<>();
        if (!this.getEffectHandler().getEffects().isEmpty()) {
            for (Effect effect : this.getEffectHandler().getEffects()) {
                clonedEffects.add(effect.clone());
                clonedEffects.getLast().setTarget(clone);
            }
            clone.getEffectHandler().takeEffect(clonedEffects);
        }
        return clone;
    }

    public EnemyProjectile(String name, String description, Texture texture, int health, int maxHealth, Vector2 direction, float speed, int damage, int price)
    {
        super(name, description, texture, health, maxHealth, direction, speed, damage);
        effectHandler = new EffectHandler();
        this.price = price;
    }

    @Override
    public void onCollision(Entity collisionEntity) {
        if (collisionEntity instanceof Player) {
            attack(collisionEntity, this.getDamage());
            setAlive(false);
        }
    }
}
