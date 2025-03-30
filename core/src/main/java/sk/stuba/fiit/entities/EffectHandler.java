package sk.stuba.fiit.entities;

import sk.stuba.fiit.effects.Effect;

import java.util.ArrayList;
import java.util.List;

public abstract class EffectHandler {
    private List<Effect> effects;

    public List<Effect> getEffects() { return effects; }
    public Effect getEffect(Class effectClass) {
        return effects.stream().filter(e -> e.getClass() == effectClass).findFirst().orElse(null);
    }
    public void setEffects(List<Effect> newEffects) { effects.addAll(newEffects); }

    public void takeEffect(Effect effect) {
        effects.add(effect);
        effect.getAffect().run();
    }

    public void updateEffects(float delta) {

    }

    public EffectHandler() {
        effects = new ArrayList<Effect>();
    }
}
