package sk.stuba.fiit.factories.weaponfactories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.Collider;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.factories.projectilefactories.EnemyProjectileFactory;
import sk.stuba.fiit.projectiles.EnemyProjectile;

public class AsteroidEnemyWeaponFactory extends WeaponFactory {
    @Override
    public Weapon create(Object... args) {
        projectileFactory = new EnemyProjectileFactory(projectileTemplate, this.radiusOfAttacker, this.positionOfAttacker);

        weapon = new Weapon("Asteroid Weapon", "", null, projectileFactory);
        return weapon;
    }

    @Override
    public void adjustProjectileTemplate() {
        projectileTemplate = new EnemyProjectile("", "", new Texture("asteroid.png"), 1, 1, null, 10f, 1);
        projectileTemplate.getSprite().setSize(0.8f,0.8f);
        projectileTemplate.getSprite().setOrigin(projectileTemplate.getSprite().getWidth() / 2, projectileTemplate.getSprite().getHeight() / 2);

        projectileTemplate.setCollider(new Collider(new Circle(new Vector2(projectileTemplate.getSprite().getPosition()), projectileTemplate.getSprite().getHeight() / 2)));
    }

    public AsteroidEnemyWeaponFactory(float radiusOfAttacker, Vector2 positionOfAttacker) {
        super(radiusOfAttacker, positionOfAttacker);
    }

    public AsteroidEnemyWeaponFactory() {
        super();
    }
}
