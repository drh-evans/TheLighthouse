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

    @Test 
    void checkDirection(){
        assertEquals(0, Actions.sortDirection("north"));
        assertEquals(1, Actions.sortDirection("east"));
        assertEquals(2, Actions.sortDirection("south"));
        assertEquals(3, Actions.sortDirection("west"));
        assertEquals(4, Actions.sortDirection("up"));
        assertEquals(5, Actions.sortDirection("down"));
        assertEquals(-1, Actions.sortDirection("sideways"));
    }
    @Test
    void giveItemNotCarriedTest() {
        GameState testGameState = World.initialiseGameState();
        Actions.executeAction(testGameState, "move", "east");
        String message = Actions.executeAction(testGameState, "give", "lantern");
 
        assertEquals("You don't have that in your inventory", message);
        assertEquals(0, testGameState.itemLocations.get("lantern").getLocation());
        assertEquals(-2, testGameState.itemLocations.get("key").getLocation());     
    }

    @Test
    void dropItemThatDoesNotExistTest() {
        GameState testGameState = World.initialiseGameState();
        assertEquals("This item doesn't exist!", Actions.executeAction(testGameState, "drop", "crown"));
    }


    @Test
    void moveThroughLockedDoorWithKeyTest() {
        GameState testGameState = World.initialiseGameState();
        testGameState.itemLocations.get("key").setLocation(-1);
        
        Actions.executeAction(testGameState, "unlock", "door");
        String message = Actions.executeAction(testGameState, "move", "north");
 
        assertTrue(message.contains("You move north."));
        assertEquals(2, testGameState.currentRoom);
}
}
