package sk.stuba.fiit.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScreenTypeTest {
    @Test
    public void testWaveRarity() {
        assertEquals(0, ScreenType.GAME.ordinal());
        assertEquals(1, ScreenType.SHOP.ordinal());
        assertEquals(2, ScreenType.RESTART.ordinal());
    }
}
