package sk.stuba.fiit.effects;

import sk.stuba.fiit.entities.Entity;
import sk.stuba.fiit.entities.Player;

public class RegenerationEffect extends Effect{
    private StringBuilder nameBuilder;
    private StringBuilder descriptionBuilder;

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
        setInitialValue(((Player) getTarget()).getHealingTimer().getPeriod());
        if ((float)getInitialValue() > ((Player) getTarget()).getHealingTimer().getCurrent()) {
            ((Player) getTarget()).getHealingTimer().setElapsed();
        }
        ((Player) getTarget()).getHealingTimer().setPeriod((float)getInitialValue() - getLevel());
    }

    @Override
    public void removeEffect() {
        ((Player) getTarget()).getHealingTimer().setPeriod((float)getInitialValue());
    }
}
