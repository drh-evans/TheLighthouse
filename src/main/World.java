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

    // For initialising gameState in App.java
    private static int initialRoom = 0;
    private static Map<String, Integer> initialItemLocations = Map.of(
        "key", 1
    );

    public static GameState initialiseGameState() {
        return new GameState(initialRoom, initialItemLocations);
    }

    public static final String[] roomNames = {
        "Shore Path",
        "Keeper's Cottage",
        "Base of the Tower",
        "Store Room",
        "Spiral Stair",
        "Watch Room",
        "Lamp Room"
    };
}
