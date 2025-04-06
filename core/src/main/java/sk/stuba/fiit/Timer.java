package sk.stuba.fiit;

import sk.stuba.fiit.entities.Spawner;

public class Timer implements Cloneable {
    private float current;
    private float period;
    private boolean elapsed;

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

    public boolean isElapsed() {
        return elapsed;
    }

    public Timer(float period) {
        current = 0;
        this.period = period;
        elapsed = false;
    }

    public Timer(float period, float current) {
        this.current = current;
        this.period = period;
        elapsed = false;
    }

    @Override
    public Timer clone() {
        return new Timer(period, current);
    }
}
