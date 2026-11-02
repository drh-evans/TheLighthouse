package main;

public class Actions {
    public static GameState executeAction(GameState gameState, String verb, String rest) {
        return switch (verb) {
            case "move" -> move(gameState, rest);
            default -> gameState;
        };
    }

    static GameState move(GameState gameState, String rest) {
        gameState.currentRoom = 1;
        return gameState;
    }
}
