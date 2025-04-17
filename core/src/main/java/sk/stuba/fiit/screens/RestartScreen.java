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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.lwjgl.opengl.GL20;
import sk.stuba.fiit.MyGame;
import sk.stuba.fiit.enums.ScreenType;


public class RestartScreen implements Screen {
    private MyGame game;
    private SpriteBatch batch;
    private Sprite background;
    private Camera uiCamera;
    private Viewport uiViewport;
    private Stage uiStage;
    private Skin uiSkin;
    private InputMultiplexer uiInputMultiplexer;

    public RestartScreen(MyGame game, SpriteBatch batch) {
        this.game = game;
        this.batch = batch;
        uiCamera = new OrthographicCamera();
        uiViewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), uiCamera);
    }

    @Override
    public void show() {
        background = new Sprite(new Texture("space_background.jpg"));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.setProjectionMatrix(uiCamera.combined);
        uiStage = new Stage(uiViewport, batch);
        uiSkin = new Skin(Gdx.files.internal("uiskin.json"));
        uiInputMultiplexer = new InputMultiplexer(uiStage);
        Gdx.input.setInputProcessor(uiInputMultiplexer);

        Table table = new Table();
        table.setFillParent(true);
        table.align(Align.center);
        Label loseLabel = new Label("You lose!", uiSkin);
        loseLabel.setFontScale(3);
        Label restartLabel = new Label("Do you want to restart?", uiSkin);
        restartLabel.setFontScale(3);
        Button restartButton = new TextButton("Restart", uiSkin);
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.reloadScreen(ScreenType.GAME);
                game.changeScreen(ScreenType.GAME);
            }
        });
        table.add(loseLabel).row();
        table.add(restartLabel).row();
        table.add(restartButton).row();
        uiStage.addActor(table);
    }

    @Override
    public void render(float v) {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        background.draw(batch);
        batch.end();

        uiCamera.update();
        uiStage.act();
        uiStage.draw();
    }

    @Override
    public void resize(int i, int i1) {

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
        uiStage.dispose();
        uiSkin.dispose();
    }
}
