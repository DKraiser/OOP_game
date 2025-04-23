package sk.stuba.fiit;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import sk.stuba.fiit.entities.Player;
import sk.stuba.fiit.enums.ScreenType;
import sk.stuba.fiit.factories.weaponfactories.BasicPlayerWeaponFactory;
import sk.stuba.fiit.screens.GameScreen;
import sk.stuba.fiit.screens.RestartScreen;
import sk.stuba.fiit.screens.ShopScreen;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MyGame extends Game {
    public static final boolean TESTMODE = true;
    private static Player defaultplayer;
    private static List<Texture> indisposedTextures;
    private SpriteBatch batch;
    private Player player;

    private GameScreen gameScreen;
    private RestartScreen restartScreen;
    private ShopScreen shopScreen;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void reloadPlayer() {
        player = defaultplayer.clone();
    }

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        indisposedTextures = new ArrayList<Texture>();
        defaultplayer = new Player("P", "Player", new Texture("earth.png"), 1, null, 5, 5, 1, new Timer(10), 10000, new BasicPlayerWeaponFactory());
        batch = new SpriteBatch();
        try {
            player = deserializePlayer();
            player.setLogger(new Logger("Player", Logger.INFO));
        } catch (Exception e) {
            System.out.println("Couldn't deserialize player");
            e.printStackTrace();
            reloadPlayer();
        }

        gameScreen = new GameScreen(this, player, batch, indisposedTextures);
        shopScreen = new ShopScreen(this, player, batch, indisposedTextures);
        restartScreen = new RestartScreen(this, batch, indisposedTextures);
        setScreen(gameScreen);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        for (Texture texture : indisposedTextures) {
            texture.dispose();
        }
        player.dispose();
        batch.dispose();
    }

    private Player deserializePlayer() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("serialized/player.json");
        ObjectInputStream ois = new ObjectInputStream(fis);
        Player deserialiazedPlayer = (Player) ois.readObject();
        ois.close();
        fis.close();
        return deserialiazedPlayer;
    }

    public void changeScreen(ScreenType screenType) {
        setScreen(switch(screenType) {
            case GAME -> gameScreen;
            case SHOP -> shopScreen;
            case RESTART -> restartScreen;
        });
    }

    public void reloadScreen(ScreenType screenType) {
        switch (screenType) {
            case GAME:
                gameScreen.dispose();
                gameScreen = new GameScreen(this, player, batch, indisposedTextures);
                break;
            case SHOP:
                shopScreen.dispose();
                shopScreen = new ShopScreen(this, player, batch, indisposedTextures);
                break;
            case RESTART:
                restartScreen.dispose();
                restartScreen = new RestartScreen(this, batch, indisposedTextures);
                break;
        }
    }
}
