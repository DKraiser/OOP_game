package sk.stuba.fiit.factories.projectilefactories;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;
import sk.stuba.fiit.interfaces.Factory;
import sk.stuba.fiit.projectiles.Projectile;

public class ProjectileFactory implements Factory<Projectile> {
    protected final Projectile projectileTemplate;
    protected Projectile newProjectile;
    protected Vector2 directionVector;
    protected float rotation;
    protected float radiusOfAttacker;
    protected Vector2 positionOfAttacker;
    protected final Logger logger;

    public void setPositionOfAttacker(Vector2 positionOfAttacker) {
        this.positionOfAttacker = positionOfAttacker;
    }

    public void setRadiusOfAttacker(float radiusOfAttacker) {
        this.radiusOfAttacker = radiusOfAttacker;
    }

    public void log()
    {
        logger.debug("Projectile Position: " + newProjectile.getSprite().getPosition());
        logger.debug("Projectile Direction: " + directionVector);
        logger.debug("Projectile Rotation: " + newProjectile.getSprite().getRotation());
    }

    @Override
    public Projectile create(Object... direction) {
        directionVector = (Vector2) direction[0];
        newProjectile = projectileTemplate.clone();
        newProjectile.setDirection(directionVector);
        rotation = (float)Math.atan(directionVector.y / directionVector.x) - (float)Math.PI / 2;
        if (directionVector.x < 0) rotation += (float)Math.PI;
        newProjectile.getSprite().setRotation((float)(rotation * 180 / Math.PI));

        newProjectile.getSprite().setPosition(new Vector2(positionOfAttacker.x, positionOfAttacker.y).add(directionVector.scl(radiusOfAttacker + newProjectile.getSprite().getHeight() / 2)).sub(new Vector2(newProjectile.getSprite().getWidth() / 2, newProjectile.getSprite().getHeight() / 2)));

        log();

        return newProjectile;
    }

    public ProjectileFactory(Projectile projectileTemplate, float radiusOfAttacker, Vector2 positionOfAttacker) {
        this.projectileTemplate = projectileTemplate;
        this.radiusOfAttacker = radiusOfAttacker;
        this.positionOfAttacker = positionOfAttacker;
        logger = new Logger("PlayerProjectileFactory", Logger.DEBUG);
    }
}
