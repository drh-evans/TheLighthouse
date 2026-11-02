package tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import main.*;

public class ActionsTest {
    @Test
    void takeItemHappyPath() {
        GameState testGameState = World.initialiseTestGameState();
        String message = Actions.executeAction(testGameState,"take", "apple");

        assertEquals(-1, testGameState.itemLocations.get("apple"));
        assertEquals(message, "Item taken!");
    }
}
