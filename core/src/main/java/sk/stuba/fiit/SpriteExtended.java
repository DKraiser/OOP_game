package sk.stuba.fiit;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.interfaces.Effectable;

import java.io.Serializable;

/**
 * An extension of the {@link Sprite} class that adds support for a {@link Vector2}-based position
 * and synchronizes it with the underlying sprite's position.
 *
 * <p>This class is also {@link Serializable}, making it suitable for use in contexts where
 * objects need to be saved or transferred.
 */
public class SpriteExtended extends Sprite implements Serializable {
    private Vector2 position;

    /**
     * Gets the current position of the sprite as a {@link Vector2}.
     *
     * @return the current position
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Sets the position of the sprite using a {@link Vector2} and synchronizes it
     * with the superclass position values.
     *
     * @param position the new position
     */
    public void setPosition(Vector2 position) {
        this.position = position;
        super.setPosition(position.x, position.y);
    }

    /**
     * Sets the position of the sprite using x and y coordinates.
     * Internally updates the {@code Vector2} position.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    @Override
    public void setPosition(float x, float y) {
        setPosition(new Vector2(x, y));
    }

    /**
     * Translates the sprite's position by a given {@link Vector2}.
     *
     * @param translation the amount to move the sprite
     */
    public void translate(Vector2 translation) {
        setPosition(new Vector2(position.x + translation.x, position.y + translation.y));
    }

    /**
     * Translates the sprite's position by the specified x and y values.
     *
     * @param x the amount to move in the x-direction
     * @param y the amount to move in the y-direction
     */
    @Override
    public void translate(float x, float y) {
        translate(new Vector2(x, y));
    }

    /**
     * Constructs a new {@code SpriteExtended} from a {@link Texture}.
     * Initializes the internal position based on the sprite's current location.
     *
     * @param texture the texture for the sprite
     */
    public SpriteExtended(Texture texture) {
        super(texture);
        this.position = new Vector2(getX(), getY());
    }

    /**
     * Constructs a new {@code SpriteExtended} by copying an existing {@link Sprite}.
     * Only copies if {@code MyGame.TESTMODE} is {@code false}.
     *
     * @param sprite the sprite to copy
     */
    public SpriteExtended(Sprite sprite) {
        if (!MyGame.TESTMODE) {
            set(sprite);
        }
    }
}
