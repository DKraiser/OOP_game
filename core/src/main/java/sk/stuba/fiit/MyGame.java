package sk.stuba.fiit;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import sk.stuba.fiit.entities.player.Player;
import sk.stuba.fiit.factories.weaponfactories.BasicPlayerWeaponFactory;
import sk.stuba.fiit.screens.GameScreen;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MyGame extends Game {
    public static final boolean TESTMODE = false;
    private static Player defaultplayer;
    private static List<Texture> undisposedTextures;
    private SpriteBatch batch;
    private Player player;

    @Override
    public void create() {
        undisposedTextures = new ArrayList<Texture>();
        defaultplayer = new Player("P", "Player", new Texture("earth.png"), 1, null, 5, 5, 1, new Timer(10), 0, new BasicPlayerWeaponFactory());
        batch = new SpriteBatch();
        try {
            player = deserializePlayer();
            player.setLogger(new Logger("Player", Logger.INFO));
        } catch (Exception e) {
            System.out.println("Couldn't deserialize player");
            player = defaultplayer;
        }
        setScreen(new GameScreen(this, player, batch,undisposedTextures));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        for (Texture texture : undisposedTextures) {
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
}
