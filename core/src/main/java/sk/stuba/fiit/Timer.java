package sk.stuba.fiit;

import java.io.Serializable;

/**
 * Represents a simple timer that tracks elapsed time based on a defined period.
 * The timer can be updated using the {@link #tick(float)} method and will reset when the period is reached.
 * It also implements {@link Cloneable} and {@link Serializable}.
 */
public class Timer implements Cloneable, Serializable {
    private float current;
    private float period;
    private boolean elapsed;

    /**
     * Returns the current time elapsed since the last reset.
     *
     * @return the current elapsed time
     */
    public float getCurrent() {
        return current;
    }

    /**
     * Sets the current elapsed time.
     *
     * @param current the new current time
     */
    public void setCurrent(float current) {
        this.current = current;
    }

    /**
     * Returns the period after which the timer resets and is considered elapsed.
     *
     * @return the timer period
     */
    public float getPeriod() {
        return period;
    }

    /**
     * Sets the timer period.
     *
     * @param period the new period
     */
    public void setPeriod(float period) {
        this.period = period;
    }

    /**
     * Checks whether the timer has elapsed.
     *
     * @return {@code true} if the timer has elapsed, {@code false} otherwise
     */
    public boolean isElapsed() {
        return elapsed;
    }

    /**
     * Forces the timer to mark as elapsed, without affecting the current time.
     */
    public void setElapsed() {
        elapsed = true;
    }

    /**
     * Updates the timer by the specified time delta.
     * If the accumulated time reaches or exceeds the period, the timer resets and is marked as elapsed.
     *
     * @param deltaTime the time to add to the current timer
     */
    public void tick(float deltaTime) {
        current += deltaTime;
        if (current >= period) {
            current = 0;
            elapsed = true;
        } else {
            elapsed = false;
        }
    }

    /**
     * Constructs a new timer with a specified period.
     * The current time is initialized to 0 and the timer is not elapsed.
     *
     * @param period the period for the timer
     */
    public Timer(float period) {
        current = 0;
        this.period = period;
        elapsed = false;
    }

    /**
     * Constructs a new timer with a specified period and current time.
     * The timer is not marked as elapsed upon creation.
     *
     * @param period  the period for the timer
     * @param current the initial current time
     */
    public Timer(float period, float current) {
        this.period = period;
        this.current = current;
        elapsed = false;
    }

    /**
     * Creates and returns a copy of this timer.
     *
     * @return a cloned instance of the timer
     */
    @Override
    public Timer clone() {
        return new Timer(period, current);
    }

    /**
     * Compares this timer to the specified object for equality.
     * Two timers are considered equal if their period, current, and elapsed values are equal.
     *
     * @param o the object to compare with
     * @return {@code true} if the timers are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        return ((Timer) o).period == period && ((Timer) o).current == current && ((Timer) o).elapsed == elapsed;
    }
}
