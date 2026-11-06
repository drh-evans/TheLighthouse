package tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import main.*;

public class ActionsTest {
    // Unlock
    @Test 
    void unlockDoorHappyPath() {
        GameState testGameState = World.initialiseTestGameState();
        testGameState.itemLocations.get("key").setLocation(0);
        String message1 = Actions.executeAction(testGameState, "unlock", "door");
        assertEquals(message1, "Need key to unlock this door");
        Actions.executeAction(testGameState, "take", "key");
        String message2 = Actions.executeAction(testGameState, "unlock", "door");
        assertEquals(message2, "Unlocked the north door!");
    }

    // Take
    @Test
    void takeItemHappyPath() {
        GameState testGameState = World.initialiseTestGameState();
        String message = Actions.executeAction(testGameState,"take", "apple");

        assertEquals(message, "Item taken!");
        assertEquals(-1, testGameState.itemLocations.get("apple").getLocation());
    }

    @Test 
    void takeNonExistentItem() {
        GameState testGameState = World.initialiseTestGameState();
        String message = Actions.executeAction(testGameState, "take", "crown");

        assertEquals(message, "No such item in game");
    }

    // Inventory
    @Test
    void checkEmptyInventoryTest() {
        GameState testGameState = World.initialiseTestGameState();
        String message = Actions.executeAction(testGameState, "check", "inventory");

        assertEquals(message, "Inventory empty");
    }

    @Test
    void checkInventoryTest() {
        GameState testGameState = World.initialiseTestGameState();
        Actions.executeAction(testGameState, "take", "apple");
        String message = Actions.executeAction(testGameState, "check", "inventory");

        assertEquals(message, "Inventory: apple");
    }

    // Move
    @Test
    void moveAnyDirectionTest() {
        GameState testGameState = World.initialiseTestGameState();
        String message = Actions.executeAction(testGameState, "move", "east");

        assertTrue(message.contains("You move east."));
        assertEquals(1, testGameState.currentRoom);
    }

    @Test
    void moveInvalidDirectionTest() {
        GameState testGameState = World.initialiseTestGameState();
        String message = Actions.executeAction(testGameState, "move", "up");    

        assertEquals(message, "You are not able to move up in this room");
    }

    @Test
    void moveNotRecognisedDirectionTest() throws Exception {
        GameState testGameState = World.initialiseTestGameState();
        String message = Actions.executeAction(testGameState, "move", "sideways");    

        assertEquals(message, "That is not a valid direction.");
    }

    @Test
    void moveLockedDoorTest() {
        GameState testGameState = World.initialiseTestGameState();
        String message = Actions.executeAction(testGameState, "move", "north");    

        assertEquals(message, "The door is locked, you need to find a key to unlock it.");
    }
    
    // Give
    @Test 
    void giveRequiredItemTest() {
        GameState testGameState = World.initialiseTestGameState();
        Actions.executeAction(testGameState, "take", "lantern");
        Actions.executeAction(testGameState, "move", "east");
        main.Character roomCharacter = testGameState.getRoomCharacter();
        assertEquals("key", roomCharacter.getHasItem());
        String message = Actions.executeAction(testGameState, "give", "lantern");
        assertEquals(message, "Aha, I've been looking for that! Take this key as a reward!");
        assertEquals(testGameState.itemLocations.get("lantern").getLocation(), -2);
        assertEquals(testGameState.itemLocations.get("key").getLocation(), -1);
        assertEquals("lantern", roomCharacter.getHasItem());
    }

    @Test
    void giveUnrequiredItemTest() {
        GameState testGameState = World.initialiseTestGameState();
        Actions.executeAction(testGameState, "take", "apple");
        Actions.executeAction(testGameState, "move", "east");
        String message = Actions.executeAction(testGameState, "give", "apple");
        assertEquals(message, "I don't need that item!");
    }
    /// Examine 
    @Test
    void checkExamineRoom() {
        GameState testGameState = World.initialiseTestGameState();
        String result = Actions.examine(testGameState, "");
        assertTrue(result.contains("lantern"));
    }
    @Test 
    void examineItemInCurrentRoom() {
        GameState testGameState = World.initialiseTestGameState();
        String result = Actions.examine(testGameState, "lantern");
        assertEquals("You examine the lantern closely and see: A bright lantern", result);

    }
    @Test 
    void examineitemDoesNotExist() {
        GameState testGameState = World.initialiseTestGameState();
        String result = Actions.examine(testGameState, "sword");
        assertEquals("You can't see that here.", result);
    }
    @Test 
    void examineEmptyRoom() {
        GameState testGameState = World.initialiseTestGameState();
        Actions.executeAction(testGameState, "move", "east");
        Actions.executeAction(testGameState, "take", "key");
        Actions.executeAction(testGameState, "move", "west");
        Actions.executeAction(testGameState, "unlock", "door");
        Actions.executeAction(testGameState, "move", "north");
        String result = Actions.examine(testGameState, "");
        assertEquals("You thoroughly examined the room but found nothing of interest.", result);
    }
    // look
    @Test 
    void testLookShorePath(){
        GameState testGameState = World.initialiseTestGameState();
        String result = Actions.executeAction(testGameState, "look", null);
        assertTrue(result.contains("east"));
        assertTrue(result.contains("north"));
    }
    @Test 
    void testLookKeepersCottage(){
        GameState testGameState = World.initialiseTestGameState();
        Actions.executeAction(testGameState, "move", "east");
        String result = Actions.executeAction(testGameState, "look", null);
        assertTrue(result.contains("west"));
    
    }

    @Test 
    void testBaseofTheTower(){
        GameState testGameState = World.initialiseTestGameState();
        Actions.executeAction(testGameState, "take", "lanter");
        Actions.executeAction(testGameState, "move", "east");
        Actions.executeAction(testGameState, "give", "lantern");
        Actions.executeAction(testGameState, "move", "west");
        Actions.executeAction(testGameState, "unlock", "door");
        Actions.executeAction(testGameState, "move", "north");
        String result = Actions.executeAction(testGameState, "look", null);
        assertTrue(result.contains("east"));
        assertTrue(result.contains("up"));
        assertTrue(result.contains("south"));
    }
    // discard items 
    @Test 
    void testDiscardItems(){
        GameState testGameState = World.initialiseTestGameState();
        Actions.executeAction(testGameState,"take" , "lantern");
        Actions.executeAction(testGameState, "drop", "lantern");
        assertEquals(0 , testGameState.getItemLocation("lantern"));
    }
    @Test 
    void checkLocationForDiscardedItems(){
        GameState testGameState = World.initialiseTestGameState();
        testGameState.itemLocations.get("apple").setLocation(-1);
        String dropResult = Actions.executeAction(testGameState, "drop", "apple");
        assertEquals("You dropped apple", dropResult);
        assertEquals(testGameState.currentRoom, testGameState.itemLocations.get("apple").getLocation());
    }

    // Help 
    @Test 
    void testHelpCommand() {
        GameState testgGameState = World.initialiseTestGameState();
        String results =  Actions.executeAction(testgGameState, "help", null);
        assertTrue(results.contains("Available commands:"));
        assertTrue(results.contains("move <direction> - Lets you move to that direction"));
        assertTrue(results.contains("inventory - Shows inventory"));
        assertTrue(results.contains("drop = behaves the same as discard"));
        assertTrue(results.contains("help - Displays available commands"));
    }
    // Map
    @Test 
    void testMapShowsPlayerAtShorePath(){
        String result = World.printMap(0);
        assertTrue(result.contains("[X] Shore Path"));
        assertTrue(result.contains("[ ] Keeper's Cottage"));
    }
    @Test 
    void testMapDisplaysAllRooms() {
    String result = World.printMap(0);
    assertTrue(result.contains("Shore Path"));
    assertTrue(result.contains("Keeper's Cottage"));
    assertTrue(result.contains("Store Room"));
    assertTrue(result.contains("Spiral Stair"));
    assertTrue(result.contains("Watch Room"));
    assertTrue(result.contains("Lamp Room"));
    }
    @Test 
    void testMapShowsPlayerAtKeepersCottage() {
        String result = World.printMap(1);
        assertTrue(result.contains("[ ] Shore Path"));
        assertTrue(result.contains("[X] Keeper's Cottage"));
    }
    
}