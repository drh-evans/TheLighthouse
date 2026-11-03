package main;

import java.util.HashMap;
import java.util.Map;

public class GameState {
    public int currentRoom;
    public HashMap<String, Item> itemLocations;

    public GameState(int currentRoom, Map<String, Item> initialItemLocations) {
        this.currentRoom = currentRoom;
        this.itemLocations = new HashMap<>(initialItemLocations);
    }

    public String getCurrentRoomLabel() {
        return World.roomNames[this.currentRoom];
    }

    public boolean doesItemExist(String item) {
        return this.itemLocations.get(item) != null;
    }

    public boolean isItemInCurrentRoom(String item) {
        return this.itemLocations.get(item).getLocation() == this.currentRoom;
    }
}
