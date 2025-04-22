package sk.stuba.fiit.strategies.attack;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.stuba.fiit.Collider;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.factories.projectilefactories.ProjectileFactory;
import sk.stuba.fiit.interfaces.attack.RangedAttackingStrategy;
import sk.stuba.fiit.projectiles.EnemyProjectile;
import sk.stuba.fiit.projectiles.PlayerProjectile;
import sk.stuba.fiit.projectiles.Projectile;
import sk.stuba.fiit.strategies.attacking.TargetRangedAttackingStrategy;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TargetRangedAttackingStrategyTest {
    RangedAttackingStrategy rangedAttackingStrategy;
    Projectile projectile;
    Projectile enemy;
    ProjectileFactory projectileFactory;
    Weapon weapon;
    List<Projectile> projectiles;
    Vector2 direction;


    @BeforeEach
    public void setUp() {
        rangedAttackingStrategy = new TargetRangedAttackingStrategy();
        projectile = new PlayerProjectile("Mock", "Mock", null, 1, 2, null, 5, 10);
        enemy = new EnemyProjectile("Enemy", "Enemy", null, 1, 2, null, 5, 10, 1);
        projectile.setCollider(new Collider(new Circle(1, 1, 1)));
        projectileFactory = new ProjectileFactory(projectile, 1, new Vector2(0, 0));
        weapon = new Weapon("Mock", "", null, projectileFactory);
        projectiles = new ArrayList<Projectile>();
    }

    @Test
    public void testAttack() {
        assertTrue(projectiles.isEmpty());
        rangedAttackingStrategy.attack(enemy, projectiles, weapon);
        assertFalse(projectiles.isEmpty());
    }
}
