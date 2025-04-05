package sk.stuba.fiit.factories.projectilefactories;

import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.projectiles.Projectile;

public class PlayerProjectileFactory extends ProjectileFactory {
    @Override
    public Projectile create(Object ... direction) {
        super.create(direction);
        return newProjectile;
    }

    public PlayerProjectileFactory(Projectile projectileTemplate, float radiusOfAttacker, Vector2 positionOfAttacker) {
        super(projectileTemplate, radiusOfAttacker, positionOfAttacker);
    }
}
