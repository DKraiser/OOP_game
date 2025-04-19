package sk.stuba.fiit.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.MyGame;
import sk.stuba.fiit.effects.Effect;
import sk.stuba.fiit.entities.Entity;
import sk.stuba.fiit.entities.Spawner;

import java.io.Serializable;
import java.util.List;

public class PlayerProjectile extends Projectile implements Cloneable, Serializable {
    @Override
    public Projectile clone() {
        Projectile clone = new PlayerProjectile(this.getName(), this.getDescription(), this.getTexture(), this.getHealth(), this.getMaxHealth(), null, this.getSpeed(), this.getDamage());
        if (!MyGame.TESTMODE)
        {
            clone.getSprite().set(this.getSprite());
        }
        clone.setCollider(this.getCollider().clone());

        return clone;
    }

    public PlayerProjectile(String name, String description, Texture texture, int health, int maxHealth, Vector2 direction, float speed, int damage) {
        super(name, description, texture, health, maxHealth, direction, speed, damage);
    }

    @Override
    public void takeDamage(int damage) { }

    @Override
    public void die() { }

    @Override
    public void onCollision(Entity collisionEntity) {
        if (collisionEntity instanceof EnemyProjectile || collisionEntity instanceof Spawner) {
            getAttacker().attack(collisionEntity, getDamage());
            setAlive(false);
        }
    }
}
