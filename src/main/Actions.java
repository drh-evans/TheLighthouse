package main;

public class Actions {
    public static boolean executeAction(GameState gameState, String verb, String rest) {
        return switch (verb) {
            case "move" -> move(gameState, rest);
            default -> false;
        };
    }

    static boolean move(GameState gameState, String rest) {
        System.out.println("Current room: " + gameState.currentRoom);
        return true; // Need implementation
    }
}
