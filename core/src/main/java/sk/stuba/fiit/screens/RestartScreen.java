package sk.stuba.fiit.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.lwjgl.opengl.GL20;
import sk.stuba.fiit.MyGame;
import sk.stuba.fiit.enums.ScreenType;

import java.util.List;

/**
 * Represents the screen shown when the player loses the game.
 * Provides options to restart the game or navigate to other screens.
 */
public class RestartScreen implements Screen {

    private MyGame game;
    private SpriteBatch batch;
    private Sprite background;
    private List<Texture> indisposedTextures;

    private Camera uiCamera;
    private Viewport uiViewport;
    private Stage uiStage;
    private Skin uiSkin;
    private InputMultiplexer uiInputMultiplexer;

    /**
     * Constructs a RestartScreen.
     *
     * @param game              The game instance.
     * @param batch             The SpriteBatch used for rendering.
     * @param indisposedTextures List of textures that have already been disposed.
     */
    public RestartScreen(MyGame game, SpriteBatch batch, List<Texture> indisposedTextures) {
        this.game = game;
        this.batch = batch;
        this.indisposedTextures = indisposedTextures;

        uiCamera = new OrthographicCamera();
        uiViewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), uiCamera);
    }

    /**
     * Initializes the restart screen by setting up the background, UI stage, and input processing.
     */
    @Override
    public void show() {
        background = new Sprite(new Texture("space_background.jpg"));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (!indisposedTextures.contains(background.getTexture())) {
            indisposedTextures.add(background.getTexture());
        }

        batch.setProjectionMatrix(uiCamera.combined);
        uiStage = new Stage(uiViewport, batch);
        uiSkin = new Skin(Gdx.files.internal("uiskin.json"));
        uiInputMultiplexer = new InputMultiplexer(uiStage);
        Gdx.input.setInputProcessor(uiInputMultiplexer);

        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(new SpriteDrawable(background));
        table.align(Align.center);

        Label loseLabel = new Label("You lose!", uiSkin);
        loseLabel.setFontScale(3);
        Label restartLabel = new Label("Do you want to restart?", uiSkin);
        restartLabel.setFontScale(3);

        Button restartButton = new TextButton("Restart", uiSkin);
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.reloadPlayer();
                game.reloadScreen(ScreenType.GAME);
                game.reloadScreen(ScreenType.SHOP);
                game.changeScreen(ScreenType.GAME);
            }
        });

        table.add(loseLabel).row();
        table.add(restartLabel).row();
        table.add(restartButton).row();

        uiStage.addActor(table);
    }

    /**
     * Renders the restart screen, drawing the background and UI elements.
     *
     * @param deltaTime The time elapsed since the last render.
     */
    @Override
    public void render(float deltaTime) {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        background.draw(batch);
        batch.end();

        uiCamera.update();
        uiStage.act();
        uiStage.draw();
    }

    /**
     * Adjusts the screen size when the window is resized.
     *
     * @param width  The new width of the screen.
     * @param height The new height of the screen.
     */
    @Override
    public void resize(int width, int height) {
        uiViewport.update(width, height, true);
        background.setSize(width, height);
    }

    /**
     * Pauses the screen, stopping any ongoing processes.
     */
    @Override
    public void pause() {
    }

    /**
     * Resumes the screen after it was paused.
     */
    @Override
    public void resume() {
    }

    /**
     * Hides the screen, cleaning up resources if necessary.
     */
    @Override
    public void hide() {
    }

    /**
     * Disposes of the screen resources when the screen is no longer needed.
     */
    @Override
    public void dispose() {
        uiStage.dispose();
        uiSkin.dispose();
    }
}
