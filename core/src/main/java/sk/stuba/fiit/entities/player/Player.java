package sk.stuba.fiit.entities.player;

import com.badlogic.gdx.graphics.Texture;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.effects.Effect;
import sk.stuba.fiit.entities.EffectHandler;
import sk.stuba.fiit.entities.Entity;

public class Player extends Entity {
    private int balance;
    private Weapon weapon;
    private EffectHandler effectHandler;

    public Weapon getWeapon() {
        return weapon;
    }
    public EffectHandler getEffectHandler() { return effectHandler; }
    public int getBalance() { return balance; }

    public void setWeapon(Weapon weapon) {this.weapon = weapon; }
    public void setEffectHandler(EffectHandler effectHandler) { this.effectHandler = effectHandler; }
    public void setBalance(int balance) { this.balance = balance; }

    public void takeEffect(Effect effect) {
        effectHandler.getEffects().add(effect);
    }

    public void updateEffects(float delta) {
        for (Effect effect : effectHandler.getEffects())
            effect.tickEffect(delta);
    }

    public Player(String name, String description, Texture texture, int health, int maxHealth, int balance, Weapon weapon) {
        super(name, description, texture, health, maxHealth);
        this.balance = balance;
        this.weapon = weapon;
    }
}
