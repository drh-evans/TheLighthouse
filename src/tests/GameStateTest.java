package tests;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import main.GameState;
import main.World;
import main.Item;

public class GameStateTest {
    @Test
    public void testGameStateConstructor() {
        int initialRoom = 0;
        Map<String, Item> initialItemLocations = Map.of(
            "key", new Item("key", "A small gold key", 1)
        );
        GameState initialGameState = new GameState(initialRoom, initialItemLocations);

        assertEquals(0, initialGameState.currentRoom);
        assertEquals(1, initialGameState.itemLocations.get("key").getLocation());
    }

    @Test 
    public void getCurrentRoomLabelTest() {
        GameState gameState = World.initialiseTestGameState();
        String roomLabel = gameState.getCurrentRoomLabel();

        assertEquals("Shore Path", roomLabel);
    }

    @Test 
    public void doesItemExistTest() {
        GameState gameState = World.initialiseTestGameState();
        assertTrue(gameState.doesItemExist("key"));
    }

    @Test
    public void isItemInCurrentRoom() {
        GameState gameState = World.initialiseTestGameState();
        assertTrue(gameState.isItemInCurrentRoom("apple"));
    }
}
