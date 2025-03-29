package sk.stuba.fiit.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.effects.Effect;
import sk.stuba.fiit.interfaces.Fightable;

import java.util.List;

public class EnemyProjectile extends Projectile implements Fightable {
    @Override
    public void attack(Fightable target, int damage) {
        target.takeDamage(damage);
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

    public Projectile clone() {
        return new EnemyProjectile(this.getName(), this.getDescription(), this.getTexture(), this.getHealth(), this.getMaxHealth(), this.getEffects(), this.getDirection(), this.getSpeed());
    }

    public EnemyProjectile(String name, String description, Texture texture, int health, int maxHealth, List<Effect> effects, Vector2 direction, float speed)
    {
        super(name, description, texture, health, maxHealth, effects, direction, speed);
    }
}
