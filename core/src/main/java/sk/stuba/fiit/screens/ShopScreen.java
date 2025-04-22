package sk.stuba.fiit.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.Viewport;
import sk.stuba.fiit.EffectUpgrade;
import sk.stuba.fiit.MyGame;
import sk.stuba.fiit.effects.ExtraHealthEffect;
import sk.stuba.fiit.effects.RegenerationEffect;
import sk.stuba.fiit.effects.ResistanceEffect;
import sk.stuba.fiit.entities.Player;
import sk.stuba.fiit.enums.ScreenType;

import java.util.List;

public class ShopScreen implements Screen {
    private final MyGame game;
    private SpriteBatch batch;
    private Sprite background;
    private Player player;
    private List<Texture> indisposedTextures;

    private OrthographicCamera uiCamera;
    private Viewport uiViewport;
    private Stage uiStage;
    private Skin uiSkin;

    private Logger logger;

    private final EffectUpgrade healthEffectUpgrade;
    private final EffectUpgrade regenEffectUpgrade;
    private final EffectUpgrade resistanceEffectUpgrade;

    public ShopScreen(MyGame game, Player player, SpriteBatch batch, List<Texture> indisposedTextures) {
        this.game = game;
        this.player = player;
        this.batch = batch;
        this.indisposedTextures = indisposedTextures;

        logger = new Logger("Shop Screen", Logger.INFO);

        uiCamera = new OrthographicCamera();
        uiViewport = new FitViewport(800, 600, uiCamera);

        healthEffectUpgrade = new EffectUpgrade("Extra Health", new int[]{100, 200, 300, 400, 500});
        healthEffectUpgrade.setUpgrade(() -> {
            player.takeEffect(new ExtraHealthEffect(healthEffectUpgrade.getLevel(), false, 1, 1, player));
            logger.info("Health Upgrade was purchased");
        });
        regenEffectUpgrade = new EffectUpgrade("Regeneration", new int[]{100, 250, 350, 450, 600});
        regenEffectUpgrade.setUpgrade(() -> {
            player.takeEffect(new RegenerationEffect(regenEffectUpgrade.getLevel(), false, 1, 1, player));
            logger.info("Regeneration Upgrade was purchased");
        });

        resistanceEffectUpgrade = new EffectUpgrade("Resistance", new int[]{120, 220, 320, 420, 520});
        resistanceEffectUpgrade.setUpgrade(() -> {
            player.takeEffect(new ResistanceEffect(resistanceEffectUpgrade.getLevel(), false, 1, 1, player));
            logger.info("Resistance Upgrade was purchased");
        });
    }

    private void addEffectUpgradeRow(Table table, EffectUpgrade effectUpgrade) {
        Label nameLabel = new Label(effectUpgrade.getName(), uiSkin);
        Label levelLabel = new Label("Level: " + effectUpgrade.getLevel(), uiSkin);
        TextButton buyButton = new TextButton("Buy (" + effectUpgrade.getCurrentCost() + ")", uiSkin);

        buyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (effectUpgrade.getLevel() < 5) {
                    if (player.getBalance() < effectUpgrade.getCurrentCost()) return;

                    player.setBalance(player.getBalance() - effectUpgrade.getCurrentCost());
                    effectUpgrade.setLevel(effectUpgrade.getLevel() + 1);
                    levelLabel.setText("Level: " + effectUpgrade.getLevel());

                    if (effectUpgrade.getLevel() < 5) {
                        buyButton.setText("Buy (" + effectUpgrade.getCurrentCost() + ")");
                    } else {
                        buyButton.setText("Max Level");
                        buyButton.setDisabled(true);
                    }
                    effectUpgrade.getUpgrade().run();
                }
            }
        });

        table.add(nameLabel).padRight(20);
        table.add(levelLabel).padRight(20);
        table.add(buyButton).padBottom(20).row();
    }

    @Override
    public void show() {
        background = new Sprite(new Texture("space_background.jpg"));
        background.setSize(uiViewport.getWorldWidth(), uiViewport.getWorldHeight());

        uiStage = new Stage(uiViewport);
        uiSkin = new Skin(Gdx.files.internal("uiskin.json"));
        Gdx.input.setInputProcessor(uiStage);

        VerticalGroup verticalGroup = new VerticalGroup();
        Table table = new Table();
        verticalGroup.setFillParent(true);
        verticalGroup.center();
        verticalGroup.space(15);

        Label titleLabel = new Label("Effect Upgrade Shop", uiSkin);
        verticalGroup.addActor(titleLabel);
        table.row();
        table.center();
        verticalGroup.addActor(table);

        addEffectUpgradeRow(table, healthEffectUpgrade);
        addEffectUpgradeRow(table, regenEffectUpgrade);
        addEffectUpgradeRow(table, resistanceEffectUpgrade);

        Button exitButton = new Button(uiSkin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(ScreenType.GAME);
            }
        });
        exitButton.add(new Image(new Texture("exit.png")));
        indisposedTextures.add(((TextureRegionDrawable)(((Image)(exitButton.getChild(0))).getDrawable())).getRegion().getTexture());

        exitButton.setSize(48, 48);
        exitButton.setPosition(uiViewport.getWorldWidth() - exitButton.getWidth() - 10, uiViewport.getWorldHeight() - exitButton.getHeight() - 10);

        uiStage.addActor(verticalGroup);
        uiStage.addActor(exitButton);
    }

    @Override
    public void render(float deltaTime) {
        batch.setProjectionMatrix(uiCamera.combined);
        batch.begin();
        background.draw(batch);
        batch.end();
        uiStage.act(deltaTime);
        uiStage.draw();
    }

    //region
    @Override
    public void resize(int width, int height) {
        uiViewport.update(width, height, true);
        background.setSize(uiViewport.getWorldWidth(), uiViewport.getWorldHeight());
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        if (uiStage != null) {
            uiStage.dispose();
            uiSkin.dispose();
        }
    }
    //endregion
}
