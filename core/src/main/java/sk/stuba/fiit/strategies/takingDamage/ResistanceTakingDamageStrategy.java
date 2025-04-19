package sk.stuba.fiit.strategies.takingDamage;

import sk.stuba.fiit.interfaces.Damageable;
import sk.stuba.fiit.interfaces.TakingDamageStrategy;

import java.util.Random;

public class ResistanceTakingDamageStrategy implements TakingDamageStrategy {
    private TakingDamageStrategy takingDamageStrategyWrapped;
    private Random random;
    private int resistanceFactor;

    @Override
    public void takeDamage(int damage, Damageable damageable) {
        if (random.nextInt(0,100) >= resistanceFactor) {
            takingDamageStrategyWrapped.takeDamage(damage, damageable);
        }
    }

    public ResistanceTakingDamageStrategy(TakingDamageStrategy takingDamageStrategyWrapped, int resistanceFactor) {
        this.takingDamageStrategyWrapped = takingDamageStrategyWrapped;
        this.resistanceFactor = resistanceFactor;
        random = new Random();
        random.setSeed(System.currentTimeMillis());
    }
}
