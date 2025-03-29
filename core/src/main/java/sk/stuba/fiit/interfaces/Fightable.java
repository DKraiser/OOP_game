package sk.stuba.fiit.interfaces;

public interface Fightable {
    void attack(Fightable target, int damage);
    int getHealth();
    void die();
    void takeDamage(int damage);
}
