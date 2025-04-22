package sk.stuba.fiit;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.stuba.fiit.exceptions.InvalidParameterInitializationException;


import static org.junit.jupiter.api.Assertions.*;

public class ColliderTest {
    private Collider circleCollider;

    @BeforeEach
    public void setUp() {
        circleCollider = new Collider(new Circle(0, 0, 10));
    }

    @Test
    public void testGetCircleCollider() {
        assertEquals(new Collider(new Circle(0, 0, 10)), circleCollider);
    }

    @Test
    public void testSetCircleCollider() {
        circleCollider.setCircleCollider(new Circle(0, 0, 100));
        assertEquals(new Collider(new Circle(0, 0, 100)), circleCollider);
    }

    @Test
    public void testSetIllegalCollisionFigure() {
        assertThrows(InvalidParameterInitializationException.class, () -> new Collider(new Ellipse(0, 0, 100, 100)));
    }

    @Test
    public void testSetPosition() {
        Vector2 pos = new Vector2(50, 50);
        circleCollider.setPosition(pos.cpy());

        assertEquals(pos.x, circleCollider.getCircleCollider().x);
        assertEquals(pos.x, circleCollider.getCircleCollider().y);
    }

    @Test
    public void testMove() {
        Vector2 position = new Vector2(circleCollider.getCircleCollider().x, circleCollider.getCircleCollider().y);
        Vector2 translation = new Vector2(10, 10);
        circleCollider.move(translation);
        assertEquals(position.x + translation.x, circleCollider.getCircleCollider().x);
        assertEquals(position.x + translation.y, circleCollider.getCircleCollider().y);
    }

    @Test
    public void testClone() {
        Collider clone = circleCollider.clone();
        assertNotSame(circleCollider, clone);
        assertEquals(circleCollider, clone);
    }

    @Test
    public void testNotCloneable() {
        Collider badCollider = new Collider(new Circle(0, 0, 10));
        badCollider.setCircleCollider(null);
        assertThrows(RuntimeException.class, () -> badCollider.clone());
    }

    @Test
    public void testOverlaps() {
        Collider col = new Collider(new Circle(50, 50, 100));
        assertTrue(col.overlaps(circleCollider));
    }

    @Test
    public void testNotOverlaps() {
        Collider col = new Collider(new Circle(50, 50, 1));
        assertFalse(col.overlaps(circleCollider));
    }

    @Test
    public void testOverlapsWithBadCollider() {
        Collider col = new Collider(new Circle(50, 50, 1));
        col.setCircleCollider(null);
        assertFalse(col.overlaps(circleCollider));
    }
}
