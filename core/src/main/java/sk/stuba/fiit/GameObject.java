package sk.stuba.fiit;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Disposable;

public class GameObject implements Disposable {
    private String name;
    private String description;
    private Texture texture;
    private SpriteExtended sprite;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Texture getTexture() { return texture; }
    public void setTexture(Texture texture) { this.texture = texture; }

    public SpriteExtended getSprite() { return sprite; }
    public void setSprite(SpriteExtended sprite) { this.sprite = sprite; }

    public GameObject(String name, String description, Texture texture) {
        this.name = name;
        this.description = description;
        this.texture = texture;
        this.sprite = texture != null ? new SpriteExtended(texture) : null;
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
