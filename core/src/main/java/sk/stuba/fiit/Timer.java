package sk.stuba.fiit;

import java.io.Serializable;

public class Timer implements Cloneable, Serializable {
    private float current;
    private float period;
    private boolean elapsed;

    public float getCurrent() {
        return current;
    }

    public void setCurrent(float current) {
        this.current = current;
    }

    public float getPeriod() {
        return period;
    }
    public void setPeriod(float period) {
        this.period = period;
    }

    public boolean isElapsed() {
        return elapsed;
    }

    public void setElapsed() {
        elapsed = true;
    }

    public void tick(float deltaTime) {
        current += deltaTime;
        if (current >= period) {
            current = 0;
            elapsed = true;
        }
        else {
            elapsed = false;
        }
    }

    public Timer(float period) {
        current = 0;
        this.period = period;
        elapsed = false;
    }

    public Timer(float period, float current) {
        this.period = period;
        this.current = current;
        elapsed = false;
    }

    @Override
    public Timer clone() {
        return new Timer(period, current);
    }
}
