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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import sk.stuba.fiit.*;
import sk.stuba.fiit.effects.ParalyseEffect;
import sk.stuba.fiit.entities.Spawner;
import sk.stuba.fiit.entities.player.Player;
import sk.stuba.fiit.events.EnemyKilledEvent;
import sk.stuba.fiit.factories.enemyfactories.SmallAsteroidSpawnerFactory;
import sk.stuba.fiit.factories.enemyfactories.SpawnerFactory;
import sk.stuba.fiit.factories.weaponfactories.BasicPlayerWeaponFactory;
import sk.stuba.fiit.factories.weaponfactories.WeaponFactory;
import sk.stuba.fiit.interfaces.Wave;
import sk.stuba.fiit.projectiles.Projectile;
import sk.stuba.fiit.waves.BigAsteroidWave;
import sk.stuba.fiit.waves.SmallAsteroidWave;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameScreen implements Screen{
    public static final float screenWidth = 8;
    public static final float screenHeight = 6;
    private final Game game;

    private SpriteBatch batch;
    private List<Texture> undisposedTextures;
    private GameObject background;
    private Viewport viewport;
    private Camera camera;
    private Logger logger;
    private Random random;

    private Player player;

    private Wave currentWave;
    private List<Wave> waves;
    private Timer waveTimer;
    private List<Projectile> projectileEnvironment;
    private List<Projectile> tempProjectileEnvironment;
    private List<Spawner> spawnerEnvironment;
    private List<Spawner> tempSpawnerEnvironment;
    private ShapeRenderer shapeRenderer;

    private InputMultiplexer inputMultiplexer;
    private Stage stage;
    private Skin skin;

    public GameScreen(Game game, Player player, SpriteBatch batch, List<Texture> undisposedTextures) {
        this.game = game;
        random = new Random();
        random.setSeed(System.currentTimeMillis());

        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        logger = new Logger("GameScreen", Application.LOG_DEBUG);

        camera = new OrthographicCamera();
        viewport = new FitViewport(screenWidth, screenHeight, camera);
        camera.position.set(screenWidth / 2, screenHeight / 2, 0);
        camera.update();

        this.player = player;
        EnemyKilledEvent.setPlayer(player);
        player.updatePosition(new Vector2(screenWidth / 2, screenHeight / 2));

        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (button == Input.Buttons.LEFT) {
                    Vector3 touchPoint;
                    Vector2 direction;

                    touchPoint = camera.unproject(new Vector3(screenX, screenY, 0));
                    direction = player.findDirectionVector(new Vector2(touchPoint.x, touchPoint.y));
                    player.attack(direction, projectileEnvironment);
                    return true;
                }
                return false;
            }

            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    try {
                        FileOutputStream fos = new FileOutputStream("serialized/player.json");
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(player);
                        oos.close();
                        fos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Gdx.app.postRunnable(() -> {
                        dispose();
                        Gdx.app.exit();
                    });
                    return true;
                }
                return false;
            }
        });
        Gdx.input.setInputProcessor(inputMultiplexer);

        projectileEnvironment = new ArrayList<Projectile>();
        tempProjectileEnvironment = new ArrayList<Projectile>();
        spawnerEnvironment = new ArrayList<Spawner>();
        tempSpawnerEnvironment = new ArrayList<Spawner>();

        waves = new ArrayList<>();
        waves.add(new SmallAsteroidWave(5, screenWidth / 2, spawnerEnvironment));
        waves.add(new BigAsteroidWave(5, screenWidth / 2, spawnerEnvironment));
        waveTimer = new Timer(10, 3);
        shapeRenderer = new ShapeRenderer();

        background = new GameObject("","", new Texture("space_background.jpg"));
        background.setSize(screenWidth, screenHeight);

        this.batch = batch;
        this.undisposedTextures = undisposedTextures;
        logger.info("Initialized");
    }

    @Override
    public void render(float deltaTime) {
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        waveTimer.tick(deltaTime);
        if (waveTimer.isElapsed())
        {
            if (currentWave != null)
                currentWave.retract();
            currentWave = waves.get(random.nextInt(waves.size()));
            currentWave.summon();
        }

        for (Spawner spawner : spawnerEnvironment) {
            spawner.tick(deltaTime);
            spawner.attack(player, projectileEnvironment);
        }
        player.tick(deltaTime);

        //Move projectile
        if (!projectileEnvironment.isEmpty()) {
            for (Projectile projectile : projectileEnvironment) {
                projectile.move(deltaTime);

                if (Math.abs(projectile.getPosition().x - screenWidth / 2) > screenWidth / 2 * 1.5f) {
                    projectile.setAlive(false);
                }
            }
        }

        //Detect collisions
        if (!projectileEnvironment.isEmpty()) {
            for (Projectile projectile : projectileEnvironment) {
                for (Projectile collisionProjectile : projectileEnvironment) {
                    if (projectile.getCollider().overlaps(collisionProjectile.getCollider()) && projectile != collisionProjectile) {
                        projectile.onCollision(collisionProjectile);
                    }
                }

                for (Spawner spawner : spawnerEnvironment) {
                    if (spawner.getCollider() == null)
                        continue;
                    if (projectile.getCollider().overlaps(spawner.getCollider())) {
                        projectile.onCollision(spawner);
                    }
                }
                if (projectile.getCollider().overlaps(player.getCollider())) {
                    projectile.onCollision(player);
                    player.onCollision(projectile);
                }
            }
        }

        //Remove objects
        if (!projectileEnvironment.isEmpty()) {
            tempProjectileEnvironment.clear();
            tempProjectileEnvironment.addAll(projectileEnvironment);

            for (Projectile projectile : projectileEnvironment) {
                if (!projectile.isAlive())
                {
                    tempProjectileEnvironment.remove(projectile);
                }
            }

            projectileEnvironment.clear();
            if (!tempProjectileEnvironment.isEmpty()) {
                projectileEnvironment.addAll(tempProjectileEnvironment);
            }
        }
        if (!spawnerEnvironment.isEmpty()) {
            tempSpawnerEnvironment.clear();
            tempSpawnerEnvironment.addAll(spawnerEnvironment);

            for (Spawner spawner : spawnerEnvironment) {
                if (!spawner.isAlive()) {
                    tempSpawnerEnvironment.remove(spawner);
                }
            }

            spawnerEnvironment.clear();
            if (!tempSpawnerEnvironment.isEmpty()) {
                spawnerEnvironment.addAll(tempSpawnerEnvironment);
            }
        }

        //Draw
        batch.begin();
        background.getSprite().draw(batch);
        player.getSprite().draw(batch);
        if (!projectileEnvironment.isEmpty()) {
            for (Projectile projectile : projectileEnvironment) {
                projectile.getSprite().draw(batch);
                if (!undisposedTextures.contains(projectile.getTexture())) {
                    undisposedTextures.add(projectile.getTexture());
                }
            }
        }
        if (!spawnerEnvironment.isEmpty()) {
            for (Spawner spawner : spawnerEnvironment) {
                if (spawner.getSprite() != null) {
                    spawner.getSprite().draw(batch);
                    if (!undisposedTextures.contains(spawner.getTexture())) {
                        undisposedTextures.add(spawner.getTexture());
                    }
                }
            }
        }
        batch.end();

        stage.act(deltaTime);
        stage.draw();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (Projectile projectile : projectileEnvironment) {
            drawCollider(projectile.getCollider());
        }
        drawCollider(player.getCollider());
        shapeRenderer.end();

        camera.update();
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
        background.dispose();
        shapeRenderer.dispose();
        skin.dispose();
        stage.dispose();
    }

    @Override
    public void show() {
        Button button = new Button(skin);
        button.setPosition(4, 4);
        button.setSize(50,50);
        stage.addActor(button);
    }
    //endregion
}
