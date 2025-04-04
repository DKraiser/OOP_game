package sk.stuba.fiit.strategies;

import sk.stuba.fiit.interfaces.Damageable;
import sk.stuba.fiit.interfaces.attack.MeleeAttacking;

public class MeleeAttackingStrategy implements MeleeAttacking {

    @Override
    public void attack(Damageable target, int damage) {
        target.takeDamage(damage);
    }
}
