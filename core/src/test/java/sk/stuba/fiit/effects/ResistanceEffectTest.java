package sk.stuba.fiit.effects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.stuba.fiit.Collider;
import sk.stuba.fiit.Timer;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.entities.Entity;
import sk.stuba.fiit.entities.Player;
import sk.stuba.fiit.factories.projectilefactories.ProjectileFactory;
import sk.stuba.fiit.factories.weaponfactories.WeaponFactory;
import sk.stuba.fiit.projectiles.Projectile;
import sk.stuba.fiit.strategies.takingDamage.ResistanceTakingDamageStrategy;
import sk.stuba.fiit.strategies.takingDamage.StandartTakingDamageStrategy;

import static org.junit.jupiter.api.Assertions.*;

public class ResistanceEffectTest {
    ResistanceEffect effect;
    Player target;

    @BeforeEach
    public void setUp() {
        WeaponFactory weaponFactory = new WeaponFactory() {
            @Override
            public void adjustProjectileTemplate() {

            }

            @Override
            public Weapon create(Object... args) {
                return new Weapon("", "", null, new ProjectileFactory(new Projectile("", "", null, 1, 1, new Vector2(1, 0), 1, 1) {
                    @Override
                    public Projectile clone() {
                        Projectile clone = new Projectile("", "", null, 1, 1, new Vector2(1, 0), 1, 1) {

                            @Override
                            public void die() {

                            }

                            @Override
                            public void takeDamage(int damage) {

                            }

                            @Override
                            public Projectile clone() {
                                return null;
                            }

                            @Override
                            public void onCollision(Entity collisionEntity) {}
                        };
                        clone.setCollider(new Collider(new Circle(1,1,1)));
                        return clone;
                    }

                    @Override
                    public void die() {

                    }

                    @Override
                    public void takeDamage(int damage) {

                    }

                    @Override
                    public void onCollision(Entity collisionEntity) {}
                }, 0, null));
            }
        };
        target = new Player("TestPlayer", "Test Desc", null, 10f, new Vector2(50, 50), 50, 100, 10, new Timer(1), 0, weaponFactory);
        effect = new ResistanceEffect(1, false, 3, 3, target);
    }

    @Test
    public void testApplyEffect() {
        effect.applyEffect();
        assertInstanceOf(ResistanceTakingDamageStrategy.class, target.getTakingDamage());
    }

    @Test
    public void testRemoveEffect() {
        effect.applyEffect();
        effect.removeEffect();
        assertInstanceOf(StandartTakingDamageStrategy.class, target.getTakingDamage());
    }

    @Test
    public void testClone() {
        Effect clone = effect.clone();
        assertEquals(effect.getLevel(), clone.getLevel());
        assertEquals(effect.isFinite(), clone.isFinite());
        assertEquals(effect.getDuration(), clone.getDuration());
        assertEquals(effect.getRemainingTime(), clone.getRemainingTime());
        assertNull(clone.getTarget());
    }
}
