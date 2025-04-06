package sk.stuba.fiit.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import sk.stuba.fiit.Collider;
import sk.stuba.fiit.GameObject;
import sk.stuba.fiit.MyGame;
import sk.stuba.fiit.Weapon;
import sk.stuba.fiit.entities.Spawner;
import sk.stuba.fiit.entities.player.Player;
import sk.stuba.fiit.factories.enemyfactories.AsteroidSpawnerFactory;
import sk.stuba.fiit.factories.enemyfactories.SpawnerFactory;
import sk.stuba.fiit.factories.weaponfactories.AsteroidEnemyWeaponFactory;
import sk.stuba.fiit.factories.weaponfactories.BasicPlayerWeaponFactory;
import sk.stuba.fiit.factories.weaponfactories.WeaponFactory;
import sk.stuba.fiit.projectiles.Projectile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameScreen implements Screen{
    public static final float screenWidth = 8;
    public static final float screenHeight = 6;
    private final Game game;
    private final SpriteBatch batch;

    private GameObject background;
    private Viewport viewport;
    private Camera camera;
    private Logger logger;

    private Player player;
    private WeaponFactory playerWeaponFactory;
    private Vector3 touchPoint;

    List<Projectile> projectileEnvironment;
    List<Projectile> tempProjectileEnvironment;
    List<Spawner> spawnerEnvironment;
    List<Spawner> tempSpawnerEnvironment;
    ShapeRenderer shapeRenderer;

    private SpawnerFactory asteroidSpawnerFactory;

    public GameScreen(Game game) {
        this.game = game;

        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        logger = new Logger("GameScreen", Application.LOG_DEBUG);

        camera = new OrthographicCamera();
        viewport = new FitViewport(screenWidth, screenHeight, camera);
        camera.position.set(screenWidth / 2, screenHeight / 2, 0);
        camera.update();

        playerWeaponFactory = new BasicPlayerWeaponFactory();
        player = new Player("P", "Player", new Texture("earth.png"), 1, new Vector2(screenWidth / 2, screenHeight / 2), 5, 5, 0, playerWeaponFactory);

        asteroidSpawnerFactory = new AsteroidSpawnerFactory();

        projectileEnvironment = new ArrayList<Projectile>();
        tempProjectileEnvironment = new ArrayList<Projectile>();
        spawnerEnvironment = new ArrayList<Spawner>();
        tempSpawnerEnvironment = new ArrayList<Spawner>();
        shapeRenderer = new ShapeRenderer();

        background = new GameObject("","", new Texture("space_background.jpg"));
        background.getSprite().setSize(screenWidth, screenHeight);

        batch = ((MyGame) game).getBatch();
        logger.info("Initialized");
    }

    @Override
    public void render(float deltaTime) {
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
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
                logger.info("Projectile: " + projectile.getSprite().getPosition());
                if (Math.abs(projectile.getSprite().getPosition().x - screenWidth / 2) > screenWidth / 2 * 1.5f) {
                    tempProjectileEnvironment.remove(projectile);
                }
            }

            projectileEnvironment.clear();
            if (tempProjectileEnvironment.size() > 0) {
                projectileEnvironment.addAll(tempProjectileEnvironment);
            }
        }
        if (!spawnerEnvironment.isEmpty())
            for (Spawner spawner : spawnerEnvironment) {
                spawner.getSprite().draw(batch);
            }

        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (Projectile projectile : projectileEnvironment) {
            drawCollider(projectile.getCollider());
        }
        drawCollider(player.getCollider());
        shapeRenderer.end();

        camera.update();
    }

    public void input() {
        touchPoint = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector2 direction = player.findDirectionVector(new Vector2(touchPoint.x, touchPoint.y));
            player.attack(direction, projectileEnvironment);
        }
        else if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            player.getSprite().setPosition(new Vector2(touchPoint.x - player.getSprite().getWidth() / 2, touchPoint.y - player.getSprite().getHeight() / 2));
            player.getWeapon().update(new Vector2(player.getSprite().getPosition()).add(new Vector2(player.getSprite().getWidth() / 2, player.getSprite().getHeight() / 2)), player.getSprite().getHeight() / 2);
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            spawnerEnvironment.add(asteroidSpawnerFactory.create());
            Random random = new Random();
            spawnerEnvironment.getLast().getSprite().setPosition(new Vector2(random.nextFloat(0, screenWidth), random.nextFloat(0, screenHeight)));
            spawnerEnvironment.getLast().getWeapon().update(new Vector2(spawnerEnvironment.getLast().getSprite().getPosition()), spawnerEnvironment.getLast().getSprite().getHeight() / 2);
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
            spawnerEnvironment.getLast().attack(player, projectileEnvironment);
        }
    }

    public void drawCollider(Collider collider) {
        if (collider.getCircleCollider() != null) {
            Circle circle = collider.getCircleCollider();
            shapeRenderer.setColor(Color.PINK);
            shapeRenderer.circle(circle.x, circle.y, circle.radius);
        } else if (collider.getRectCollider() != null) {
            Rectangle rect = collider.getRectCollider();
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
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
