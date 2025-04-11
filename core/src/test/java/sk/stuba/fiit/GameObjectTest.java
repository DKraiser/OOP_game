package sk.stuba.fiit;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameObjectTest {

    private SpriteExtended spriteExtended;
    private GameObject gameObject;

    @BeforeAll
    public static void init() {
        new HeadlessApplication(new ApplicationAdapter() {});
    }

    @BeforeEach
    public void setUp() {
        gameObject = new GameObject("TestName", "TestDescription", null);
    }

    @Test
    public void testGettersAfterInitialization() {
        assertEquals("TestName", gameObject.getName());
        assertEquals("TestDescription", gameObject.getDescription());
        assertEquals(new Vector2(0, 0), gameObject.getPosition());
        assertEquals(new Vector2(0, 0), gameObject.getOrigin());
        assertEquals(0, gameObject.getRotation());
        assertEquals(1, gameObject.getWidth());
        assertEquals(1, gameObject.getHeight());
    }

    @Test
    public void testSetName() {
        gameObject.setName("NewName");
        assertEquals("NewName", gameObject.getName());
    }

    @Test
    public void testSetDescription() {
        gameObject.setDescription("NewDescription");
        assertEquals("NewDescription", gameObject.getDescription());
    }

    @Test
    public void testSetSprite() {
        SpriteExtended sprite = null;
        gameObject.setSprite(sprite);
        assertEquals(sprite, gameObject.getSprite());
    }

    @Test
    public void testSetVectorPosition() {
        gameObject.setPosition(new Vector2(2, 2));
        assertEquals(new Vector2(2, 2), gameObject.getPosition());
    }

    @Test
    public void testSetFloatPosition() {
        gameObject.setPosition(2, 2);
        assertEquals(new Vector2(2, 2), gameObject.getPosition());
    }

    @Test
    public void testSetVectorOrigin() {
        gameObject.setOrigin(new Vector2(2, 2));
        assertEquals(new Vector2(2, 2), gameObject.getOrigin());
    }

    @Test
    public void testSetFloatOrigin() {
        gameObject.setOrigin(2, 2);
        assertEquals(new Vector2(2, 2), gameObject.getOrigin());
    }

    @Test
    public void testSetRotation() {
        gameObject.setRotation(90);
        assertEquals(90, gameObject.getRotation());
    }

    @Test
    public void testSetSize() {
        gameObject.setSize(2, 2);
        assertEquals(2, gameObject.getWidth());
        assertEquals(2, gameObject.getHeight());
    }

    @Test
    public void testTranslation() {
        gameObject.translate(new Vector2(3, 3));
        assertEquals(new Vector2(3, 3), gameObject.getPosition());
    }
}
