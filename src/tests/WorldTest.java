package tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;
import main.GameState;
import main.World;

public class WorldTest {
    @Test 
    void initialiseGameStateHappyPath() {
        int initialRoom = World.getInitialRoom();
        int initialKeyLocation = World.getInitialItemLocations().get("key");
        GameState initialGameState = World.initialiseGameState();

        assertEquals(initialRoom, initialGameState.currentRoom);
        assertEquals(initialKeyLocation, initialGameState.itemLocations.get("key"));
    }

    @Test 
    void initialiseTestGameStateHappyPath() {
        GameState testGameState = World.initialiseTestGameState();

        assertEquals(0, testGameState.currentRoom);
        assertEquals(1, testGameState.itemLocations.get("key"));
    }
}
