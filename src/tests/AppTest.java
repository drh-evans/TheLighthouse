package tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import main.*;

public class AppTest {
    @Test
    void restartGameTest() {
        //initialise game state move east, take item then
        GameState testGameState = World.initialiseTestGameState();
        Actions.executeAction(testGameState, "take", "apple");
        Actions.executeAction(testGameState, "move", "east");
        
        //check that the game state has been updated
        assertEquals(1, testGameState.currentRoom);
        assertEquals(-1, testGameState.itemLocations.get("apple").getLocation());

        //restart the game
        testGameState = World.initialiseTestGameState();

        //check that the game state has been reset to the initial values
        assertNotNull(testGameState);
        assertEquals(0, testGameState.currentRoom);
        assertEquals(0, testGameState.itemLocations.get("apple").getLocation());  
    }

}