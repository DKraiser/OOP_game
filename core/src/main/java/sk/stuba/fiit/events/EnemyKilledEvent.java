package sk.stuba.fiit.events;

import sk.stuba.fiit.entities.Player;

/**
 * This class represents an event that is triggered when an enemy is killed.
 * It notifies the player about the kill and updates the player's state accordingly.
 *
 * The event is static and operates by setting a reference to a {@link Player} instance,
 * which is then used to invoke the `onEnemyKilled()` method when the event is triggered.
 */
public class EnemyKilledEvent {
    private static Player player;

    /**
     * Sets the player who will be notified when the enemy is killed.
     *
     * @param player The player instance to be set for event notifications.
     */
    public static void setPlayer(Player player) {
        EnemyKilledEvent.player = player;
    }

    /**
     * Invokes the enemy killed event, notifying the player with the reward price.
     * This method calls {@link Player#onEnemyKilled(int)} on the player if the player
     * reference is not null, providing the price of the killed enemy as an argument.
     *
     * @param price The reward or price associated with killing the enemy.
     */
    public static void invokeEvent(int price) {
        if (player != null) {
            player.onEnemyKilled(price);
        }
    }
}
