package main;

import java.util.HashMap;
import java.util.Map;

public class GameState {
    public int currentRoom;
    public HashMap<String, Item> itemLocations;
    public int[][] doorStates;

    public GameState(
        int currentRoom, 
        Map<String, Item> initialItemLocations,
        int[][] doorStates
    ) {
        this.currentRoom = currentRoom;
        this.itemLocations = new HashMap<>(initialItemLocations);
        this.doorStates = doorStates;
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

    public boolean doesInventoryContain(String item) {
        return this.itemLocations.get(item).getLocation() == -1;
    }

    public boolean isLockedDoor(int direction) {
        return this.doorStates[this.currentRoom][direction] == 1;
    }

    public int findLockedDoorForCurrentRoom(GameState gameState) {
        int foundLockedDoorIndex = -1;
        int[] currentRoomDoorArr = gameState.doorStates[gameState.currentRoom];
        
        for (int i = 0; i <currentRoomDoorArr.length; i++) {
            if (currentRoomDoorArr[i] == 1) {
                foundLockedDoorIndex = i;
            }
        }
        return foundLockedDoorIndex;
    } 
}
