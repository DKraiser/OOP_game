package sk.stuba.fiit.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import sk.stuba.fiit.GameObject;
import sk.stuba.fiit.MyGame;
import sk.stuba.fiit.entities.player.Player;
import sk.stuba.fiit.factories.BasicWeaponFactory;
import sk.stuba.fiit.factories.WeaponFactory;
import sk.stuba.fiit.projectiles.Projectile;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen{
    private Game game;
    private SpriteBatch batch;
    private Player player;
    private GameObject background;
    private Viewport viewport;
    private Camera camera;
    private Logger logger;
    public static final float screenWidth = 8;
    public static final float screenHeight = 6;

    WeaponFactory weaponFactory;
    List<Projectile> projectileEnvironment;
    List<Projectile> tempProjectileEnvironment;
    private Vector3 touchPoint;
    private Vector2 direction;

    public GameScreen(Game game) {
        this.game = game;

        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        logger = new Logger("GameScreen", Application.LOG_DEBUG);

        camera = new OrthographicCamera();
        viewport = new FitViewport(screenWidth, screenHeight, camera);
        camera.position.set(screenWidth / 2, screenHeight / 2, 0);
        camera.update();

        weaponFactory = new BasicWeaponFactory();
        projectileEnvironment = new ArrayList<Projectile>();
        tempProjectileEnvironment = new ArrayList<Projectile>();
        initializePlayer();

        background = new GameObject("","", new Texture("space_background.jpg"));
        background.getSprite().setSize(screenWidth, screenHeight);

        batch = ((MyGame) game).getBatch();
        logger.info("Initialized");
    }

    @Override
    public void render(float deltaTime) {
        batch.setProjectionMatrix(camera.combined);
        input();
        batch.begin();
        background.getSprite().draw(batch);
        player.getSprite().draw(batch);

        if (projectileEnvironment.size() > 0) {
            tempProjectileEnvironment.clear();
            tempProjectileEnvironment.addAll(projectileEnvironment);

            for (Projectile projectile : projectileEnvironment) {
                projectile.getSprite().draw(batch);
                projectile.move(deltaTime);
                logger.debug("Projectile speed: " + projectile.getSpeed());

                if (Math.abs(projectile.getSprite().getPosition().x - screenWidth / 2) > screenWidth / 2 * 1.5f) {
                    tempProjectileEnvironment.remove(projectile);
                }
            }

            projectileEnvironment.clear();
            if (tempProjectileEnvironment.size() > 0) {
                for (Projectile projectile : tempProjectileEnvironment) {
                    projectileEnvironment.add(projectile);
                }
            }
        }

        batch.end();
        camera.update();
    }

    public void initializePlayer() {
        player = new Player("P", "Player P", new Texture("earth.png"), 20, 20, 0, weaponFactory.create());
        player.getSprite().setSize(1, 1);
        player.getSprite().setPosition(new Vector2(screenWidth / 2 - player.getSprite().getWidth() / 2,screenHeight / 2 - player.getSprite().getHeight() / 2));
    }

    public void input() {
        if (Gdx.input.justTouched()) {
            touchPoint = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            direction = new Vector2(touchPoint.x - screenWidth / 2, touchPoint.y - screenHeight / 2).nor();
            player.getWeapon().shoot(direction, projectileEnvironment);
        }
    }

    //region
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(screenWidth / 2, screenHeight / 2, 0);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void show() {

    }
    //endregion
}
