package sk.stuba.fiit.strategies.takingDamage;

import sk.stuba.fiit.entities.Entity;
import sk.stuba.fiit.interfaces.Damageable;
import sk.stuba.fiit.interfaces.TakingDamageStrategy;

import static java.lang.Math.max;

public class StandartTakingDamageStrategy implements TakingDamageStrategy {
    @Override
    public void takeDamage(int damage, Damageable damageable) {
        ((Entity)damageable).setHealth(max(((Entity)damageable).getHealth() - damage, 0));
    }
}
