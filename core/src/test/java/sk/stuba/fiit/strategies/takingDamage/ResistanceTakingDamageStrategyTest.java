package sk.stuba.fiit.strategies.takingDamage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import sk.stuba.fiit.entities.Entity;
import sk.stuba.fiit.interfaces.TakingDamageStrategy;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ResistanceTakingDamageStrategyTest {
    ResistanceTakingDamageStrategy takingDamageStrategy;
    Entity entity;

    @BeforeEach
    public void setUp() {
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
        takingDamageStrategy = new ResistanceTakingDamageStrategy(new StandartTakingDamageStrategy(), 5);
    }

    @Test
    public void testResistDamage() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Random rMock = Mockito.mock(Random.class);
        Mockito.when(rMock.nextInt(0, 100)).thenReturn(4);
        takingDamageStrategy.setRandom(rMock);
        entity.getClass().getMethod("setTakingDamage", TakingDamageStrategy.class).invoke(entity, takingDamageStrategy);
        entity.takeDamage(5);
        assertEquals(10, entity.getHealth());
    }

    @Test
    public void testTakeDamage() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Random rMock = Mockito.mock(Random.class);
        Mockito.when(rMock.nextInt(0, 100)).thenReturn(6);
        takingDamageStrategy.setRandom(rMock);
        entity.getClass().getMethod("setTakingDamage", TakingDamageStrategy.class).invoke(entity, takingDamageStrategy);
        entity.takeDamage(5);
        assertEquals(5, entity.getHealth());
    }
}
