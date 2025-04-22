package sk.stuba.fiit.events;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
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

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EnemyKilledEventTest {
    Player player;

    @BeforeEach
    public void setUp() {
        new HeadlessApplication(new ApplicationAdapter() {});
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
        player = new Player("TestPlayer", "Test Desc", null, 10f, new Vector2(50, 50), 50, 100, 10, new Timer(1), 0, weaponFactory);
    }

    @Test
    public void testSetPlayer() throws NoSuchFieldException, IllegalAccessException {
        EnemyKilledEvent.setPlayer(player);
        Field f = EnemyKilledEvent.class.getDeclaredField("player");
        f.setAccessible(true);
        assertEquals(player, f.get(Player.class));
    }

    @Test
    public void testWasInvoked() throws NoSuchFieldException, IllegalAccessException {
        EnemyKilledEventTestable.setPlayer(player);
        EnemyKilledEventTestable.invokeEvent(1);
        assertTrue(EnemyKilledEventTestable.class.getField("wasInvoked").getBoolean(EnemyKilledEventTestable.class));
    }

    private static class EnemyKilledEventTestable extends EnemyKilledEvent {
        public static boolean wasInvoked = false;
        public static void invokeEvent(int price) {
            EnemyKilledEvent.invokeEvent(price);
            wasInvoked = true;
        }
    }
}
