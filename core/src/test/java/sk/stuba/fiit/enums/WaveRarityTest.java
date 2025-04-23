package sk.stuba.fiit.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WaveRarityTest {
    @Test
    public void testWaveRarity() {
        assertEquals(0, WaveRarity.LEGENDARY.ordinal());
        assertEquals(1, WaveRarity.EPIC.ordinal());
        assertEquals(2, WaveRarity.RARE.ordinal());
        assertEquals(3, WaveRarity.UNCOMMON.ordinal());
        assertEquals(4, WaveRarity.COMMON.ordinal());
    }
}
