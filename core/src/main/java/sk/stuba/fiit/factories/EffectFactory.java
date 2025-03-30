package sk.stuba.fiit.factories;

import sk.stuba.fiit.effects.Effect;

public class EffectFactory {
    public Effect createRegenerationEffect(int level, boolean isFinite, float duration, Effectable target)
    {
        return new Effect("Regeneration",
            "Your health are recovering 10% faster",
            Math.min(1, level),
            isFinite,
            duration,
            target,
            () -> {

            },
            () -> {

            });
    }
    public Effect createResistanceEffect(int level, boolean isFinite, float duration, Effectable target)
    {
        return new Effect("Resistance",
            "You can block the damage with 10% chance",
            Math.min(1, level),
            isFinite,
            duration,
            target,
            () -> {

            },
            () -> {

            });
    }

    public Effect createParalysisEffect(int level, boolean isFinite, float duration, Effectable target)
    {
        return new Effect("Paralysis",
            "You cannot attack your enemies",
            Math.min(1, level),
            isFinite,
            duration,
            target,
            () -> {

            },
            () -> {

            });
    }
}
