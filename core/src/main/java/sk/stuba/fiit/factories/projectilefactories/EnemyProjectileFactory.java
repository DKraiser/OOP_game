package sk.stuba.fiit.factories.projectilefactories;

import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.projectiles.EnemyProjectile;
import sk.stuba.fiit.effects.Effect;
import sk.stuba.fiit.projectiles.Projectile;

import java.util.ArrayList;
import java.util.List;

public class EnemyProjectileFactory extends ProjectileFactory {
    @Override
    public Projectile create(Object ... direction) {
        super.create(direction);
        return newProjectile;
    }

    public EnemyProjectileFactory(Projectile projectileTemplate, float radiusOfAttacker, Vector2 positionOfAttacker) {
        super(projectileTemplate, radiusOfAttacker, positionOfAttacker);
    }
}
