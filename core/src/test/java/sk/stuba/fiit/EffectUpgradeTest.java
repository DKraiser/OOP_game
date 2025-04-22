package sk.stuba.fiit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class EffectUpgradeTest {
    EffectUpgrade upgrade;

    @BeforeEach
    public void setUp() {
        upgrade = new EffectUpgrade("Test", new int[] {1, 2, 3});
    }

    @Test
    public void testGetName() {
        assertEquals("Test", upgrade.getName());
    }

    @Test
    public void testGetLevel() {
        assertEquals(0, upgrade.getLevel());
    }

    @Test
    public void testGetUpgrade() {
        assertNull(upgrade.getUpgrade());
    }

    @Test
    public void testGetCurrentCost() {
        int[] expected = new int[] {1, 2, 3};
        for (int i = 0; i < 3; i++) {
            assertEquals(expected[i], upgrade.getCurrentCost());
            upgrade.setLevel(upgrade.getLevel() + 1);
        }
    }

    @Test
    public void testSetName() {
        upgrade.setName("Test1");
    }

    @Test
    public void testSetLevel() {
        upgrade.setLevel(2);
        assertEquals(2, upgrade.getLevel());
    }

    @Test
    public void testSetUpgrade() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("1");
            }
        };

        upgrade.setUpgrade(r);

        assertEquals(r, upgrade.getUpgrade());
    }
}
