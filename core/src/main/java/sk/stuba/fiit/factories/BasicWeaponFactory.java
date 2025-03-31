package sk.stuba.fiit.factories;

import com.badlogic.gdx.graphics.Texture;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.projectiles.PlayerProjectile;

public class BasicWeaponFactory extends WeaponFactory {
    @Override
    public Weapon create(Object... args) {
        projectileTemplate = new PlayerProjectile("", "", new Texture("basicBullet.png"), 1, 1, null, 10f);
        projectileFactory = new PlayerProjectileFactory(projectileTemplate);
        weapon = new Weapon("Player Gun", "Basic Player Gun", null, projectileFactory);
        return weapon;
    }
}
