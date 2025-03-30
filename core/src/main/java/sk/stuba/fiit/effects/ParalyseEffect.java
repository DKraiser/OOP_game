package sk.stuba.fiit.effects;

import sk.stuba.fiit.entities.Entity;

public class ParalyseEffect extends Effect{
    private final String name;
    private final String description;

    public ParalyseEffect(boolean isFinite, float duration, float remainingTime, Entity target) {
        super(null, null, 1, isFinite, duration, remainingTime, target);

        name = "Paralyse";
        description = "Your weapons are broken and cannot shoot";

        setName(name);
        setDescription(description);
    }

    @Override
    public Effect clone() {
        return new ParalyseEffect(isFinite(), getDuration(), getRemainingTime(), null);
    }

    @Override
    public void applyEffect() {

    }
}
