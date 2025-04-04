package sk.stuba.fiit;

import sk.stuba.fiit.effects.Effect;

import java.util.ArrayList;
import java.util.List;

public class EffectHandler implements Cloneable {
    private List<Effect> effects;

    public List<Effect> getEffects() { return effects; }
    public Effect getEffect(Class effectClass) {
        return effects.stream().filter(e -> e.getClass() == effectClass).findFirst().orElse(null);
    }
    public void setEffects(List<Effect> newEffects) { effects.addAll(newEffects); }

    public void takeEffect(Effect effect) {
        effects.add(effect);
        effect.applyEffect();
    }

    public void updateEffects(float delta) {

    }

    @Override
    public EffectHandler clone()
    {
        EffectHandler newEffectHandler = new EffectHandler();
        List<Effect> newEffects = new ArrayList<>();

        if (effects.size() > 0) {
            for (Effect effect : getEffects()) {
                newEffects.add(effect.clone());
            }
        }
        newEffectHandler.setEffects(newEffects);

        return newEffectHandler;
    }

    public EffectHandler() {
        effects = new ArrayList<Effect>();
    }
}
