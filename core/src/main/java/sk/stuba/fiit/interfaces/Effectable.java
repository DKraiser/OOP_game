package sk.stuba.fiit.interfaces;

import sk.stuba.fiit.effects.Effect;

public interface Effectable {
    void takeEffect(Effect effect);
    void updateEffects(float deltaTime);
}
