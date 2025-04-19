package sk.stuba.fiit.interfaces.attack;

import sk.stuba.fiit.interfaces.Damageable;

import java.io.Serializable;

public interface MeleeAttackingStrategy extends Serializable {
    void attack(Damageable target, int damage);
}
