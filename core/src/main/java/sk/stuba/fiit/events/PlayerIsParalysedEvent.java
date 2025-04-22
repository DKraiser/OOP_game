package sk.stuba.fiit.events;

import sk.stuba.fiit.screens.GameScreen;

public class PlayerIsParalysedEvent {
    private static GameScreen targetScreen;

    public static void setScreen(GameScreen screen) {
        targetScreen = screen;
    }

    public static GameScreen getScreen() {
        return targetScreen;
    }

    public static void invokeEvent() {
        if (targetScreen != null) {
            targetScreen.onPlayerIsParalysed();
        }
    }
}
