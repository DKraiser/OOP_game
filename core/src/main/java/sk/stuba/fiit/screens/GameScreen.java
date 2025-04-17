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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.*;

import org.lwjgl.opengl.GL20;
import sk.stuba.fiit.*;
import sk.stuba.fiit.entities.Spawner;
import sk.stuba.fiit.entities.player.Player;
import sk.stuba.fiit.enums.ScreenType;
import sk.stuba.fiit.events.EnemyKilledEvent;
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

    private final MyGame game;
    private SpriteBatch batch;
    private List<Texture> undisposedTextures;
    private InputMultiplexer inputMultiplexer;
    private Logger logger;
    private Random random;

    private Viewport gameViewport;
    private Camera gameCamera;
    private GameObject background;

    private Stage uiStage;
    private Skin uiSkin;
    private Camera uiCamera;
    private Viewport uiViewport;

    private Player player;

    private Wave currentWave;
    private List<Wave> waves;
    private Timer waveTimer;
    private List<Projectile> projectileEnvironment;
    private List<Projectile> tempProjectileEnvironment;
    private List<Spawner> spawnerEnvironment;
    private List<Spawner> tempSpawnerEnvironment;
    private ShapeRenderer shapeRenderer;

    public GameScreen(MyGame game, Player player, SpriteBatch batch, List<Texture> undisposedTextures) {
        this.game = game;

        this.player = player;
        EnemyKilledEvent.setPlayer(player);
        player.updatePosition(new Vector2(screenWidth / 2, screenHeight / 2));

        this.batch = batch;
        this.undisposedTextures = undisposedTextures;

        random = new Random();
        random.setSeed(System.currentTimeMillis());

        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        logger = new Logger("GameScreen", Application.LOG_DEBUG);

        gameCamera = new OrthographicCamera();
        gameViewport = new FitViewport(screenWidth, screenHeight, gameCamera);

        uiCamera = new OrthographicCamera();
        uiViewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), uiCamera);

        projectileEnvironment = new ArrayList<Projectile>();
        tempProjectileEnvironment = new ArrayList<Projectile>();
        spawnerEnvironment = new ArrayList<Spawner>();
        tempSpawnerEnvironment = new ArrayList<Spawner>();

        waves = new ArrayList<>();
        waves.add(new SmallAsteroidWave(5, screenWidth / 2, spawnerEnvironment));
        waves.add(new BigAsteroidWave(5, screenWidth / 2, spawnerEnvironment));
        waveTimer = new Timer(10, 3);

        logger.info("Initialized");
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(gameCamera.combined);
        shapeRenderer.setProjectionMatrix(gameCamera.combined);

        waveTimer.tick(deltaTime);
        if (waveTimer.isElapsed()) {
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

        if (!player.isAlive()) {
            try {
                playerIsDeadAction();
            } catch (Exception e) {
                logger.error(e.getMessage());
                logger.error(e.getStackTrace().toString());
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

        uiStage.act(deltaTime);
        ((Label)((Table)uiStage.getActors().get(0)).getChild(0)).setText("HP: " + player.getHealth());
        ((Label)((Table)uiStage.getActors().get(0)).getChild(1)).setText("Balance: " + player.getBalance());
        uiStage.draw();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (Projectile projectile : projectileEnvironment) {
            drawCollider(projectile.getCollider());
        }
        drawCollider(player.getCollider());
        shapeRenderer.end();

        gameCamera.update();
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
        uiViewport.update(width, height, true);
        gameViewport.update(width, height, true);
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
        shapeRenderer.dispose();
        uiSkin.dispose();
        uiStage.dispose();
    }

    @Override
    public void show() {
        background = new GameObject("Background","Background", new Texture("space_background.jpg"));
        background.setSize(screenWidth, screenHeight);
        undisposedTextures.add(background.getTexture());

        shapeRenderer = new ShapeRenderer();

        uiStage = new Stage(uiViewport, batch);
        uiSkin = new Skin(Gdx.files.internal("uiskin.json"));

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(uiStage);
        inputMultiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (button == Input.Buttons.LEFT) {
                    Vector3 touchPoint;
                    Vector2 direction;

                    touchPoint = gameViewport.unproject(new Vector3(screenX, screenY, 0));
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
                        Gdx.app.exit();
                    });
                    return true;
                }
                return false;
            }
        });
        Gdx.input.setInputProcessor(inputMultiplexer);

        Table table = new Table();
        table.setFillParent(true);
        table.left().top();

        Label hpLabel = new Label("", uiSkin);
        hpLabel.setAlignment(Align.left);

        Label balanceLabel = new Label("", uiSkin);
        balanceLabel.setAlignment(Align.left);

        table.add(hpLabel).align(Align.left);
        table.row();
        table.add(balanceLabel).align(Align.left);

        uiStage.setDebugAll(true);
        uiStage.addActor(table);
    }

    public void playerIsDeadAction() throws InterruptedException{
        Thread.sleep(350);
        game.changeScreen(ScreenType.RESTART);
    }
    //endregion
}
