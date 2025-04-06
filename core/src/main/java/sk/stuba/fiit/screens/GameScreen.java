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
import sk.stuba.fiit.*;
import sk.stuba.fiit.entities.Spawner;
import sk.stuba.fiit.entities.player.Player;
import sk.stuba.fiit.events.EnemyKilledEvent;
import sk.stuba.fiit.factories.enemyfactories.AsteroidSpawnerFactory;
import sk.stuba.fiit.factories.enemyfactories.SpawnerFactory;
import sk.stuba.fiit.factories.weaponfactories.AsteroidEnemyWeaponFactory;
import sk.stuba.fiit.factories.weaponfactories.BasicPlayerWeaponFactory;
import sk.stuba.fiit.factories.weaponfactories.WeaponFactory;
import sk.stuba.fiit.projectiles.EnemyProjectile;
import sk.stuba.fiit.projectiles.PlayerProjectile;
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
    private Random random;

    List<Projectile> projectileEnvironment;
    List<Projectile> tempProjectileEnvironment;
    List<Spawner> spawnerEnvironment;
    List<Spawner> tempSpawnerEnvironment;
    ShapeRenderer shapeRenderer;

    private SpawnerFactory asteroidSpawnerFactory;

    public GameScreen(Game game) {
        this.game = game;
        random = new Random();
        random.setSeed(System.currentTimeMillis());

        Gdx.app.setLogLevel(Application.LOG_INFO);
        logger = new Logger("GameScreen", Application.LOG_DEBUG);

        camera = new OrthographicCamera();
        viewport = new FitViewport(screenWidth, screenHeight, camera);
        camera.position.set(screenWidth / 2, screenHeight / 2, 0);
        camera.update();

        playerWeaponFactory = new BasicPlayerWeaponFactory();
        player = new Player("P", "Player", new Texture("earth.png"), 1, new Vector2(screenWidth / 2, screenHeight / 2), 5, 5, 1, new Timer(10), 0, playerWeaponFactory);

        EnemyKilledEvent.setPlayer(player);

        asteroidSpawnerFactory = new AsteroidSpawnerFactory();

        projectileEnvironment = new ArrayList<Projectile>();
        tempProjectileEnvironment = new ArrayList<Projectile>();
        spawnerEnvironment = new ArrayList<Spawner>();
        tempSpawnerEnvironment = new ArrayList<Spawner>();
        for (int i = 0; i < 5; i++)
        {
            spawnerEnvironment.add(asteroidSpawnerFactory.create());
            float x = random.nextFloat(-screenWidth / 2, screenWidth / 2);
            float y = (float)(Math.sqrt(Math.pow(1.3f * screenWidth / 2, 2) - Math.pow(x, 2)) * Math.pow(-1, random.nextInt(0, 2)));
            spawnerEnvironment.getLast().getSprite().setPosition(new Vector2(x + screenWidth / 2, y + screenHeight / 2));
            spawnerEnvironment.getLast().getWeapon().update(new Vector2(spawnerEnvironment.getLast().getSprite().getPosition()), spawnerEnvironment.getLast().getSprite().getHeight() / 2);
            logger.info("Spawner: " + i + "position: " + spawnerEnvironment.getLast().getSprite().getPosition());
        }
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

        for (Spawner s : spawnerEnvironment)
        {
            s.getTimer().tick(deltaTime);
            s.attack(player, projectileEnvironment);
        }

        if (projectileEnvironment.size() > 0) {
            tempProjectileEnvironment.clear();
            tempProjectileEnvironment.addAll(projectileEnvironment);

            for (Projectile projectile : projectileEnvironment) {
                projectile.getSprite().draw(batch);
                projectile.move(deltaTime);
                if (Math.abs(projectile.getSprite().getPosition().x - screenWidth / 2) > screenWidth / 2 * 1.5f) {
                    tempProjectileEnvironment.remove(projectile);
                }

                if (projectile instanceof EnemyProjectile && projectile.getCollider().overlaps(player.getCollider())) {
                    projectile.getAttacker().attack(player, projectile.getDamage());
                    tempProjectileEnvironment.remove(projectile);
                }

                if (projectile instanceof PlayerProjectile) {
                    for (Projectile enemyProjectile : projectileEnvironment) {
                        if (enemyProjectile instanceof EnemyProjectile && projectile.getCollider().overlaps(enemyProjectile.getCollider())) {
                            projectile.getAttacker().attack(enemyProjectile, projectile.getDamage());
                            tempProjectileEnvironment.remove(projectile);
                        }
                    }
                }

            }
            for (Projectile projectile : projectileEnvironment) {
                if (projectile.getHealth() <= 0)
                    tempProjectileEnvironment.remove(projectile);
            }

            projectileEnvironment.clear();
            if (tempProjectileEnvironment.size() > 0) {
                projectileEnvironment.addAll(tempProjectileEnvironment);
            }

        }
        player.getTimer().tick(deltaTime);
        player.heal();

        if (!spawnerEnvironment.isEmpty()) {
            for (Spawner spawner : spawnerEnvironment) {
                spawner.getSprite().draw(batch);
            }
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
        else if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
            for (Spawner spawner : spawnerEnvironment) {
                spawner.attack(player, projectileEnvironment);
            }
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
