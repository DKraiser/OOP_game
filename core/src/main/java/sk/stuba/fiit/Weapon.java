package sk.stuba.fiit;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.factories.projectilefactories.ProjectileFactory;
import sk.stuba.fiit.projectiles.Projectile;

import java.io.Serializable;
import java.util.Collection;

/**
 * Represents a weapon in the game that can shoot projectiles using a {@link ProjectileFactory}.
 * Extends {@link GameObject} and implements {@link Cloneable} and {@link Serializable}.
 */
public class Weapon extends GameObject implements Cloneable, Serializable {
    private final ProjectileFactory projectileFactory;

    /**
     * Updates the weapon's position and relevant projectile factory attributes such as the
     * position and radius of the attacker.
     *
     * @param position the new position of the weapon and attacker
     * @param radius the radius of the attacker, passed to the projectile factory
     */
    public void update(Vector2 position, float radius)
    {
        setPosition(new Vector2(position));
        projectileFactory.setPositionOfAttacker(position);
        projectileFactory.setRadiusOfAttacker(radius);
    }

    /**
     * Shoots a projectile in the specified direction and adds it to the provided projectile environment.
     *
     * @param direction the direction in which to shoot the projectile
     * @param projectileEnvironment the collection to which the new projectile will be added
     */
    public void shoot(Vector2 direction, Collection<Projectile> projectileEnvironment) {
        projectileEnvironment.add(projectileFactory.create(direction));
    }

    /**
     * Constructs a new {@code Weapon} with the specified name, description, texture, and projectile factory.
     *
     * @param name the name of the weapon
     * @param description a short description of the weapon
     * @param texture the texture representing the weapon's appearance
     * @param projectileFactory the factory used to create projectiles for this weapon
     */
    public Weapon(String name, String description, Texture texture, ProjectileFactory projectileFactory) {
        super(name, description, texture);
        this.projectileFactory = projectileFactory;
    }
}
