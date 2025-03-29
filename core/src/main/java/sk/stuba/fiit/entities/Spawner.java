package sk.stuba.fiit.entities;

import com.badlogic.gdx.graphics.Texture;
import sk.stuba.fiit.effects.Effect;
import sk.stuba.fiit.factories.EnemyProjectileFactory;
import sk.stuba.fiit.projectiles.EnemyProjectile;

import java.util.List;

public class Spawner extends Entity {
    private EnemyProjectileFactory projectileFactory;

    public Spawner(String name, String description, Texture texture, int health, int maxHealth, List<Effect> effects, EnemyProjectileFactory projectileFactory) {
        super(name, description, texture, health, maxHealth, effects);
        this.projectileFactory = projectileFactory;
    }
}
