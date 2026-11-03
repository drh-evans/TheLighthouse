package tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import main.*;

public class ActionsTest {
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
}
