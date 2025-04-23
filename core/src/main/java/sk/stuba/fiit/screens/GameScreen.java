package sk.stuba.fiit.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.*;

import org.lwjgl.opengl.GL20;
import sk.stuba.fiit.*;
import sk.stuba.fiit.Timer;
import sk.stuba.fiit.entities.Spawner;
import sk.stuba.fiit.entities.Player;
import sk.stuba.fiit.enums.ScreenType;
import sk.stuba.fiit.events.EnemyKilledEvent;
import sk.stuba.fiit.events.PlayerIsParalysedEvent;
import sk.stuba.fiit.waves.BigAsteroidWave;
import sk.stuba.fiit.waves.SmallAsteroidWave;
import sk.stuba.fiit.waves.Wave;
import sk.stuba.fiit.projectiles.Projectile;
import sk.stuba.fiit.waves.UfoWave;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.List;

public class GameScreen implements Screen{
    public static final float worldWidth = 8;
    public static final float worldHeight = 6;

    public static final float screenWidth = 800;
    public static final float screenHeight = 600;

    private final MyGame game;
    private SpriteBatch batch;
    private List<Texture> indisposedTextures;
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
    private WaveCollection waves;
    private Timer waveTimer;
    private List<Projectile> projectileEnvironment;
    private List<Projectile> tempProjectileEnvironment;
    private List<Spawner> spawnerEnvironment;
    private List<Spawner> tempSpawnerEnvironment;
    private ShapeRenderer shapeRenderer;

    public GameScreen(MyGame game, Player player, SpriteBatch batch, List<Texture> indisposedTextures) {
        this.game = game;
        PlayerIsParalysedEvent.setScreen(this);

        this.player = player;
        EnemyKilledEvent.setPlayer(player);
        player.updatePosition(new Vector2(worldWidth / 2, worldHeight / 2));

        this.batch = batch;
        this.indisposedTextures = indisposedTextures;

        random = new Random();
        random.setSeed(System.currentTimeMillis());

        logger = new Logger("GameScreen", Application.LOG_DEBUG);

        gameCamera = new OrthographicCamera();
        gameViewport = new FitViewport(worldWidth, worldHeight, gameCamera);

        uiCamera = new OrthographicCamera();
        uiViewport = new FitViewport(screenWidth, screenHeight, uiCamera);

        projectileEnvironment = new ArrayList<Projectile>();
        tempProjectileEnvironment = new ArrayList<Projectile>();
        spawnerEnvironment = new ArrayList<Spawner>();
        tempSpawnerEnvironment = new ArrayList<Spawner>();

        waves = new WaveCollection();
        waveTimer = new Timer(10);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.info("Initialized");
    }

    private void spawnWave() {
        new Thread(() -> {
            if (currentWave != null) {
                currentWave.retract();
            }
            currentWave = waves.getWave(random.nextFloat(0, 1));

            currentWave.summon();
        }).start();
    }

    private void moveProjectilesOnScreen(float deltaTime) {
        if (!projectileEnvironment.isEmpty()) {
            for (Projectile projectile : projectileEnvironment) {
                projectile.move(deltaTime);

                if (Math.abs(projectile.getPosition().x - worldWidth / 2) > worldHeight / 2 * 1.5f) {
                    projectile.setAlive(false);
                }
            }
        }
    }

    private void checkCollisionsOnScreen() {
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
    }

    private void removeDeadObjects() {
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
    }

    private void drawScreen(float deltaTime) {
        batch.begin();
        background.getSprite().draw(batch);
        player.getSprite().draw(batch);
        if (!projectileEnvironment.isEmpty()) {
            for (Projectile projectile : projectileEnvironment) {
                projectile.getSprite().draw(batch);
                if (!indisposedTextures.contains(projectile.getTexture())) {
                    indisposedTextures.add(projectile.getTexture());
                }
            }
        }
        if (!spawnerEnvironment.isEmpty()) {
            for (Spawner spawner : spawnerEnvironment) {
                if (spawner.getSprite() != null) {
                    spawner.getSprite().draw(batch);
                    if (!indisposedTextures.contains(spawner.getTexture())) {
                        indisposedTextures.add(spawner.getTexture());
                    }
                }
            }
        }
        batch.end();

        uiStage.act(deltaTime);
        ((Label)((Table)uiStage.getActors().get(0)).getChild(0)).setText("HP: " + player.getHealth() + "/" + player.getMaxHealth());
        ((Label)((Table)uiStage.getActors().get(0)).getChild(1)).setText("Balance: " + player.getBalance());
        uiStage.draw();
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(gameCamera.combined);
        shapeRenderer.setProjectionMatrix(gameCamera.combined);

        waveTimer.tick(deltaTime);
        if (waveTimer.isElapsed() || spawnerEnvironment.isEmpty()) {
            spawnWave();
        }

        for (Spawner spawner : spawnerEnvironment) {
                spawner.tick(deltaTime);
            spawner.attack(player, projectileEnvironment);
        }
        player.tick(deltaTime);

        moveProjectilesOnScreen(deltaTime);

        checkCollisionsOnScreen();

        removeDeadObjects();

        if (!player.isAlive()) {
            try {
                onPlayerIsDead();
            } catch (Exception e) {
                logger.error(e.getMessage());
                logger.error(e.getStackTrace().toString());
            }
        }

        drawScreen(deltaTime);

        gameCamera.update();
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
        waves.addWave(new SmallAsteroidWave(5, worldWidth / 2, spawnerEnvironment));
        waves.addWave(new BigAsteroidWave(5, worldWidth / 2, spawnerEnvironment));
        waves.addWave(new UfoWave(3, worldHeight / 2, spawnerEnvironment));
        currentWave = waves.getWave(random.nextFloat(0, 1));

        background = new GameObject("Background","Background", new Texture("space_background.jpg"));
        background.setSize(gameViewport.getWorldWidth(), gameViewport.getWorldHeight());
        indisposedTextures.add(background.getTexture());

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

        Button shopButton = new Button(uiSkin);
        shopButton.add(new Image(new Texture("shop.png")));
        indisposedTextures.add(((TextureRegionDrawable)(((Image)(shopButton.getChild(0))).getDrawable())).getRegion().getTexture());

        shopButton.setSize(48, 48);
        shopButton.setPosition(screenWidth - shopButton.getWidth() - 10, screenHeight - shopButton.getHeight() - 10);
        shopButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(ScreenType.SHOP);
            }
        });

        uiStage.addActor(table);
        uiStage.addActor(shopButton);
    }

    public void onPlayerIsDead() throws InterruptedException{
        Thread.sleep(650);
        game.changeScreen(ScreenType.RESTART);
    }

    public void onPlayerIsParalysed() {
        Thread notifyingAboutParalyseThread = new Thread(() -> {
            Label paralyseLabel = new Label("Paralyse", uiSkin);
            paralyseLabel.setAlignment(Align.center);
            paralyseLabel.setFontScale(2);
            paralyseLabel.setPosition(uiViewport.getWorldWidth() / 2 - paralyseLabel.getWidth() / 2, uiViewport.getWorldHeight() / 2 - paralyseLabel.getHeight() / 2 - 100);
            uiStage.addActor(paralyseLabel);

            try { Thread.sleep(1500); }
            catch (InterruptedException e) { logger.error(e.getMessage()); }

            uiStage.getActors().removeValue(paralyseLabel, true);
        });
        notifyingAboutParalyseThread.start();
    }
    //endregion
}
