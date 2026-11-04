package tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import main.*;

public class ActionsTest {
    // Unlock
    @Test 
    void unlockDoorHappyPath() {
        GameState testGameState = World.initialiseTestGameState();
        String message = Actions.executeAction(testGameState, "unlock", "door");
        assertEquals(message, "Unlocked the north door!");
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

        assertEquals(message, "You move east.");
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

        assertEquals(message, "Locked door. Unlock with key");
    }
    
    // Give
    @Test 
    void giveRequiredItemTest() {
        GameState testGameState = World.initialiseTestGameState();
        Actions.executeAction(testGameState, "take", "lantern");
        Actions.executeAction(testGameState, "move", "east");
        String message = Actions.executeAction(testGameState, "give", "lantern");
        assertEquals(message, "Item given! You've received an item back!");
        assertEquals(testGameState.itemLocations.get("lantern").getLocation(), -2);
        assertEquals(testGameState.itemLocations.get("key").getLocation(), -1);
    }

    @Test
    void giveUnrequiredItemTest() {
        GameState testGameState = World.initialiseTestGameState();
        Actions.executeAction(testGameState, "take", "apple");
        Actions.executeAction(testGameState, "move", "east");
        String message = Actions.executeAction(testGameState, "give", "apple");
        assertEquals(message, "Character doesn't require that");
    }
}
