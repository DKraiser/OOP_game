package sk.stuba.fiit.effects;

import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.entities.Entity;
import sk.stuba.fiit.entities.player.Player;
import sk.stuba.fiit.interfaces.Damageable;
import sk.stuba.fiit.interfaces.attack.RangedAttacking;
import sk.stuba.fiit.projectiles.Projectile;
import sk.stuba.fiit.strategies.DirectionRangedAttackingStrategy;
import sk.stuba.fiit.strategies.TargetRangedAttackingStrategy;

import java.util.Collection;

public class ParalyseEffect extends Effect{
    private final String name;
    private final String description;

    public ParalyseEffect(boolean isFinite, float duration, float remainingTime, Entity target) {
        super(null, null, 1, isFinite, duration, remainingTime, target);

        name = "Paralyse";
        description = "Your weapons are broken and cannot shoot";

        setName(name);
        setDescription(description);
    }

    public ParalyseEffect(boolean isFinite, float duration, Entity target) {
        super(null, null, 1, isFinite, duration, target);

        name = "Paralyse";
        description = "Your weapons are broken and cannot shoot";

        setName(name);
        setDescription(description);
    }

    @Override
    public Effect clone() {
        return new ParalyseEffect(isFinite(), getDuration(), getRemainingTime(), null);
    }

    @Override
    public void applyEffect() {
        RangedAttacking noAttackStrategy = new TargetRangedAttackingStrategy() {
            @Override
            public void attack(Vector2 direction, Collection<Projectile> projectileEnvironment, Weapon weapon) { }

            @Override
            public void attack(Damageable target, Collection<Projectile> projectileEnvironment, Weapon weapon) { }
        };

        if (getTarget() instanceof Player) {
            ((Player) getTarget()).setRangedAttacking(noAttackStrategy);
        }
    }

    @Override
    public void removeEffect() {
        if (getTarget() instanceof Player) {
            ((Player) getTarget()).setRangedAttacking(new DirectionRangedAttackingStrategy());
        }
    }
}
