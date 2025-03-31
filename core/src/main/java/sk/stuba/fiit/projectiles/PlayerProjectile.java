package sk.stuba.fiit.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.effects.Effect;

import java.util.List;

public class PlayerProjectile extends Projectile implements Cloneable{
    @Override
    public Projectile clone() {
        return new PlayerProjectile(this.getName(), this.getDescription(), this.getTexture(), this.getHealth(), this.getMaxHealth(), null, this.getSpeed());
    }

    public PlayerProjectile(String name, String description, Texture texture, int health, int maxHealth, Vector2 direction, float speed) {
        super(name, description, texture, health, maxHealth, direction, speed);
    }
}
