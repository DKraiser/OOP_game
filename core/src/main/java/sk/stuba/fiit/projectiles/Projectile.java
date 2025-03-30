package sk.stuba.fiit.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.effects.Effect;
import sk.stuba.fiit.entities.Entity;
import sk.stuba.fiit.screens.GameScreen;

import java.util.List;

public abstract class Projectile extends Entity {
    private Vector2 direction;
    private float speed;

    public Vector2 getDirection() { return direction; }
    public void setDirection(Vector2 direction) { this.direction = direction; }

    public float getSpeed() { return speed; }
    public void setSpeed(float speed) { this.speed = speed; }

    public void move(float deltaTime) {
        getSprite().translate(new Vector2(getDirection().x * speed * deltaTime, getDirection().y * speed * deltaTime));
    }

    public Projectile(String name, String description, Texture texture, int health, int maxHealth, Vector2 direction, float speed) {
        super(name, description, texture, health, maxHealth);
        this.direction = direction;
        this.speed = speed;
    }
}
