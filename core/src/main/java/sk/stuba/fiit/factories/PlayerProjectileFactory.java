package sk.stuba.fiit.factories;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;
import sk.stuba.fiit.effects.Effect;
import sk.stuba.fiit.exceptions.EmptyCollectionEnumeratingException;
import sk.stuba.fiit.projectiles.PlayerProjectile;
import sk.stuba.fiit.projectiles.Projectile;
import sk.stuba.fiit.screens.GameScreen;

import java.util.ArrayList;
import java.util.List;

public class PlayerProjectileFactory implements Factory<PlayerProjectile>{
    private final Projectile projectileTemplate;
    private PlayerProjectile newProjectile;
    private Logger logger;

    @Override
    public PlayerProjectile create(Object ... direction) {
        Vector2 directionVector = (Vector2) direction[0];

        newProjectile = new PlayerProjectile(projectileTemplate.getName(), projectileTemplate.getDescription(), projectileTemplate.getTexture(), projectileTemplate.getHealth(), projectileTemplate.getMaxHealth(), directionVector, projectileTemplate.getSpeed());

        newProjectile.getSprite().setSize(0.5f,0.5f);
        newProjectile.getSprite().setOrigin(newProjectile.getSprite().getWidth() / 2, newProjectile.getSprite().getHeight() / 2);
        newProjectile.getSprite().setPosition(new Vector2(GameScreen.screenWidth / 2 - 0.25f, GameScreen.screenHeight / 2 - 0.25f).add(directionVector.scl(0.8f)));

        float angleRad = (float)Math.atan(directionVector.y / directionVector.x) - (float)Math.PI / 2;
        if (directionVector.x < 0) angleRad += (float)Math.PI;
        newProjectile.getSprite().setRotation((float)(angleRad * 180 / Math.PI));

        logger.debug("Projectile Position: " + newProjectile.getSprite().getPosition());
        logger.debug("Projectile Direction: " + directionVector);
        logger.debug("Projectile Rotation: " + newProjectile.getSprite().getRotation());
        return newProjectile;
    }

    public PlayerProjectileFactory(Projectile projectileTemplate) {
        this.projectileTemplate = projectileTemplate;
        logger = new Logger("PlayerProjectileFactory", Logger.DEBUG);
    }
}
