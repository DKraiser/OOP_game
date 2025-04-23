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

/**
 * Main entry point of the game. Extends {@link Game} and implements {@link com.badlogic.gdx.ApplicationListener}.
 * Handles screen transitions, resource management, and player serialization.
 */
public class MyGame extends Game {

    /**
     * Global test mode flag to control conditional logic.
     */
    public static final boolean TESTMODE = true;

    private static Player defaultplayer;
    private static List<Texture> indisposedTextures;

    private SpriteBatch batch;
    private Player player;

    private GameScreen gameScreen;
    private RestartScreen restartScreen;
    private ShopScreen shopScreen;

    /**
     * Returns the current player instance.
     *
     * @return current player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the player instance.
     *
     * @param player the player to set
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Reloads the player by cloning the default player.
     */
    public void reloadPlayer() {
        player = defaultplayer.clone();
    }

    /**
     * Initializes the game: sets up logging, loads the default player, attempts to deserialize player data,
     * and creates all game screens.
     */
    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        indisposedTextures = new ArrayList<>();
        defaultplayer = new Player(
            "P", "Player", new Texture("earth.png"), 1,
            null, 5, 5, 1,
            new Timer(10), 0,
            new BasicPlayerWeaponFactory()
        );

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

    /**
     * Delegates rendering to the currently active screen.
     */
    @Override
    public void render() {
        super.render();
    }

    /**
     * Disposes of all disposable resources (textures, player, sprite batch).
     */
    @Override
    public void dispose() {
        for (Texture texture : indisposedTextures) {
            texture.dispose();
        }
        player.dispose();
        batch.dispose();
    }

    /**
     * Deserializes the player object from a file.
     *
     * @return the deserialized {@link Player}
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class of the serialized object cannot be found
     */
    private Player deserializePlayer() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("serialized/player.json");
        ObjectInputStream ois = new ObjectInputStream(fis);
        Player deserialiazedPlayer = (Player) ois.readObject();
        ois.close();
        fis.close();
        return deserialiazedPlayer;
    }

    /**
     * Changes the current screen based on the given {@link ScreenType}.
     *
     * @param screenType the screen type to switch to
     */
    public void changeScreen(ScreenType screenType) {
        setScreen(switch (screenType) {
            case GAME -> gameScreen;
            case SHOP -> shopScreen;
            case RESTART -> restartScreen;
        });
    }

    /**
     * Reloads the specified screen by disposing and recreating it.
     *
     * @param screenType the screen type to reload
     */
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
