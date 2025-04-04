package sk.stuba.fiit.factories;

import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.projectiles.Projectile;

public abstract class WeaponFactory implements Factory <Weapon> {
    protected Projectile projectileTemplate;
    protected ProjectileFactory projectileFactory;
    protected Weapon weapon;
    protected float radiusOfAttacker;
    protected Vector2 positionOfAttacker;

    public WeaponFactory(float radiusOfAttacker, Vector2 positionOfAttacker) {
        this.radiusOfAttacker = radiusOfAttacker;
        this.positionOfAttacker = positionOfAttacker;
    }
}
