package sk.stuba.fiit.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.effects.Effect;

import java.util.List;

public class PlayerProjectile extends Projectile implements Cloneable{
    @Override
    public Projectile clone() {
        Projectile clone = new PlayerProjectile(this.getName(), this.getDescription(), this.getTexture(), this.getHealth(), this.getMaxHealth(), null, this.getSpeed(), this.getDamage());
        clone.getSprite().set(this.getSprite());
        clone.setCollider(this.getCollider().clone());

        return clone;
    }

    public PlayerProjectile(String name, String description, Texture texture, int health, int maxHealth, Vector2 direction, float speed, int damage) {
        super(name, description, texture, health, maxHealth, direction, speed, damage);
    }

    public PlayerProjectile() {
        super();
    }

    @Override
    public void takeDamage(int damage) { }

    @Override
    public void die() { }
}
