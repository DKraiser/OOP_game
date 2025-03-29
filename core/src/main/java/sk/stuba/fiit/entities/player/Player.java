package sk.stuba.fiit.entities.player;

import com.badlogic.gdx.graphics.Texture;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.effects.Effect;
import sk.stuba.fiit.entities.Entity;
import sk.stuba.fiit.interfaces.Effectable;

import java.util.ArrayList;

public class Player extends Entity implements Effectable {
    private int balance;
    private Weapon weapon;

    public Weapon getWeapon() {
        return weapon;
    }

    public Player(String name, String description, Texture texture, int health, int maxHealth, int balance, Weapon weapon) {
        super(name, description, texture, health, maxHealth, new ArrayList<>());
        this.balance = balance;
        this.weapon = weapon;
    }

    @Override
    public void takeEffect(Effect effect) {
        getEffects().add(effect);
    }

    @Override
    public void updateEffects(float delta) {
        for (Effect effect : getEffects())
            effect.tickEffect(delta);
    }
}
