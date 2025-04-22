package sk.stuba.fiit.effects;

import sk.stuba.fiit.entities.Entity;

public class ExtraHealthEffect extends Effect {
    private final StringBuilder nameBuilder;
    private final StringBuilder descriptionBuilder;

    @Override
    public Effect clone() {
        return new ExtraHealthEffect(getLevel(), isFinite(), getDuration(), getRemainingTime(), null);
    }

    @Override
    public void applyEffect() {
        setInitialValue(getTarget().getMaxHealth());
        getTarget().setMaxHealth((int) getInitialValue() + getLevel());
    }

    @Override
    public void removeEffect() {
        getTarget().setMaxHealth((int) getInitialValue());
    }

    public ExtraHealthEffect(int level, boolean isFinite, float duration, float remainingTime, Entity target) {
        super(null, null, level, isFinite, duration, remainingTime, target);

        nameBuilder = new StringBuilder();
        nameBuilder.append("Extra health ");
        nameBuilder.append('I' * level);
        setName(nameBuilder.toString());

        descriptionBuilder = new StringBuilder();
        descriptionBuilder.append("Now you have ");
        descriptionBuilder.append(level);
        descriptionBuilder.append(" extra health points");
        setDescription(descriptionBuilder.toString());
    }
}
