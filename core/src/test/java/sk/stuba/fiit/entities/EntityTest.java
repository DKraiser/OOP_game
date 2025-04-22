package sk.stuba.fiit.entities;

import com.badlogic.gdx.math.Circle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.stuba.fiit.Collider;
import sk.stuba.fiit.exceptions.InvalidParameterInitializationException;

import static org.junit.jupiter.api.Assertions.*;

public class EntityTest {

    private Entity entity;

    @BeforeEach
    public void setUp() {
        entity = new Entity("EntityName", "EntityDescription", null, 100, 100) {
            public boolean collision = false;
            @Override
            public void die() {}

            @Override
            public void takeDamage(int damage) {}

            @Override
            public void onCollision(Entity collisionEntity) {
                collision = true;
            }
        };
        entity.setCollider(new Collider(new Circle(1,1,1)));
    }

    @Test
    public void testConstructorValidValues() {
        assertEquals(100, entity.getHealth());
        assertEquals(100, entity.getMaxHealth());
    }

    @Test
    public void testConstructorInvalidHealth() {
        assertThrows(InvalidParameterInitializationException.class, () -> {
            new Entity("Invalid Entity", "Invalid Description", null, 200, 100) {
                @Override
                public void die() {}

                @Override
                public void takeDamage(int damage) {}

                @Override
                public void onCollision(Entity collisionEntity) {}
            };
        });
    }

    @Test
    public void testConstructorInvalidMaxHealthNegative() {
        assertThrows(InvalidParameterInitializationException.class, () -> {
            new Entity("Invalid Entity", "Invalid Description", null, 100, -1) {
                @Override
                public void die() {}

                @Override
                public void takeDamage(int damage) {}

                @Override
                public void onCollision(Entity collisionEntity) {}
            };
        });
    }

    @Test
    public void testConstructorInvalidHealthNegative() {
        assertThrows(InvalidParameterInitializationException.class, () -> {
            new Entity("Invalid Entity", "Invalid Description", null, -1, 100) {
                @Override
                public void die() {}

                @Override
                public void takeDamage(int damage) {}

                @Override
                public void onCollision(Entity collisionEntity) {}
            };
        });
    }

    @Test
    public void testSetValidHealth() {
        entity.setHealth(50);
        assertEquals(50, entity.getHealth());
    }

    @Test
    public void testSetInvalidHealthNegative() {
        assertThrows(IllegalArgumentException.class, () -> entity.setHealth(-1));
    }

    @Test
    public void testSetInvalidHealthMoreThanMaxHealth() {
        entity.setMaxHealth(150);
        assertEquals(100, entity.getHealth());
    }

    @Test
    public void testSetValidMaxHealth() {
        entity.setMaxHealth(150);
        assertEquals(150, entity.getMaxHealth());
    }

    @Test
    public void testSetInvalidMaxHealth() {
        assertThrows(IllegalArgumentException.class, () -> entity.setMaxHealth(-1));
    }

    @Test
    public void testSetMaxHealthLowerThanHealth() {
        entity.setMaxHealth(100);
        entity.setHealth(50);
        entity.setMaxHealth(10);
        assertEquals(10, entity.getMaxHealth());
    }

    @Test
    public void testGetCollider() {
        assertNotNull(entity.getCollider());
    }

    @Test
    public void testSetCollider() {
        Collider collider = new Collider(new Circle(2,2,2));
        entity.setCollider(collider);
        assertEquals(collider, entity.getCollider());
    }

    @Test
    public void testIsAlive() {
        assertTrue(entity.isAlive());
    }

    @Test
    public void testSetAlive() {
        entity.setAlive(false);
        assertFalse(entity.isAlive());
    }

    @Test
    public void testCollision() throws NoSuchFieldException, IllegalAccessException {
        entity.onCollision(entity);
        assertTrue(entity.getClass().getField("collision").getBoolean(entity));
    }
}
