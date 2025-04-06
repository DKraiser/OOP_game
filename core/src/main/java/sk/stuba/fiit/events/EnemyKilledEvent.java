package sk.stuba.fiit.events;

import sk.stuba.fiit.entities.Entity;
import sk.stuba.fiit.entities.Spawner;
import sk.stuba.fiit.entities.player.Player;
import sk.stuba.fiit.projectiles.EnemyProjectile;

public class EnemyKilledEvent {
    private static Player player;

    public static void setPlayer(Player player) {
        EnemyKilledEvent.player = player;
    }

    public static void invokeEvent(int price) {
        player.onEnemyKilled(price);
    }
}
