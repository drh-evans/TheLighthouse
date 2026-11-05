package main;

import java.util.Map;
import java.util.List;

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

    static int[][] doorMap = {
        // -1 = no exit
        // 0 = unlocked exit
        // 1 = locked exit
        // N, E, S, W, U, D
        {1, 0, -1, -1, -1, -1},     // Room 0 Shore Path
        {-1, -1, -1, 0, -1, -1},    // Room 1 Keeper's Cottage
        {-1, 0, 0, -1, 0, -1},      // Room 2 Base of the Tower
        {-1, -1, -1, 0, -1, -1},    // Room 3 Store Room
        {-1, -1, -1, -1, 0, 0},     // Room 4 Spiral Stair
        {-1, -1, -1, -1, 0, 0},     // Room 5 Watch Room
        {-1, -1, -1, -1, -1, 0},    // Room 6 Lamp Room
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

    public static final String[] roomDescriptions = {
        "You are on a cold windy shore path. You can see the door to the lighthouse to your north but it is locked, and to the east you can see a dimly lit cottage.\n",
        "You have entered the keeper's cottage, it's pleasantly warm and inviting compared to the storm outside.\nThe Keeper is sat in the corner by the fireplace, maybe he has something useful for you.\n",
        "You are at the base of the lighthouse tower. You notice an archway to your north that leads to a staircase, and behind you to the south is a door that leads back to the shore path. You also notice a rotting door to your east, where could that lead?\n",
        "You have entered the store room through the door, which is now to your east.\nA strong smell of fumes fills the air and you notice a stash of old supplies and equipment.\n",
        "You are on the spiral staircase. There is an archway to the south that leads back to the base of the tower, and a door up that leads to the next floor.\n",
        "Exhausted, you've entered the watch room. You notice a small rusting ladder, infested with bugs and cobwebs and a rusty generator that hasn't seen power in years.\n",
        "You have made it to the lamp room, the top of the tower. You feel the wind push against you as it blows through the cracked glass and you can see the waves roaring below you as the boat continues towards land, you notice that the lamp is not working as it could use with some fuel to light it up.\n"
    };

    public static final String[] directionNames = {
        "north",
        "east",
        "south",
        "west",
        "up",
        "down"
    };

    // For initialising gameState in App.java
    // TODO: Move into initialise function
    private static int initialRoom = 0;

    private static Map<String, Item> initialItemLocations = Map.of(
        "lantern", new Item("lantern", "A bright lantern", 0),
        "key", new Item("key", "A small gold key", -2),
        "oil can", new Item("oil can", "A greasy can full of oil", 3)
    );

    // To pass copy of door array for gameState
    private static int[][] copy2dArray(int[][] nestedArr) {
        int[][] resultArr = new int[nestedArr.length][];
        for (int i = 0; i < nestedArr.length; i++) {
            if (nestedArr[i] != null) {
                resultArr[i] = nestedArr[i].clone();
            }
        }
        return resultArr;
    }

    private static Map<String, Character> characters = Map.of(
        "keeper", new Character("Keeper", 1, List.of("key"), "lantern")
    );

    public static GameState initialiseGameState() {
        return new GameState(initialRoom, initialItemLocations, copy2dArray(doorMap), characters);
    }

    // For initialising gameState for testing
    public static GameState initialiseTestGameState() {
        // Test vals
        int initialRoom = 0;

        Map<String, Item> initialItemLocations = Map.of(
            "lantern", new Item("lantern", "A bright lantern", 0),
            "apple", new Item("apple", "A crisp red apple", 0), // Location -1 = Inventory
            "key", new Item("key", "A small gold key", -2)
        );

        int[][] testDoorMap = {
            {1, 0, -1, -1, -1, -1},
            {-1, -1, -1, 0, -1, -1},
            {-1, 0, 0, -1, 0, -1},
            {-1, -1, -1, 0, -1, -1},
            {-1, -1, -1, -1, 0, 0},
            {-1, -1, -1, -1, 0, 0},     
            {-1, -1, -1, -1, -1, 0},
        };

        Map<String, Character> testCharacters = Map.of(
            "keeper", new main.Character("Keeper", 1, List.of("key"), "lantern")
        );

        return new GameState(initialRoom, initialItemLocations, testDoorMap, testCharacters);
    }

    public static int getInitialRoom() {
        return initialRoom;
    }

    public static Map<String, Item> getInitialItemLocations() {
        return initialItemLocations;
    }

    public static Map<String, Character> getCharacters() {
        return characters;
    }

    public static String printMap(int currentRoom) {
    String[] m = new String[7];
    for (int i = 0; i < m.length; i++) {
        m[i] = (i == currentRoom) ? "[X]" : "[ ]";
    }

    return """
    
                    %s Lamp Room

                           |
                           |

                    %s Watch Room

                           |
                           |

                    %s Spiral Stair
                    
                           |
                           |

                    %s Base of the Tower ---  %s Store Room

                           |
                           |

                    %s Shore Path --- %s Keeper's Cottage

        [X] = You are here
        """.formatted(m[6], m[5], m[4], m[2], m[3], m[0], m[1]);
}
}
