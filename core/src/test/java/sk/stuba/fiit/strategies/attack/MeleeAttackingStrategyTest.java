package sk.stuba.fiit.strategies.attack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sk.stuba.fiit.interfaces.Damageable;
import sk.stuba.fiit.strategies.attacking.MeleeAttackingStrategy;

public class MeleeAttackingStrategyTest {
    MeleeAttackingStrategy meleeAttackingStrategy;
    Damageable damageable;

    @BeforeEach
    public void setUp() {
        meleeAttackingStrategy = new MeleeAttackingStrategy();
        damageable = Mockito.mock(Damageable.class);
    }

    @Test
    public void testAttack() {
        meleeAttackingStrategy.attack(damageable, 5);
        Mockito.verify(damageable).takeDamage(5);
    }
}
