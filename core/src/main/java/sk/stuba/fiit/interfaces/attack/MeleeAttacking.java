package sk.stuba.fiit.interfaces.attack;

import sk.stuba.fiit.interfaces.Damageable;

public interface MeleeAttacking {
    void attack(Damageable target, int damage);
}
