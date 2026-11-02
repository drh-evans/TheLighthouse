package tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;
import main.GameState;

public class WorldTest {
    @Test 
    void initialiseGameStateHappyPath() {
        int initialRoom = 0;
        Map<String, Integer> initialItemLocations = Map.of(
            "key", 1
        );
        GameState initialGameState = new GameState(initialRoom, initialItemLocations);

        assertEquals(0, initialGameState.currentRoom);
        assertEquals(1, initialGameState.itemLocations.get("key"));
    }
}
