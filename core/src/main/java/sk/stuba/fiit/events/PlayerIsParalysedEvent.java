package sk.stuba.fiit.events;

import sk.stuba.fiit.screens.GameScreen;

/**
 * This class represents an event that is triggered when the player becomes paralyzed.
 * It notifies the target {@link GameScreen} to handle the paralysis event.
 *
 * The event is static and operates by setting a reference to a {@link GameScreen} instance,
 * which is then used to invoke the `onPlayerIsParalysed()` method when the event is triggered.
 */
public class PlayerIsParalysedEvent {
    private static GameScreen targetScreen;

    /**
     * Sets the GameScreen that will be notified when the player is paralyzed.
     *
     * @param screen The GameScreen instance to be set for event notifications.
     */
    public static void setScreen(GameScreen screen) {
        targetScreen = screen;
    }

    /**
     * Retrieves the current GameScreen that will be notified of the paralysis event.
     *
     * @return The target GameScreen that will handle the paralysis event.
     */
    public static GameScreen getScreen() {
        return targetScreen;
    }

    /**
     * Invokes the player paralysis event, notifying the target GameScreen.
     * This method calls {@link GameScreen#onPlayerIsParalysed()} on the target screen if
     * the screen reference is not null.
     */
    public static void invokeEvent() {
        if (targetScreen != null) {
            targetScreen.onPlayerIsParalysed();
        }
    }
}
