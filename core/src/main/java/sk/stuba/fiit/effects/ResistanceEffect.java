package sk.stuba.fiit.effects;

import sk.stuba.fiit.entities.Entity;

public class ResistanceEffect extends Effect{
    private final StringBuilder nameBuilder;
    private final StringBuilder descriptionBuilder;

    public ResistanceEffect(int level, boolean isFinite, float duration, float remainingTime, Entity target) {
        super(null, null, level, isFinite, duration, remainingTime, target);

        nameBuilder = new StringBuilder();
        nameBuilder.append("Resistance ");
        nameBuilder.append('I' * level);
        setName(nameBuilder.toString());

        descriptionBuilder = new StringBuilder();
        descriptionBuilder.append("You have ");
        descriptionBuilder.append(level * 10);
        descriptionBuilder.append("% chance to block the damage");
        setDescription(descriptionBuilder.toString());
    }

    @Override
    public Effect clone() {
        return new ResistanceEffect(getLevel() ,isFinite(), getDuration(), getRemainingTime(), null);
    }

    @Override
    public void applyEffect() {

    }
}
