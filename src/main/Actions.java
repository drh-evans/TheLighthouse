package main;

public class Actions {
    public static String executeAction(GameState gameState, String verb, String rest) {
        return switch (verb) {
            case "move", "go" -> move(gameState, rest);
            case "take" -> take(gameState, rest);
            default -> "";

        };
    }

    // Move
    static String move(GameState gameState, String rest) {
        //if the user did not provide a direction, return a message asking for one
        if (rest.isEmpty()) {
            return "Move where?";
        }

        int direction = sortDirection(rest);
        //if the user provided an invalid direction, return a message saying so
        if (direction == -1) {
            return "That is not a valid direction.";
        }
        //check if they can move in that direction and return the next room name if they can
        int moveNext = nextRoom(gameState.currentRoom, direction);
        if (moveNext == -1) {
            return "You are not able to move" + rest + " in this room";
        }
        //update the current room and return the next room name
        gameState.currentRoom = moveNext;
        System.out.println("You move " + rest + ".");
        return World.roomNames[gameState.currentRoom];
    }

    //returns the next room number based on the current room and the direction given
    public static int nextRoom(int currentRoom, int direction) {
        return World.map[currentRoom][direction];
    }

    //assigns the direction given after a 'move' or 'go' command to a number that corresponds to the direction in the map array
    public static int sortDirection(String direction) {
        return switch (direction) {
            case "north" -> 0;
            case "east" -> 1;
            case "south" -> 2;
            case "west" -> 3;
            case "up" -> 4;
            case "down" -> 5;
            default -> -1;
        };
    }

    // Take
    static String take(GameState gameState, String rest) {
        boolean doesItemExist = gameState.doesItemExist(rest);
        if (!doesItemExist) return "No such item in game";

        boolean isItemHere = gameState.isItemInCurrentRoom(rest);

        if (isItemHere) {
            gameState.itemLocations.replace(rest, -1);
            // System.out.println("Item taken!");
            return "Item taken!";
        } else {
            // System.out.println("No such item here");
            return "No such item here";
        }
    }

    
}
