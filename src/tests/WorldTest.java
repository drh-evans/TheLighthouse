package tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import main.GameState;
import main.World;

public class WorldTest {
    @Test 
    void initialiseGameStateHappyPath() {;
        GameState initialGameState = World.initialiseGameState();
        main.Character keeperCharacter = initialGameState.characters.get("keeper");

        assertEquals(0, initialGameState.currentRoom);
        assertEquals(-2, initialGameState.itemLocations.get("key").getLocation());
        assertEquals("Keeper", keeperCharacter.getName());
        assertEquals(1, keeperCharacter.getLocation());
        assertEquals("key", keeperCharacter.getHasItem());
        assertEquals("lantern", keeperCharacter.getRequiredItem());
    }

    @Test 
    void initialiseTestGameStateHappyPath() {
        GameState testGameState = World.initialiseTestGameState();

        assertEquals(0, testGameState.currentRoom);
        assertEquals(0, testGameState.itemLocations.get("lantern").getLocation());
    }
}
