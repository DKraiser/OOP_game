package sk.stuba.fiit;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Collider implements Cloneable{
    private Circle circleCollider;
    private Rectangle rectCollider;

    public Rectangle getRectCollider() {
        return rectCollider;
    }

    public void setRectCollider(Rectangle rectCollider) {
        this.rectCollider = rectCollider;
    }

    public Circle getCircleCollider() {
        return circleCollider;
    }

    public void setCircleCollider(Circle circleCollider) {
        this.circleCollider = circleCollider;
    }

    public Collider(Object colliderFigure) {
        if (colliderFigure instanceof Circle) {
            circleCollider = (Circle) colliderFigure;
            rectCollider = null;
        }
        else if (colliderFigure instanceof Rectangle) {
            rectCollider = (Rectangle) colliderFigure;
            circleCollider = null;
        }
        else throw new IllegalArgumentException();
    }

    public boolean overlaps(Collider targetCollider) {
        if (this.circleCollider != null && targetCollider.getCircleCollider() != null) {
            return this.circleCollider.overlaps(targetCollider.getCircleCollider());
        }

        if (this.rectCollider != null && targetCollider.getRectCollider() != null) {
            return this.rectCollider.overlaps(targetCollider.getRectCollider());
        }

        if (this.rectCollider != null && targetCollider.getCircleCollider() != null) {
            return isCircleRectOverlap(this.circleCollider, this.rectCollider);
        }

        if (this.circleCollider != null && targetCollider.getRectCollider() != null) {
            return isCircleRectOverlap(targetCollider.getCircleCollider(), targetCollider.getRectCollider());
        }
        return false;
    }

    private boolean isCircleRectOverlap(Circle circle, Rectangle rect) {
        float nearestX = clamp(circle.x, rect.x, rect.x + rect.width);
        float nearestY = clamp(circle.y, rect.y, rect.y + rect.height);

        float dx = nearestX - circle.x;
        float dy = nearestY - circle.y;

        float distanceSquared = dx * dx + dy * dy;
        return distanceSquared <= (circle.radius * circle.radius);
    }

    public void move(Vector2 translation) {
        if (rectCollider != null) {
            rectCollider.setPosition(new Vector2(rectCollider.getX() + translation.x, rectCollider.getY() + translation.y));
        }
        if (circleCollider != null) {
            circleCollider.setPosition(new Vector2(circleCollider.x + translation.x, circleCollider.y + translation.y));
        }
    }

    @Override
    public Collider clone() {
        if (rectCollider != null) {
            Collider clone = new Collider(new Rectangle(rectCollider));
            clone.getRectCollider().setPosition(new Vector2(rectCollider.x, rectCollider.y));
            return clone;
        }
        if (circleCollider != null) {
            Collider clone = new Collider(new Circle(circleCollider));
            clone.getCircleCollider().setPosition(new Vector2(circleCollider.x, circleCollider.y));
            return clone;
        }
        return null;
    }

    private float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    public void setPosition(Vector2 position) {
        if (rectCollider != null) {
            rectCollider.setPosition(position);
        }
        if (circleCollider != null) {
            circleCollider.setPosition(position);
        }
    }
}
