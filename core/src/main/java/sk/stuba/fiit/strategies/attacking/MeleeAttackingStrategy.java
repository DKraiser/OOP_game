package sk.stuba.fiit.strategies.attacking;

import sk.stuba.fiit.interfaces.Damageable;

public class MeleeAttackingStrategy implements sk.stuba.fiit.interfaces.attack.MeleeAttackingStrategy {

    @Override
    public void attack(Damageable target, int damage) {
        target.takeDamage(damage);
    }
}
