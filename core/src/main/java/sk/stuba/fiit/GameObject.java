package sk.stuba.fiit;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class GameObject implements Disposable {
    private String name;
    private String description;
    private Texture texture;
    private SpriteExtended sprite;
    private Vector2 position;
    private Vector2 origin;
    private float width;
    private float height;
    private float rotation;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Texture getTexture() { return texture; }
    public void setTexture(Texture texture) { this.texture = texture; }

    public SpriteExtended getSprite() { return sprite; }
    public void setSprite(SpriteExtended sprite) { this.sprite = sprite; }

    public Vector2 getPosition() { return position; }
    public void setPosition(Vector2 position) {
        this.position = position;
        if (!MyGame.TESTMODE) {
            if (sprite != null) {
                sprite.setPosition(position.x, position.y);
            }
        }
    }
    public void setPosition(float x, float y) {
        position.x = x;
        position.y = y;
        if (!MyGame.TESTMODE) {
            if (sprite != null) {
                sprite.setPosition(x, y);
            }
        }
    }

    public Vector2 getOrigin() { return origin; }
    public void setOrigin(Vector2 origin) {
        this.origin = origin;
        if (!MyGame.TESTMODE) {
            sprite.setOrigin(origin.x, origin.y);
        }
    }
    public void setOrigin(float x, float y) {
        origin.x = x;
        origin.y = y;
        if (!MyGame.TESTMODE) {
            sprite.setOrigin(x, y);
        }
    }

    public float getWidth() {
        return width;
    }
    public float getHeight() {
        return height;
    }
    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
        if (!MyGame.TESTMODE) {
            if (sprite != null) {
                sprite.setSize(width, height);
            }
        }
    }

    public float getRotation() { return rotation; }
    public void setRotation(float rotation) {
        this.rotation = rotation;
        if (!MyGame.TESTMODE) {
            sprite.setRotation(rotation);
        }
    }

    public void translate(Vector2 translation) {
        position.add(translation);
        if (!MyGame.TESTMODE) {
            if (sprite != null) {
                sprite.translate(translation);
            }
        }
    }

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

    @Override
    public void dispose() {
        texture.dispose();
    }
}
