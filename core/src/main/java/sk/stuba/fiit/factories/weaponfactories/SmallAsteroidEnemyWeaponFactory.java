package sk.stuba.fiit.factories.weaponfactories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.Collider;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.factories.projectilefactories.EnemyProjectileFactory;
import sk.stuba.fiit.projectiles.EnemyProjectile;

public class SmallAsteroidEnemyWeaponFactory extends WeaponFactory {
    @Override
    public Weapon create(Object... args) {
        projectileFactory = new EnemyProjectileFactory(projectileTemplate, this.radiusOfAttacker, this.positionOfAttacker);

        weapon = new Weapon("Small Asteroid Weapon", "", null, projectileFactory);
        return weapon;
    }

    @Override
    public void adjustProjectileTemplate() {
        projectileTemplate = new EnemyProjectile("Small Asteroid", "", new Texture("asteroid.png"), 1, 1, null, 3.5f, 1, 2);
        projectileTemplate.setSize(0.4f,0.4f);
        projectileTemplate.setOrigin(projectileTemplate.getWidth() / 2, projectileTemplate.getHeight() / 2);

        projectileTemplate.setCollider(new Collider(new Circle(new Vector2(projectileTemplate.getPosition()), projectileTemplate.getHeight() / 2)));
    }

    public SmallAsteroidEnemyWeaponFactory(float radiusOfAttacker, Vector2 positionOfAttacker) {
        super(radiusOfAttacker, positionOfAttacker);
    }

    public SmallAsteroidEnemyWeaponFactory() {
        super();
    }
}
