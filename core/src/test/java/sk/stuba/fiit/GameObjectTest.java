package sk.stuba.fiit;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameObjectTest {

    private SpriteExtended spriteExtended;
    private GameObject gameObject;

    @BeforeEach
    public void setUp() {
        //spriteExtended = mock(SpriteExtended.class);
        //spriteExtended = mock(SpriteExtended.class);
        gameObject = new GameObject("TestName", "TestDescription", null);
    }

    @Test
    public void testGettersAfterInitialization() {
        assertEquals("TestName", gameObject.getName());
        assertEquals("TestDescription", gameObject.getDescription());
        assertEquals(spriteExtended, gameObject.getSprite());
        assertNotNull(gameObject.getSprite());
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
    public void testConstructorWithNullTexture() {
        GameObject nullTextureObject = new GameObject("Name", "Desc", (Texture) null);
        assertNull(nullTextureObject.getTexture());
        assertNull(nullTextureObject.getSprite());
    }
}
