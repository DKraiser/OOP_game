package sk.stuba.fiit.waves;

import com.badlogic.gdx.math.Vector2;
import sk.stuba.fiit.entities.Spawner;
import sk.stuba.fiit.factories.enemyfactories.SmallAsteroidSpawnerFactory;
import sk.stuba.fiit.factories.enemyfactories.SpawnerFactory;
import com.badlogic.gdx.utils.Logger;
import sk.stuba.fiit.interfaces.Wave;
import sk.stuba.fiit.screens.GameScreen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class SmallAsteroidWave implements Wave {
    int waveSize;
    float radius;
    Collection<Spawner> summons;
    Collection<Spawner> spawnerEnvironment;
    SpawnerFactory spawnerFactory;
    Random random;
    Logger logger;

    @Override
    public void summon() {
        float x, y;
        Spawner spawner;
        for (int i = 0; i < waveSize; i++) {
            spawner = spawnerFactory.create();

            x = random.nextFloat(-1, 1) * radius;
            y = (float)Math.sqrt(radius * radius - x * x);

            x += GameScreen.screenWidth / 2;
            y += GameScreen.screenHeight / 2;
            y *= (float)Math.pow(-1, random.nextInt(0, 3));

            spawner.setPosition(x, y);
            spawner.translate(new Vector2(spawner.getWidth(), spawner.getHeight()).scl(-0.5f));
            spawner.getWeapon().update(spawner.getPosition(), spawner.getHeight() / 2);

            summons.add(spawner);
            logger.debug("Spawner " + i + " position: " + spawner.getPosition());
        }
        spawnerEnvironment.addAll(summons);
    }

    @Override
    public void retract() {
        spawnerEnvironment.removeAll(summons);
        summons.clear();
    }

    public SmallAsteroidWave(int waveSize, float radius, Collection<Spawner> spawnerEnvironment) {
        this.waveSize = waveSize;
        this.radius = radius;
        this.spawnerEnvironment = spawnerEnvironment;

        spawnerFactory = new SmallAsteroidSpawnerFactory();
        summons = new ArrayList<>();
        random = new Random();
        random.setSeed(System.currentTimeMillis());
        logger = new Logger("SmallAsteroidWave", Logger.DEBUG);
    }
}
