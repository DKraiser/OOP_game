package sk.stuba.fiit;

import sk.stuba.fiit.effects.Effect;
import sk.stuba.fiit.interfaces.Effectable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EffectHandler implements Effectable, Cloneable, Serializable {
    private List<Effect> effects;

    public List<Effect> getEffects() { return effects; }
    public Effect getEffect(Class<? extends Effect> effectClass) {
        return effects.stream().filter(e -> e.getClass() == effectClass).findFirst().orElse(null);
    }

    @Override
    public void takeEffect(Effect effect) {
        Effect existing = getEffect(effect.getClass());
        if (existing != null) {
            existing.removeEffect();
            effects.remove(existing);
        }
        effects.add(effect);
        effect.applyEffect();
    }

    public void takeEffect(Collection<Effect> effect) {
        for (Effect e : effect) {
            takeEffect(e);
        }
    }

    @Override
    public void updateEffects(float deltaTime) {
        for (Effect effect : getEffects()) {
            effect.tick(deltaTime);
        }
    }

    @Override
    public EffectHandler clone()
    {
        EffectHandler newEffectHandler = new EffectHandler();
        List<Effect> newEffects = new ArrayList<>();

        if (!effects.isEmpty()) {
            for (Effect effect : getEffects()) {
                newEffects.add(effect.clone());
            }
            newEffectHandler.takeEffect(newEffects);
        }

        return newEffectHandler;
    }

    public EffectHandler() {
        effects = new ArrayList<Effect>();
    }
}
