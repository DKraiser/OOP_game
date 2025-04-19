package sk.stuba.fiit.interfaces.attack;

import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.interfaces.Damageable;
import sk.stuba.fiit.projectiles.Projectile;

import java.io.Serializable;
import java.util.Collection;

public interface RangedAttackingStrategy extends Serializable {
    void attack(Vector2 direction, Collection<Projectile> projectileEnvironment, Weapon weapon);
    void attack(Damageable target, Collection<Projectile> projectileEnvironment, Weapon weapon);
}
