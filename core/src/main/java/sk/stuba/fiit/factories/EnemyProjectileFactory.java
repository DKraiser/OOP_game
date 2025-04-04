package sk.stuba.fiit.factories;

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

        List<Effect> effects = new ArrayList<>();
        for (Effect effect : ((EnemyProjectile) projectileTemplate).getEffectHandler().getEffects()) {
            effects.add(effect.clone());
            effect.setTarget(newProjectile);
        }
        
        return newProjectile;
    }

    public EnemyProjectileFactory(Projectile projectileTemplate, float radiusOfAttacker, Vector2 positionOfAttacker) {
        super(projectileTemplate, radiusOfAttacker, positionOfAttacker);
    }
}
