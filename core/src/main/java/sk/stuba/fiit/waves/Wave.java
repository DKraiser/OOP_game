package sk.stuba.fiit.waves;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;
import sk.stuba.fiit.entities.Spawner;
import sk.stuba.fiit.enums.WaveRarity;
import sk.stuba.fiit.factories.spawnerfactories.SpawnerFactory;
import sk.stuba.fiit.screens.GameScreen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public abstract class Wave {
    protected int waveSize;
    protected float radius;
    protected Vector2 spawnPosition;
    protected WaveRarity waveRarity;
    protected Spawner newSpawner;
    protected Collection<Spawner> summons;
    protected Collection<Spawner> spawnerEnvironment;
    protected SpawnerFactory spawnerFactory;
    protected Random random;
    protected Logger logger;

    public void summon() {
        float x;
        for (int i = 0; i < waveSize; i++) {
            newSpawner = spawnerFactory.create();

            x = random.nextFloat(-1, 1) * radius;
            spawnPosition = new Vector2(x, (float)(Math.sqrt(radius * radius - x * x) * Math.pow(-1, random.nextInt(0, 3))));
            spawnPosition.add(new Vector2(GameScreen.worldWidth, GameScreen.worldHeight).scl(0.5f));

            newSpawner.setPosition(spawnPosition.cpy());
            newSpawner.getWeapon().update(spawnPosition, newSpawner.getHeight() / 2);
            newSpawner.translate(new Vector2(newSpawner.getWidth(), newSpawner.getHeight()).scl(-0.5f));

            if (newSpawner.getCollider() != null) {
                newSpawner.getCollider().setPosition(spawnPosition.cpy());
            }

            summons.add(newSpawner);
            logger.debug(newSpawner.getName() + " " + i + " position: " + newSpawner.getPosition());
        }
        spawnerEnvironment.addAll(summons);
    }

    public void retract() {
        for (Spawner spawner : summons) {
            spawner.setAlive(false);
        }
        summons.clear();
    }

    public WaveRarity getRarity() {
        return waveRarity;
    }

    public Wave(int waveSize, float radius, Collection<Spawner> spawnerEnvironment) {
        this.waveSize = waveSize;
        this.radius = radius;
        this.spawnerEnvironment = spawnerEnvironment;

            summons = new ArrayList<>();
        random = new Random();
        random.setSeed(System.currentTimeMillis());
        logger = new Logger(this.getClass().getName(), Logger.DEBUG);
    }
}
