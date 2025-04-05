package sk.stuba.fiit;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.factories.projectilefactories.ProjectileFactory;
import sk.stuba.fiit.projectiles.Projectile;

import java.util.Collection;

public class Weapon extends GameObject implements Cloneable {
    private final ProjectileFactory projectileFactory;
    private Vector2 positionOfWeapon;

    public Vector2 getPositionOfWeapon() {
        return positionOfWeapon;
    }

    public void update(Vector2 position, float radius)
    {
        positionOfWeapon = new Vector2(position);
        projectileFactory.setPositionOfAttacker(position);
        projectileFactory.setRadiusOfAttacker(radius);
    }

    public void shoot(Vector2 direction, Collection<Projectile> projectileEnvironment) {
        projectileEnvironment.add(projectileFactory.create(direction));
    }

    public Weapon(String name, String description, Texture texture, ProjectileFactory projectileFactory) {
        super(name, description, texture);
        this.projectileFactory = projectileFactory;
    }
}
