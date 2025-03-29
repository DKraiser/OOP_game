package sk.stuba.fiit;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.factories.PlayerProjectileFactory;
import sk.stuba.fiit.projectiles.Projectile;

import java.util.Collection;

public class Weapon extends GameObject {
    private PlayerProjectileFactory projectileFactory;

    public void shoot(Vector2 direction, Collection<Projectile> projectileEnvironment) {
        projectileEnvironment.add(projectileFactory.create(direction));
    }

    public Weapon(String name, String description, Texture texture, PlayerProjectileFactory projectileFactory) {
        super(name, description, texture);
        this.projectileFactory = projectileFactory;
    }
}
