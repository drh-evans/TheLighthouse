package tests;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import main.GameState;
import main.World;
import main.Item;
import main.Actions;

public class GameStateTest {
    @Test
    public void testGameStateConstructor() {
        int initialRoom = 0;
        Map<String, Item> initialItemLocations = Map.of(
            "key", new Item("key", "A small gold key", 1)
        );
        int[][] doorMap = {
            {1, 0, -1, -1, -1, -1},
            {-1, -1, -1, 0, -1, -1},
            {-1, 0, 0, -1, -1, -1},
        };
        GameState initialGameState = new GameState(initialRoom, initialItemLocations, doorMap);

        assertEquals(0, initialGameState.currentRoom);
        assertEquals(1, initialGameState.itemLocations.get("key").getLocation());
        assertEquals(1, initialGameState.doorStates[0][0]);
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

    @Test
    public void doesInventoryContainTest() {
        GameState gameState = World.initialiseTestGameState();
        assertFalse(gameState.doesInventoryContain("key"));

        Actions.executeAction(gameState, "move", "east");
        Actions.executeAction(gameState, "take", "key");

        assertTrue(gameState.doesInventoryContain("key"));
    }

    @Test 
    public void isLockedDoorTest() {
        GameState gameState = World.initialiseTestGameState();

        assertTrue(gameState.isLockedDoor(0));
    }

    @Test 
    public void findLockedDoorForCurrentRoomTest() {
        GameState gameState = World.initialiseTestGameState();

        assertEquals(0, gameState.findLockedDoorForCurrentRoom(gameState));
    }
}
