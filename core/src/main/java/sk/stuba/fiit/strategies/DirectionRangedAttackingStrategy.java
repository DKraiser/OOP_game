package sk.stuba.fiit.strategies;

import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.interfaces.Damageable;
import sk.stuba.fiit.interfaces.attack.RangedAttacking;
import sk.stuba.fiit.projectiles.Projectile;

import java.util.Collection;

public class DirectionRangedAttackingStrategy implements RangedAttacking {
    @Override
    public void attack(Vector2 direction, Collection<Projectile> projectileEnvironment, Weapon weapon) {
        weapon.shoot(direction, projectileEnvironment);
    }

    @Override
    public void attack(Damageable target, Collection<Projectile> projectileEnvironment, Weapon weapon) { }
}
