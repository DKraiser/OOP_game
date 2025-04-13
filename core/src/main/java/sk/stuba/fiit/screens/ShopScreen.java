package sk.stuba.fiit.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import sk.stuba.fiit.MyGame;

public class ShopScreen implements Screen {

    private final MyGame game;
    private Stage stage;
    private Skin skin;

    private SpriteBatch batch;
    private Sprite background;

    // Улучшения
    private final EffectUpgrade healthEffectUpgrade;
    private final EffectUpgrade regenEffectUpgrade;
    private final EffectUpgrade resistanceEffectUpgrade;

    public ShopScreen(MyGame game, SpriteBatch batch) {
        this.game = game;
        this.batch = batch;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        this.skin = new Skin(Gdx.files.internal("uiskin.json")); // стандартный skin
        background = new Sprite(new Texture("space_background.jpg"));
        background.setSize(stage.getViewport().getScreenWidth(), stage.getViewport().getScreenHeight());

        // Настройка улучшений
        healthEffectUpgrade = new EffectUpgrade("Extra Health", new int[]{100, 200, 300, 400, 500});
        regenEffectUpgrade = new EffectUpgrade("Regeneration", new int[]{150, 250, 350, 450, 600});
        resistanceEffectUpgrade = new EffectUpgrade("Resistance", new int[]{120, 220, 320, 420, 520});

        setupUI();
    }

    // Sets up the visual part of the shop: labels and buttons
    private void setupUI() {
        VerticalGroup verticalGroup = new VerticalGroup();
        Table table = new Table();
        verticalGroup.setFillParent(true);
        verticalGroup.center();
        verticalGroup.space(15);
        stage.addActor(verticalGroup);

        Label titleLabel = new Label("Effect Upgrade Shop", skin);
        verticalGroup.addActor(titleLabel);
        table.row();
        table.center();
        verticalGroup.addActor(table);

        addEffectUpgradeRow(table, healthEffectUpgrade);
        addEffectUpgradeRow(table, regenEffectUpgrade);
        addEffectUpgradeRow(table, resistanceEffectUpgrade);
    }

    // Adds one row per effectUpgrade: label, level indicator, and button
    private void addEffectUpgradeRow(Table table, EffectUpgrade effectUpgrade) {
        Label nameLabel = new Label(effectUpgrade.name, skin);
        Label levelLabel = new Label("Level: " + effectUpgrade.level, skin);
        TextButton buyButton = new TextButton("Buy (" + effectUpgrade.getCurrentCost() + ")", skin);

        buyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (effectUpgrade.level < 5) {
                    // NOTE: Here you'd check if the player has enough money
                    effectUpgrade.level++;
                    levelLabel.setText("Level: " + effectUpgrade.level);

                    if (effectUpgrade.level < 5) {
                        buyButton.setText("Buy (" + effectUpgrade.getCurrentCost() + ")");
                    } else {
                        buyButton.setText("Max Level");
                        buyButton.setDisabled(true); // Disable the button if maxed
                    }
                }
            }
        });

        table.add(nameLabel).padRight(20);
        table.add(levelLabel).padRight(20);
        table.add(buyButton).padBottom(20).row();
    }

    @Override
    public void show() { }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(stage.getCamera().combined);
        batch.begin();
        background.draw(batch);
        batch.end();
        stage.act(delta);
        stage.draw();
    }
    //region
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        background.setSize(stage.getViewport().getScreenWidth(), stage.getViewport().getScreenHeight());
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
    //endregion

    // Вложенный класс для улучшений
    public static class EffectUpgrade {
        public String name;
        public int[] costs;
        public int level;

        public EffectUpgrade(String name, int[] costs) {
            this.name = name;
            this.costs = costs;
            this.level = 0;
        }

        public int getCurrentCost() {
            return level < costs.length ? costs[level] : 0;
        }
    }
}
