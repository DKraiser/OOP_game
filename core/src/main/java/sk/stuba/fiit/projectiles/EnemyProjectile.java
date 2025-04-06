package sk.stuba.fiit.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.effects.Effect;
import sk.stuba.fiit.EffectHandler;
import sk.stuba.fiit.interfaces.Damageable;

import java.util.ArrayList;
import java.util.List;

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
        EnemyProjectile clone = new EnemyProjectile(this.getName(), this.getDescription(), this.getTexture(), this.getHealth(), this.getMaxHealth(), null, this.getSpeed(), this.getDamage());
        clone.getSprite().set(this.getSprite());
        clone.setCollider(this.getCollider().clone());

        List<Effect> clonedEffects = new ArrayList<>();
        if (!this.getEffectHandler().getEffects().isEmpty()) {
            for (Effect effect : this.getEffectHandler().getEffects()) {
                clonedEffects.add(effect.clone());
                clonedEffects.getLast().setTarget(clone);
            }
            clone.getEffectHandler().setEffects(clonedEffects);
        }
        return clone;
    }

    public EnemyProjectile(String name, String description, Texture texture, int health, int maxHealth, Vector2 direction, float speed, int damage)
    {
        super(name, description, texture, health, maxHealth, direction, speed, damage);
        this.effectHandler = new EffectHandler();
    }

    public EnemyProjectile(){
        super();
        this.effectHandler = new EffectHandler();
    }

    public void takeEffect(Effect effect) {

    }

    public void updateEffects(float delta) {

    }
}
