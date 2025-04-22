package sk.stuba.fiit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.stuba.fiit.effects.Effect;
import sk.stuba.fiit.entities.Entity;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EffectHandlerTest {
    EffectHandler handler;
    Entity target;
    Effect effect;

    @BeforeEach
    public void setUp() {
        handler = new EffectHandler();
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
            public boolean effectWasApplied = false;

            @Override
            public Effect clone() { return new Effect(getName(), getDescription(), getLevel(), isFinite(), getDuration(), getRemainingTime(), null) {
                @Override
                public Effect clone() {
                    return null;
                }

                @Override
                public void applyEffect() {

                }

                @Override
                public void removeEffect() {

                }
            }; }

            @Override
            public void applyEffect() { effectWasApplied = true; }

            @Override
            public void removeEffect() { effectWasRemoved = true; }
        };
    }

    @Test
    public void testGetEffects() {
        assertEquals(new ArrayList<Effect>(), handler.getEffects());
    }

    @Test
    public void testGetEffect() {
        handler.takeEffect(effect);
        assertEquals(effect, handler.getEffect(effect.getClass()));
        Effect effect1 = new Effect("Test1", "Test1", 2, true, 5, target ) {
            public boolean effectWasRemoved = false;
            public boolean effectWasApplied = false;

            @Override
            public Effect clone() { return null; }

            @Override
            public void applyEffect() { effectWasApplied = true; }

            @Override
            public void removeEffect() { effectWasRemoved = true; }
        };
        handler.takeEffect(effect1);
        assertEquals(effect1, handler.getEffect(effect1.getClass()));
    }

    @Test
    public void testTakeEffect() {
        handler.takeEffect(effect);
        assertTrue(handler.getEffects().contains(effect));

    }

    @Test
    public void testEffects() {
        Effect effect1 = new Effect("Test1", "Test1", 2, true, 5, target ) {
            public boolean effectWasRemoved = false;
            public boolean effectWasApplied = false;

            @Override
            public Effect clone() { return null; }

            @Override
            public void applyEffect() { effectWasApplied = true; }

            @Override
            public void removeEffect() { effectWasRemoved = true; }
        };

        ArrayList<Effect> effects = new ArrayList<>();
        effects.add(effect);
        effects.add(effect1);

        handler.takeEffect(effects);
        assertEquals(effects, handler.getEffects());
    }

    @Test
    public void testUpdateEffects() {
        handler.takeEffect(effect);
        handler.updateEffects(1);
        assertEquals(4, handler.getEffect(effect.getClass()).getRemainingTime());
    }

    @Test
    public void testClone() {
        handler.takeEffect(effect);
        EffectHandler clone = handler.clone();

        assertEquals(effect.getName(), clone.getEffects().getLast().getName());
        assertEquals(effect.getDescription(), clone.getEffects().getLast().getDescription());
        assertEquals(effect.isFinite(), clone.getEffects().getLast().isFinite());
        assertEquals(effect.getDuration(), clone.getEffects().getLast().getDuration());
        assertEquals(effect.getRemainingTime(), clone.getEffects().getLast().getRemainingTime());
        assertEquals(effect.getLevel(), clone.getEffects().getLast().getLevel());
    }
}
