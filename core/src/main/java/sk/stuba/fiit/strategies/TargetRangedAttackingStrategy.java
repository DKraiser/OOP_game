package sk.stuba.fiit.strategies;

import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.entities.Entity;
import sk.stuba.fiit.interfaces.Damageable;
import sk.stuba.fiit.interfaces.attack.RangedAttacking;
import sk.stuba.fiit.projectiles.Projectile;

import java.util.Collection;

public class TargetRangedAttackingStrategy implements RangedAttacking {

    @Override
    public void attack(Vector2 direction, Collection<Projectile> projectileEnvironment, Weapon weapon) { }

    @Override
    public void attack(Damageable target, Collection<Projectile> projectileEnvironment, Weapon weapon) {
        Vector2 direction = new Vector2(((Entity) target).getSprite().getPosition().x, ((Entity) target).getSprite().getPosition().y);
        direction.add(((Entity) target).getSprite().getWidth() / 2, ((Entity) target).getSprite().getHeight() / 2);
    }

}
