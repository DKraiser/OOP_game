package sk.stuba.fiit.factories.projectilefactories;

import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.interfaces.Factory;
import sk.stuba.fiit.projectiles.Projectile;

import java.io.Serializable;

/**
 * Factory class for creating projectiles. The ProjectileFactory is responsible for
 * initializing a new projectile based on a template and setting its direction, rotation,
 * position, and collider.
 */
public class ProjectileFactory implements Factory<Projectile>, Serializable {
    protected final Projectile projectileTemplate;
    protected Projectile newProjectile;
    protected Vector2 directionVector;
    protected float rotation;
    protected float radiusOfAttacker;
    protected Vector2 positionOfAttacker;

    /**
     * Gets the position of the attacker.
     *
     * @return The position of the attacker.
     */
    public Vector2 getPositionOfAttacker() {
        return positionOfAttacker;
    }

    /**
     * Sets the position of the attacker.
     *
     * @param positionOfAttacker The position of the attacker.
     */
    public void setPositionOfAttacker(Vector2 positionOfAttacker) {
        this.positionOfAttacker = positionOfAttacker;
    }

    /**
     * Gets the radius of the attacker.
     *
     * @return The radius of the attacker.
     */
    public float getRadiusOfAttacker() {
        return radiusOfAttacker;
    }

    /**
     * Sets the radius of the attacker.
     *
     * @param radiusOfAttacker The radius of the attacker.
     */
    public void setRadiusOfAttacker(float radiusOfAttacker) {
        this.radiusOfAttacker = radiusOfAttacker;
    }

    /**
     * Creates a new projectile based on the provided direction vector.
     * The new projectile is a clone of the template with the specified direction, rotation,
     * and position. The collider's position is also adjusted according to the projectile's size.
     *
     * @param direction The direction in which the projectile will move (as a Vector2).
     * @return A new projectile instance with the specified properties.
     */
    @Override
    public Projectile create(Object... direction) {
        directionVector = (Vector2) direction[0];
        newProjectile = projectileTemplate.clone();
        newProjectile.setDirection(directionVector);

        rotation = (float) Math.atan(directionVector.y / directionVector.x) - (float) Math.PI / 2;
        if (directionVector.x < 0) rotation += (float) Math.PI;
        newProjectile.setRotation((float) (rotation * 180 / Math.PI));

        newProjectile.setPosition(new Vector2(positionOfAttacker.x - projectileTemplate.getWidth() / 2,
            positionOfAttacker.y - projectileTemplate.getHeight() / 2)
            .add(new Vector2(directionVector).scl(1.5f * radiusOfAttacker)));
        newProjectile.getCollider().setPosition(newProjectile.getPosition().cpy()
            .add(new Vector2(newProjectile.getWidth(), newProjectile.getHeight()).scl(0.25f)));

        return newProjectile;
    }

    /**
     * Constructs a new ProjectileFactory.
     *
     * @param projectileTemplate The template projectile to use for creating new projectiles.
     * @param radiusOfAttacker The radius of the attacker that fires the projectile.
     * @param positionOfAttacker The position of the attacker that fires the projectile.
     */
    public ProjectileFactory(Projectile projectileTemplate, float radiusOfAttacker, Vector2 positionOfAttacker) {
        this.projectileTemplate = projectileTemplate;
        this.radiusOfAttacker = radiusOfAttacker;
        this.positionOfAttacker = positionOfAttacker;
    }
}
