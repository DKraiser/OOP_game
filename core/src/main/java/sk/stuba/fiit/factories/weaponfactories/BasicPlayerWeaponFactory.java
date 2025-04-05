package sk.stuba.fiit.factories.weaponfactories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.factories.projectilefactories.PlayerProjectileFactory;
import sk.stuba.fiit.projectiles.PlayerProjectile;

public class BasicPlayerWeaponFactory extends WeaponFactory {
    @Override
    public Weapon create(Object... args) {
        projectileFactory = new PlayerProjectileFactory(projectileTemplate, this.radiusOfAttacker, this.positionOfAttacker);

        weapon = new Weapon("Player Gun", "Basic Player Gun", null, projectileFactory);
        return weapon;
    }

    @Override
    public void adjustProjectileTemplate() {
        projectileTemplate = new PlayerProjectile("", "", new Texture("basic_bullet.png"), 1, 1, null, 10f);
        projectileTemplate.getSprite().setSize(0.5f,0.5f);
        projectileTemplate.getSprite().setOrigin(projectileTemplate.getSprite().getWidth() / 2, projectileTemplate.getSprite().getHeight() / 2);
    }

    public BasicPlayerWeaponFactory (float radiusOfAttacker, Vector2 positionOfAttacker) {
        super(radiusOfAttacker, positionOfAttacker);
    }

    public BasicPlayerWeaponFactory() {
        super();
    }
}
