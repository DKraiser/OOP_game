package sk.stuba.fiit.strategies.attack;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.stuba.fiit.Collider;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.factories.projectilefactories.ProjectileFactory;
import sk.stuba.fiit.interfaces.attack.RangedAttackingStrategy;
import sk.stuba.fiit.projectiles.PlayerProjectile;
import sk.stuba.fiit.projectiles.Projectile;
import sk.stuba.fiit.strategies.attacking.DirectionRangedAttackingStrategy;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DirectionRangedAttackingStrategyTest {
    RangedAttackingStrategy rangedAttackingStrategy;
    Projectile projectile;
    ProjectileFactory projectileFactory;
    Weapon weapon;
    List<Projectile> projectiles;
    Vector2 direction;


    @BeforeEach
    public void setUp() {
        rangedAttackingStrategy = new DirectionRangedAttackingStrategy();
        projectile = new PlayerProjectile("Mock", "Mock", null, 1, 2, null, 5, 10);
        projectile.setCollider(new Collider(new Circle(1, 1, 1)));
        projectileFactory = new ProjectileFactory(projectile, 1, new Vector2(0, 0));
        weapon = new Weapon("Mock", "", null, projectileFactory);
        projectiles = new ArrayList<Projectile>();
        direction = new Vector2(1, 1);
    }

    @Test
    public void testAttack() {
        assertTrue(projectiles.isEmpty());
        rangedAttackingStrategy.attack(direction, projectiles, weapon);
        assertFalse(projectiles.isEmpty());
    }
}
