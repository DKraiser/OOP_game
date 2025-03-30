package sk.stuba.fiit.factories;

import com.badlogic.gdx.graphics.Texture;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.projectiles.PlayerProjectile;

public class BasicWeaponFactory extends WeaponFactory {
    @Override
    public Weapon create(Object... args) {
        playerProjectileTemplate = new PlayerProjectile("", "", new Texture("basicBullet.png"), 1, 1, null, 10f);
        playerProjectileFactory = new PlayerProjectileFactory(playerProjectileTemplate);
        playerWeapon = new Weapon("Gun", "DefaultGun", null, playerProjectileFactory);
        return playerWeapon;
    }
}
