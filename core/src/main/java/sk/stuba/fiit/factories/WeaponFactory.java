package sk.stuba.fiit.factories;

import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.projectiles.Projectile;

public abstract class WeaponFactory implements Factory <Weapon> {
    protected Projectile projectileTemplate;
    protected PlayerProjectileFactory projectileFactory;
    protected Weapon weapon;
}
