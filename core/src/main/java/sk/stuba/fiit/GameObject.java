package sk.stuba.fiit;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import java.io.Serializable;

/**
 * Represents a basic game object with position, size, texture, and sprite information.
 * Implements {@link Disposable} to clean up texture resources.
 */
public class GameObject implements Disposable {

    private String name;
    private String description;
    private transient Texture texture;
    private transient SpriteExtended sprite;
    private String texturePath;
    private Vector2 position;
    private Vector2 origin;
    private float width;
    private float height;
    private float rotation;

    /**
     * Gets the object's name.
     *
     * @return the name of the object
     */
    public String getName() { return name; }

    /**
     * Sets the object's name.
     *
     * @param name the new name
     */
    public void setName(String name) { this.name = name; }

    /**
     * Gets the object's description.
     *
     * @return the description
     */
    public String getDescription() { return description; }

    /**
     * Sets the object's description.
     *
     * @param description the new description
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * Gets the texture of the object.
     *
     * @return the texture
     */
    public Texture getTexture() { return texture; }

    /**
     * Sets the texture of the object.
     *
     * @param texture the new texture
     */
    public void setTexture(Texture texture) { this.texture = texture; }

    /**
     * Gets the extended sprite associated with the object.
     *
     * @return the sprite
     */
    public SpriteExtended getSprite() { return sprite; }

    /**
     * Sets the extended sprite.
     *
     * @param sprite the new sprite
     */
    public void setSprite(SpriteExtended sprite) { this.sprite = sprite; }

    /**
     * Gets the position of the object.
     *
     * @return position as a {@link Vector2}
     */
    public Vector2 getPosition() { return position; }

    /**
     * Sets the position of the object.
     *
     * @param position new position
     */
    public void setPosition(Vector2 position) {
        this.position = position;
        if (!MyGame.TESTMODE && sprite != null) {
            sprite.setPosition(position.x, position.y);
        }
    }

    /**
     * Sets the position using X and Y values.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public void setPosition(float x, float y) {
        position.x = x;
        position.y = y;
        if (!MyGame.TESTMODE && sprite != null) {
            sprite.setPosition(x, y);
        }
    }

    /**
     * Gets the origin point of the object.
     *
     * @return origin as a {@link Vector2}
     */
    public Vector2 getOrigin() { return origin; }

    /**
     * Sets the origin using a {@link Vector2}.
     *
     * @param origin new origin
     */
    public void setOrigin(Vector2 origin) {
        this.origin = origin;
        if (!MyGame.TESTMODE && sprite != null) {
            sprite.setOrigin(origin.x, origin.y);
        }
    }

    /**
     * Sets the origin using X and Y values.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public void setOrigin(float x, float y) {
        origin.x = x;
        origin.y = y;
        if (!MyGame.TESTMODE && sprite != null) {
            sprite.setOrigin(x, y);
        }
    }

    /**
     * Gets the width of the object.
     *
     * @return the width
     */
    public float getWidth() { return width; }

    /**
     * Gets the height of the object.
     *
     * @return the height
     */
    public float getHeight() { return height; }

    /**
     * Sets the object's width and height.
     *
     * @param width new width
     * @param height new height
     */
    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
        if (!MyGame.TESTMODE && sprite != null) {
            sprite.setSize(width, height);
        }
    }

    /**
     * Gets the object's rotation in degrees.
     *
     * @return the rotation
     */
    public float getRotation() { return rotation; }

    /**
     * Sets the rotation of the object in degrees.
     *
     * @param rotation new rotation
     */
    public void setRotation(float rotation) {
        this.rotation = rotation;
        if (!MyGame.TESTMODE && sprite != null) {
            sprite.setRotation(rotation);
        }
    }

    /**
     * Translates the object's position by the given vector.
     *
     * @param translation translation vector
     */
    public void translate(Vector2 translation) {
        position.add(translation);
        if (!MyGame.TESTMODE && sprite != null) {
            sprite.translate(translation);
        }
    }

    /**
     * Constructs a new {@code GameObject} with given name, description, and texture.
     *
     * @param name        the object's name
     * @param description the object's description
     * @param texture     the texture for visual representation
     */
    public GameObject(String name, String description, Texture texture) {
        this.name = name;
        this.description = description;
        this.texture = texture;
        this.sprite = texture != null ? new SpriteExtended(texture) : null;
        this.position = new Vector2(0, 0);
        this.origin = new Vector2(0, 0);
        this.width = 1;
        this.height = 1;
        this.rotation = 0;
    }

    /**
     * Disposes of the object's texture to free GPU memory.
     */
    @Override
    public void dispose() {
        texture.dispose();
    }
}
