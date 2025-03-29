package sk.stuba.fiit.factories;

import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.projectiles.Projectile;

public abstract class WeaponFactory implements Factory <Weapon> {
    protected Projectile playerProjectileTemplate;
    protected PlayerProjectileFactory playerProjectileFactory;
    protected Weapon playerWeapon;
}
