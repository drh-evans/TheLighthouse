package tests;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import main.*;

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
        Map<String, main.Character> testCharacters = Map.of(
            "keeper", new main.Character("keeper", 1, "key", "lantern")
        );
        GameState initialGameState = new GameState(initialRoom, initialItemLocations, doorMap, testCharacters);

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
        assertFalse(gameState.doesInventoryContain("lantern"));

        Actions.executeAction(gameState, "take", "lantern");

        assertTrue(gameState.doesInventoryContain("lantern"));
    }

    @Test 
    public void isLockedDoorTest() {
        GameState gameState = World.initialiseTestGameState();

        assertTrue(gameState.isLockedDoor(0));
    }

    @Test 
    public void findLockedDoorForCurrentRoomTest() {
        GameState gameState = World.initialiseTestGameState();

        assertEquals(0, gameState.findLockedDoorForCurrentRoom());
    }

    @Test 
    public void isCharacterPresentTest() {
        GameState gameState = World.initialiseTestGameState();
        Actions.executeAction(gameState, "move", "east");
        assertTrue(gameState.isCharacterPresent());
    }

    @Test 
    public void getRoomCharacterTest() {
        GameState gameState = World.initialiseTestGameState();
        main.Character character = new main.Character("Keeper", 1, "key", "lantern");
        Actions.executeAction(gameState, "move", "east");
        assertEquals(character.getName(), gameState.getRoomCharacter().getName());
        assertEquals(character.getLocation(), gameState.getRoomCharacter().getLocation());
        assertEquals(character.getHasItem(), gameState.getRoomCharacter().getHasItem());
    }

    @Test 
    public void checkGameWonTest() {
        GameState gameState = World.initialiseTestGameState();
        assertFalse(gameState.checkGameWon());

        Actions.executeAction(gameState, "take", "lantern");
        Actions.executeAction(gameState, "move", "east");
        Actions.executeAction(gameState, "give", "lantern");
        Actions.executeAction(gameState, "move", "west");
        Actions.executeAction(gameState, "unlock", "door");
        Actions.executeAction(gameState, "move", "north");
        Actions.executeAction(gameState, "move", "east");
        Actions.executeAction(gameState, "take", "oil can");
        Actions.executeAction(gameState, "move", "west");
        Actions.executeAction(gameState, "move", "up");
        Actions.executeAction(gameState, "move", "up");
        Actions.executeAction(gameState, "move", "up");

        assertTrue(gameState.checkGameWon());
    }
}
