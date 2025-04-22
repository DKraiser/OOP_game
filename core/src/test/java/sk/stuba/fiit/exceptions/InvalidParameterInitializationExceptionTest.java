package sk.stuba.fiit.exceptions;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.Test;
import sk.stuba.fiit.Collider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InvalidParameterInitializationExceptionTest {
    @Test
    public void testThrow() {
        assertThrows(InvalidParameterInitializationException.class, () -> new Collider(new Vector2(0, 0)));
    }

    @Test
    public void testGetMessage() {
        assertEquals("Check your init parameters", new InvalidParameterInitializationException().getMessage());
    }
}
