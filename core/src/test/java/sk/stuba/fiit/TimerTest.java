package sk.stuba.fiit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TimerTest {
    Timer timer;
    @BeforeEach
    public void setUp() {
        timer = new Timer(3, 2);
    }

    @Test
    public void testGetPeriod() {
        assertEquals(3, timer.getPeriod());
    }

    @Test
    public void testSetPeriod() {
        timer.setPeriod(5);
        assertEquals(5, timer.getPeriod());
    }

    @Test
    public void testGetCurrent() {
        assertEquals(2, timer.getCurrent());
    }

    @Test
    public void testSetCurrent() {
        timer.setCurrent(4);
        assertEquals(4, timer.getCurrent());
    }

    @Test
    public void testIsElapsed() {
        assertFalse(timer.isElapsed());
    }

    @Test
    public void testSetElapsed() {
        timer.setElapsed();
        assertTrue(timer.isElapsed());
    }

    @Test
    public void testTickNotElapsed() {
        timer.tick(0.5f);
        assertEquals(2.5f, timer.getCurrent());
    }

    @Test
    public void testTickElapsed() {
        timer.tick(3);
        assertTrue(timer.isElapsed());
    }

    @Test
    public void testOnlyPeriodConstructor() {
        timer = new Timer(6);
        assertEquals(6, timer.getPeriod());
        assertEquals(0, timer.getCurrent());
    }

    @Test
    public void testClone() {
        Timer clone = timer.clone();
        assertNotSame(timer, clone);
        assertEquals(timer, clone);
    }
}
