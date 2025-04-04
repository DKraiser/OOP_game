package sk.stuba.fiit.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.effects.Effect;
import sk.stuba.fiit.EffectHandler;
import sk.stuba.fiit.interfaces.Damageable;

public class EnemyProjectile extends Projectile implements Damageable {
    private EffectHandler effectHandler;

    public EffectHandler getEffectHandler() { return effectHandler; }

    public void setEffectHandler(EffectHandler effectHandler) { this.effectHandler = effectHandler; }

    public void attack(Damageable target, int damage) {
        getAttacker().attack(target, damage);
    }

    @Override
    public void die() {
        System.out.println("Dead " + getName());
    }

    @Override
    public void takeDamage(int damage) {
        setHealth(getHealth() - damage);
        if (getHealth() <= 0)
            die();
    }

    @Override
    public Projectile clone() {
        return new EnemyProjectile(this.getName(), this.getDescription(), this.getTexture(), this.getHealth(), this.getMaxHealth(), this.getEffectHandler().clone(), null, this.getSpeed());
    }

    public EnemyProjectile(String name, String description, Texture texture, int health, int maxHealth, EffectHandler effectHandler, Vector2 direction, float speed)
    {
        super(name, description, texture, health, maxHealth, direction, speed);
        this.effectHandler = effectHandler;
    }

    public void takeEffect(Effect effect) {

    }

    public void updateEffects(float delta) {

    }
}
