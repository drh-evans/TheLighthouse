package tests;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import main.GameState;
import main.World;

public class GameStateTest {
    @Test
    public void testGameStateConstructor() {
        int initialRoom = 0;
        Map<String, Integer> initialItemLocations = Map.of(
            "key", 1
        );
        GameState initialGameState = new GameState(initialRoom, initialItemLocations);

        assertEquals(0, initialGameState.currentRoom);
        assertEquals(1, initialGameState.itemLocations.get("key"));
    }

    @Test
    public void isItemInCurrentRoom() {
        GameState gameState = World.initialiseTestGameState();
        assertTrue(gameState.isItemInCurrentRoom("apple"));
    }
}
