package sk.stuba.fiit.factories;

import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.projectiles.EnemyProjectile;
import sk.stuba.fiit.effects.Effect;

import java.util.ArrayList;
import java.util.List;

public class EnemyProjectileFactory implements Factory<EnemyProjectile>{
    private final EnemyProjectile projectileTemplate;

    @Override
    public EnemyProjectile create(Object ... direction) {
        List<Effect> effects = new ArrayList<>();
        for (Effect effect : projectileTemplate.getEffectHandler().getEffects())
            effects.add(effect.clone());

        return new EnemyProjectile(projectileTemplate.getName(), projectileTemplate.getDescription(), projectileTemplate.getTexture(),
            projectileTemplate.getHealth(), projectileTemplate.getMaxHealth(), projectileTemplate.getEffectHandler(),
            (Vector2) direction[0], projectileTemplate.getSpeed());
    }

    public EnemyProjectileFactory(EnemyProjectile projectileTemplate) {
        this.projectileTemplate = projectileTemplate;
    }
}
