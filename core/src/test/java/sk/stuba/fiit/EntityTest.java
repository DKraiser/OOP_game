package sk.stuba.fiit;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.stuba.fiit.entities.Entity;
import sk.stuba.fiit.exceptions.InvalidParameterInitializationException;

import static org.junit.jupiter.api.Assertions.*;

public class EntityTest {

    private Entity entity;

    @BeforeEach
    public void setUp() {
        Texture mockTexture = new Texture("empty.png");

        entity = new Entity("EntityName", "EntityDescription", mockTexture, 100, 100) {
            @Override
            public void die() {}

            @Override
            public void takeDamage(int damage) {}
        };
    }

    @Test
    public void testConstructorValidValues() {
        assertEquals(100, entity.getHealth());
        assertEquals(100, entity.getMaxHealth());
    }

    @Test
    public void testConstructorInvalidHealth() {
        assertThrows(InvalidParameterInitializationException.class, () -> {
            new Entity("Invalid Entity", "Invalid Description", new Texture("empty.png"), 200, 100) {
                @Override
                public void die() {}

                @Override
                public void takeDamage(int damage) {}
            };
        });
    }

    @Test
    public void testConstructorInvalidMaxHealthNegative() {
        assertThrows(InvalidParameterInitializationException.class, () -> {
            new Entity("Invalid Entity", "Invalid Description", new Texture("empty.png"), 100, -1) {
                @Override
                public void die() {}

                @Override
                public void takeDamage(int damage) {}
            };
        });
    }

    @Test
    public void testConstructorInvalidHealthNegative() {
        assertThrows(InvalidParameterInitializationException.class, () -> {
            new Entity("Invalid Entity", "Invalid Description", new Texture("empty.png"), -1, 100) {
                @Override
                public void die() {}

                @Override
                public void takeDamage(int damage) {}
            };
        });
    }

    @Test
    public void testSetHealth() {
        entity.setHealth(50);
        assertEquals(50, entity.getHealth());
        assertThrows(IllegalArgumentException.class, () -> {
            entity.setHealth(-10);
        });
    }

    @Test
    public void testSetMaxHealth() {
        entity.setMaxHealth(150);
        assertEquals(150, entity.getMaxHealth());
        assertThrows(IllegalArgumentException.class, () -> {
            entity.setMaxHealth(-20);
        });
    }

    @Test
    public void testGetAndSetCollider() {
        Collider collider = new Collider(new Circle(0, 0, 1));
        entity.setCollider(collider);
        assertEquals(collider, entity.getCollider());
    }
}
