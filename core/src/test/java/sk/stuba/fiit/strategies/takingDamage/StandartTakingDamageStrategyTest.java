package sk.stuba.fiit.strategies.takingDamage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.stuba.fiit.entities.Entity;
import sk.stuba.fiit.interfaces.TakingDamageStrategy;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StandartTakingDamageStrategyTest {
    TakingDamageStrategy takingDamageStrategy;
    Entity entity;

    @BeforeEach
    public void setUp() {
        takingDamageStrategy = new StandartTakingDamageStrategy();
        entity = new Entity("", "", null, 10, 10) {
            public TakingDamageStrategy takingDamageStrategy;

            @Override
            public void onCollision(Entity collisionEntity) {

            }

            @Override
            public void die() {

            }

            @Override
            public void takeDamage(int damage) {
                takingDamageStrategy.takeDamage(damage, entity);
            }

            public void setTakingDamage(TakingDamageStrategy takingDamageStrategy) {
                this.takingDamageStrategy = takingDamageStrategy;
            }
        };
    }

    @Test
    public void testTakeDamage() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        entity.getClass().getMethod("setTakingDamage", TakingDamageStrategy.class).invoke(entity, takingDamageStrategy);
        entity.takeDamage(5);
        assertEquals(5, entity.getHealth());
    }
}
