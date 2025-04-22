package sk.stuba.fiit;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.interfaces.Effectable;

import java.io.Serializable;

public class SpriteExtended extends Sprite implements Serializable {
    private Vector2 position;

    public Vector2 getPosition() { return position; }
    public void setPosition(Vector2 position) {
        this.position = position;
        super.setPosition(position.x, position.y);
    }

    @Override
    public void setPosition(float x, float y) {
        setPosition(new Vector2(x, y));
    }

    public void translate(Vector2 translation) {
        setPosition(new Vector2(position.x + translation.x, position.y + translation.y));
    }

    @Override
    public void translate(float x, float y) {
        translate(new Vector2(x, y));
    }

    public SpriteExtended(Texture texture) {
        super(texture);
        this.position = new Vector2(getX(), getY());
    }

    public SpriteExtended(Sprite sprite) {
        if (!MyGame.TESTMODE) {
            set(sprite);
        }
    }
}
