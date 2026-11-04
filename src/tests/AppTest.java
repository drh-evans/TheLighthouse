package tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import main.*;

public class AppTest {
    @Test
    void restartGameTest() {
        //initialise game state move east, take item then
        GameState testGameState = World.initialiseTestGameState();
        Actions.executeAction(testGameState, "move", "east");
        Actions.executeAction(testGameState, "take", "key");
        
        //check that the game state has been updated
        assertEquals(1, testGameState.currentRoom);
        assertEquals(-1, testGameState.itemLocations.get("key").getLocation());

        //restart the game
        testGameState = World.initialiseGameState();

        //check that the game state has been reset to the initial values
        assertNotNull(testGameState);
        assertEquals(0, testGameState.currentRoom);
        assertEquals(1, testGameState.itemLocations.get("key").getLocation());  
    }

}