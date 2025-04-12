package sk.stuba.fiit.effects;

import sk.stuba.fiit.entities.Entity;

public class RegenerationEffect extends Effect{
    private final StringBuilder nameBuilder;
    private final StringBuilder descriptionBuilder;

    public RegenerationEffect(int level, boolean isFinite, float duration, float remainingTime, Entity target) {
        super(null, null, level, isFinite, duration, remainingTime, target);

        nameBuilder = new StringBuilder();
        nameBuilder.append("Regeneration ");
        nameBuilder.append('I' * level);
        setName(nameBuilder.toString());

        descriptionBuilder = new StringBuilder();
        descriptionBuilder.append("Your hp are regenerated ");
        descriptionBuilder.append(level * 10);
        descriptionBuilder.append("% faster");
        setDescription(descriptionBuilder.toString());
    }

    @Override
    public Effect clone() {
        return new ResistanceEffect(getLevel(), isFinite(), getDuration(), getRemainingTime(), null);
    }

    @Override
    public void applyEffect() {

    }

    @Override
    public void removeEffect() {

    }
}
