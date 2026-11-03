package main;

import java.util.Map;

public class World {
    static final int[][] map = {
       // N, E, S, W, U, D
        {2, 1, -1, -1, -1, -1},     // Room 0 Shore Path
        {-1, -1, -1, 0, -1, -1},    // Room 1 Keeper's Cottage
        {-1, 3, 0, -1, 4, -1},      // Room 2 Base of the Tower
        {-1, -1, -1, 2, -1, -1},     // Room 3 Store Room
        {-1, -1, -1, -1, 5, 2},       // Room 4 Spiral Stair
        {-1, -1, -1, -1, 6, 4},       // Room 5 Watch Room
        {-1, -1, -1, -1, -1, 5},      // Room 6 Lamp Room
    };

    public static final String[] roomNames = {
        "Shore Path",
        "Keeper's Cottage",
        "Base of the Tower",
        "Store Room",
        "Spiral Stair",
        "Watch Room",
        "Lamp Room"
    };

    // For initialising gameState in App.java
    private static int initialRoom = 0;
    private static Map<String, Item> initialItemLocations = Map.of(
        "key", new Item("key", "A small gold key", 1)
    );

    public static GameState initialiseGameState() {
        return new GameState(initialRoom, initialItemLocations);
    }

    // For initialising gameState for testing
    public static GameState initialiseTestGameState() {
        // Test vals
        int initialRoom = 0;
        Map<String, Item> initialItemLocations = Map.of(
            "apple", new Item("apple", "A crisp red apple", 0),
            "key", new Item("key", "A small gold key", 1)
        );
        return new GameState(initialRoom, initialItemLocations);
    }

    public static int getInitialRoom() {
        return initialRoom;
    }

    public static Map<String, Item> getInitialItemLocations() {
        return initialItemLocations;
    }
}
