package sk.stuba.fiit;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.exceptions.InvalidParameterInitializationException;

import java.io.Serializable;

/**
 * Represents a collision detection component using a circular collider.
 * This class provides methods to check for overlaps, clone the collider, and manipulate its position.
 */
public class Collider implements Cloneable, Serializable {
    private Circle circleCollider;

    /**
     * Gets the circle collider associated with this {@link Collider}.
     *
     * @return the {@link Circle} representing the collider
     */
    public Circle getCircleCollider() {
        return circleCollider;
    }

    /**
     * Sets the circle collider for this {@link Collider}.
     *
     * @param circleCollider the {@link Circle} to set as the collider
     */
    public void setCircleCollider(Circle circleCollider) {
        this.circleCollider = circleCollider;
    }

    /**
     * Constructs a new {@link Collider} using the provided figure.
     * The figure must be of type {@link Circle}.
     *
     * @param colliderFigure the collision figure to use (must be an instance of {@link Circle})
     * @throws InvalidParameterInitializationException if the provided figure is not a {@link Circle}
     */
    public Collider(Object colliderFigure) {
        if (colliderFigure instanceof Circle) {
            circleCollider = (Circle) colliderFigure;
        } else {
            throw new InvalidParameterInitializationException();
        }
    }

    /**
     * Checks if this collider overlaps with another collider.
     *
     * @param targetCollider the target {@link Collider} to check for overlap
     * @return {@code true} if the colliders overlap, {@code false} otherwise
     */
    public boolean overlaps(Collider targetCollider) {
        if (this.circleCollider != null && targetCollider.getCircleCollider() != null) {
            return this.circleCollider.overlaps(targetCollider.getCircleCollider());
        }
        return false;
    }

    /**
     * Creates a deep clone of this {@link Collider}, copying its circle collider.
     *
     * @return a cloned {@link Collider} object
     * @throws RuntimeException if there is no collider figure to clone
     */
    @Override
    public Collider clone() {
        if (circleCollider != null) {
            Collider clone = new Collider(new Circle(circleCollider));
            clone.getCircleCollider().setPosition(new Vector2(circleCollider.x, circleCollider.y));
            return clone;
        } else {
            throw new RuntimeException("There is no collision figure in Collider to clone");
        }
    }

    /**
     * Sets the position of the collider.
     *
     * @param position the new {@link Vector2} position to set
     */
    public void setPosition(Vector2 position) {
        if (circleCollider != null) {
            circleCollider.setPosition(position);
        }
    }

    /**
     * Moves the collider by the given translation vector.
     *
     * @param translation the {@link Vector2} translation to apply to the collider's position
     */
    public void move(Vector2 translation) {
        if (circleCollider != null) {
            circleCollider.setPosition(new Vector2(circleCollider.x + translation.x, circleCollider.y + translation.y));
        }
    }

    /**
     * Checks if this collider is equal to another object. Two colliders are considered equal if they have the same circle collider.
     *
     * @param o the object to compare to
     * @return {@code true} if the colliders are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Collider other = (Collider) o;
        return getCircleCollider().equals(other.getCircleCollider());
    }

    /**
     * Returns the hash code of this {@link Collider}. The hash code is based on the circle collider's hash code.
     *
     * @return the hash code of this {@link Collider}
     */
    @Override
    public int hashCode() {
        return circleCollider != null ? circleCollider.hashCode() : 0;
    }
}
