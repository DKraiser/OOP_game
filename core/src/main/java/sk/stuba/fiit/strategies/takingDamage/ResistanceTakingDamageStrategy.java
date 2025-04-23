package sk.stuba.fiit.strategies.takingDamage;

import sk.stuba.fiit.interfaces.Damageable;
import sk.stuba.fiit.interfaces.TakingDamageStrategy;

import java.util.Random;

/**
 * A strategy for handling damage with a resistance factor that may prevent damage based on a random chance.
 * This strategy wraps another taking damage strategy and adds a resistance factor that reduces the likelihood of taking damage.
 *
 * The damage will be taken if a randomly generated number is below the resistance factor threshold.
 *
 * @see TakingDamageStrategy
 */
public class ResistanceTakingDamageStrategy implements TakingDamageStrategy {
    private TakingDamageStrategy takingDamageStrategyWrapped;
    private Random random;
    private int resistanceFactor;

    /**
     * Applies damage to the damageable entity, but only if the random check is above the resistance threshold.
     * The damage is processed by the wrapped strategy only if it passes the resistance check.
     *
     * @param damage the amount of damage to inflict
     * @param damageable the entity that takes damage
     */
    @Override
    public void takeDamage(int damage, Damageable damageable) {
        if (random.nextInt(0, 100) >= resistanceFactor) {
            takingDamageStrategyWrapped.takeDamage(damage, damageable);
        }
    }

    /**
     * Constructs a new resistance damage-taking strategy with a given resistance factor.
     *
     * @param takingDamageStrategyWrapped the strategy to wrap and use for applying damage
     * @param resistanceFactor the resistance factor (0-100) that determines the chance of avoiding damage
     */
    public ResistanceTakingDamageStrategy(TakingDamageStrategy takingDamageStrategyWrapped, int resistanceFactor) {
        this.takingDamageStrategyWrapped = takingDamageStrategyWrapped;
        this.resistanceFactor = resistanceFactor;
        random = new Random();
        random.setSeed(System.currentTimeMillis());
    }

    /**
     * Sets a custom random number generator.
     * This allows for controlling randomness, such as for testing or simulation purposes.
     *
     * @param random the custom random number generator
     */
    public void setRandom(Random random) {
        this.random = random;
    }
}
