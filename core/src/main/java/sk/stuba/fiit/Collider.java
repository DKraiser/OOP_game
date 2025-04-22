package sk.stuba.fiit;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.exceptions.InvalidParameterInitializationException;

import java.io.Serializable;

public class Collider implements Cloneable, Serializable {
    private Circle circleCollider;

    public Circle getCircleCollider() {
        return circleCollider;
    }

    public void setCircleCollider(Circle circleCollider) {
        this.circleCollider = circleCollider;
    }

    public Collider(Object colliderFigure) {
        if (colliderFigure instanceof Circle) {
            circleCollider = (Circle) colliderFigure;
        }
        else throw new InvalidParameterInitializationException();
    }

    public boolean overlaps(Collider targetCollider) {
        if (this.circleCollider != null && targetCollider.getCircleCollider() != null) {
            return this.circleCollider.overlaps(targetCollider.getCircleCollider());
        }
        return false;
    }

    @Override
    public Collider clone() {
        if (circleCollider != null) {
            Collider clone = new Collider(new Circle(circleCollider));
            clone.getCircleCollider().setPosition(new Vector2(circleCollider.x, circleCollider.y));
            return clone;
        }
        else throw new RuntimeException("There is no collision figure in Collider to clone");
    }

    public void setPosition(Vector2 position) {
        if (circleCollider != null) {
            circleCollider.setPosition(position);
        }
    }

    public void move(Vector2 translation) {
        if (circleCollider != null) {
            circleCollider.setPosition(new Vector2(circleCollider.x + translation.x, circleCollider.y + translation.y));
        }
    }

    @Override
    public boolean equals(Object o) {
        Collider other = (Collider) o;

        return getCircleCollider().equals(other.getCircleCollider());
    }
}
