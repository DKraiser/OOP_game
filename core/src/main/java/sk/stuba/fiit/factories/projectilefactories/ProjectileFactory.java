package sk.stuba.fiit.factories.projectilefactories;

import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.interfaces.Factory;
import sk.stuba.fiit.projectiles.Projectile;

import java.io.Serializable;

public class ProjectileFactory implements Factory<Projectile>, Serializable {
    protected final Projectile projectileTemplate;
    protected Projectile newProjectile;
    protected Vector2 directionVector;
    protected float rotation;
    protected float radiusOfAttacker;
    protected Vector2 positionOfAttacker;

    public Vector2 getPositionOfAttacker() { return positionOfAttacker; }
    public void setPositionOfAttacker(Vector2 positionOfAttacker) {
        this.positionOfAttacker = positionOfAttacker;
    }

    public float getRadiusOfAttacker() { return radiusOfAttacker; }
    public void setRadiusOfAttacker(float radiusOfAttacker) {
        this.radiusOfAttacker = radiusOfAttacker;
    }

    @Override
    public Projectile create(Object... direction) {
        directionVector = (Vector2) direction[0];
        newProjectile = projectileTemplate.clone();
        newProjectile.setDirection(directionVector);
        rotation = (float)Math.atan(directionVector.y / directionVector.x) - (float)Math.PI / 2;
        if (directionVector.x < 0) rotation += (float)Math.PI;
        newProjectile.setRotation((float)(rotation * 180 / Math.PI));

        newProjectile.setPosition(new Vector2(positionOfAttacker.x - projectileTemplate.getWidth() / 2, positionOfAttacker.y - projectileTemplate.getHeight() / 2).add(new Vector2(directionVector).scl(1.5f * radiusOfAttacker)));

        newProjectile.getCollider().setPosition(newProjectile.getPosition().cpy().add(new Vector2(newProjectile.getWidth(), newProjectile.getHeight()).scl(0.25f)));

        return newProjectile;
    }

    public ProjectileFactory(Projectile projectileTemplate, float radiusOfAttacker, Vector2 positionOfAttacker) {
        this.projectileTemplate = projectileTemplate;
        this.radiusOfAttacker = radiusOfAttacker;
        this.positionOfAttacker = positionOfAttacker;
    }
}
