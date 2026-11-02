package main;

import java.util.HashMap;
import java.util.Map;

public class GameState {
    public int currentRoom;
    public HashMap<String, Integer> itemLocations;

    public GameState(int currentRoom, Map<String, Integer> initialItemLocations) {
        this.currentRoom = currentRoom;
        this.itemLocations = new HashMap<>(initialItemLocations);
    }

    public boolean isItemInCurrentRoom(String item) {
        return this.itemLocations.get(item) == this.currentRoom;
    }
}
