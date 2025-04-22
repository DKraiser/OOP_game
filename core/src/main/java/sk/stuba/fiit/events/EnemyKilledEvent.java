package sk.stuba.fiit.events;

import sk.stuba.fiit.entities.Player;

public class EnemyKilledEvent {
    private static Player player;

    public static void setPlayer(Player player) {
        EnemyKilledEvent.player = player;
    }

    public static void invokeEvent(int price) {
        if (player != null) {
            player.onEnemyKilled(price);
        }
    }
}
