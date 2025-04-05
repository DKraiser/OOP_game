package sk.stuba.fiit.factories.weaponfactories;

import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.factories.projectilefactories.ProjectileFactory;
import sk.stuba.fiit.interfaces.Factory;
import sk.stuba.fiit.projectiles.Projectile;

public abstract class WeaponFactory implements Factory<Weapon> {
    protected Projectile projectileTemplate;
    protected ProjectileFactory projectileFactory;
    protected Weapon weapon;
    protected float radiusOfAttacker;
    protected Vector2 positionOfAttacker;

    public void setRadiusOfAttacker(float radiusOfAttacker) {
        this.radiusOfAttacker = radiusOfAttacker;
    }

    public void setPositionOfAttacker(Vector2 positionOfAttacker) {
        this.positionOfAttacker = positionOfAttacker;
    }

    public float getRadiusOfAttacker() {
        return radiusOfAttacker;
    }

    public Vector2 getPositionOfAttacker() {
        return positionOfAttacker;
    }

    public abstract void adjustProjectileTemplate();

    public WeaponFactory(float radiusOfAttacker, Vector2 positionOfAttacker) {
        this.radiusOfAttacker = radiusOfAttacker;
        this.positionOfAttacker = positionOfAttacker;
        adjustProjectileTemplate();
    }

    public WeaponFactory() {
        this.radiusOfAttacker = 0;
        this.positionOfAttacker = null;
        adjustProjectileTemplate();
    }
}
