package sk.stuba.fiit.effects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.stuba.fiit.entities.Entity;

import static org.junit.jupiter.api.Assertions.*;

public class EffectTest {
    Entity target;
    Effect effect;

    @BeforeEach
    public void setUp() {
        target = new Entity("", "", null, 1, 2) {
            @Override
            public void onCollision(Entity collisionEntity) {

            }

            @Override
            public void takeDamage(int damage) {

            }

            @Override
            public void die() {

            }
        };
        effect = new Effect("Test", "Test", 2, true, 5, target ) {
            public boolean effectWasRemoved = false;

            @Override
            public Effect clone() { return null; }

            @Override
            public void applyEffect() { }

            @Override
            public void removeEffect() { effectWasRemoved = true; }
        };
    }

    @Test
    public void testGetName() {
        assertEquals("Test", effect.getName());
    }

    @Test
    public void testGetDescription() {
        assertEquals("Test", effect.getDescription());
    }

    @Test
    public void testGetLevel() {
        assertEquals(2, effect.getLevel());
    }

    @Test
    public void testIsFinite() {
        assertTrue(effect.isFinite());
    }

    @Test
    public void testGetRemainingTime() {
        assertEquals(5, effect.getRemainingTime());
    }

    @Test
    public void testGetDuration() {
        assertEquals(5, effect.getDuration());
    }

    @Test
    public void testGetTarget() {
        assertEquals(target, effect.getTarget());
    }

    @Test
    public void testSetName() {
        effect.setName("Test1");
        assertEquals("Test1", effect.getName());
    }

    @Test
    public void testSetDescription() {
        effect.setDescription("Test2");
        assertEquals("Test2", effect.getDescription());
    }

    @Test
    public void testSetLevel() {
        effect.setLevel(3);
        assertEquals(3, effect.getLevel());
    }

    @Test
    public void testSetFinite() {
        effect.setFinite(false);
        assertFalse(effect.isFinite());
    }

    @Test
    public void testSetRemainingTime() {
        effect.setRemainingTime(2);
        assertEquals(2, effect.getRemainingTime());
    }

    @Test
    public void testSetDuration() {
        effect.setDuration(6);
        assertEquals(6, effect.getDuration());
    }

    @Test
    public void testSetTarget() {
        Entity newTarget = new Entity("nt", "nt", null, 20, 20) {
            @Override
            public void onCollision(Entity collisionEntity) {

            }

            @Override
            public void takeDamage(int damage) {

            }

            @Override
            public void die() {

            }
        };
        effect.setTarget(newTarget);
        assertEquals(newTarget, effect.getTarget());
    }

    @Test
    public void testSetInitialValue() {
        effect.setInitialValue(1);
        assertEquals(1, effect.getInitialValue());
    }

    @Test
    public void testTickAndRemove() throws NoSuchFieldException, IllegalAccessException {
        effect.tick(5);
        assertTrue(effect.getClass().getField("effectWasRemoved").getBoolean(effect));
    }
}
