package main;

import java.util.stream.Collectors;
import java.util.Scanner;

public class Actions {
    public static String executeAction(GameState gameState, String verb, String rest) {
        return switch (verb) {
            case "move", "go" -> move(gameState, rest);
            case "take" -> take(gameState, rest);
            case "check", "inv", "inventory" -> inventory(gameState, rest);
            case "examine" -> examine(gameState, rest);
            default -> "Unrecognised command";

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
            if (isTowerDoor(gameState.currentRoom, direction)){
                return "The tower door is locked.";
            }
            return "You are not able to move" + rest + " in this room";
        }
        //update the current room and return the next room name
        gameState.currentRoom = moveNext;
        return "You move " + rest + ".";
    }

    //returns the next room number based on the current room and the direction given
    public static int nextRoom(int currentRoom, int direction) {
        if (isTowerDoor(currentRoom, direction))
            return -1;
        return World.map[currentRoom][direction];
    }

    //checks if the room they'r entering is the base of the tower
    public static Boolean isTowerDoor(int currentRoom, int direction){
        return (currentRoom == 0 && direction == 0) || (currentRoom == 2 && direction == 2);

    }

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
        if (!doesItemExist)
            return "No such item in game";

        boolean isItemHere = gameState.isItemInCurrentRoom(rest);

        if (isItemHere) {
            gameState.itemLocations.get(rest).setLocation(-1);
            // System.out.println("Item taken!");
            return "Item taken!";
        } else {
            // System.out.println("No such item here");
            return "No such item here";
        }
    }

    // Check inventory
    static String inventory(GameState gameState, String rest) {
        String itemString = gameState.itemLocations.entrySet().stream()
        .filter(entry -> entry.getValue().getLocation() == -1)
        .map(entry -> entry.getKey())
        .collect(Collectors.joining(", "));

        if (itemString.equals("")) {
            return "Inventory empty";
        } else {
            return "Inventory: " + itemString;
        }
    }

    // Quit
    public static boolean quit() {
        System.out.println("This action cannot be reversed, are you sure you want to quit? (y/n)");

        String input = getStringInput();

        while (!input.equals("y") && !input.equals("n")) {
            System.out.println("Invalid input, please enter 'y' or 'n': ");
            input = getStringInput();
        }
        return getQuitGameResult(input);
    }

    // Examine
    public static String examine(GameState gameState, String rest) {
        if (rest.equals("")) {
            return examineRoom(gameState);
        }
        return examineItem(gameState, rest);
    }

    // Examine Item in Inventory or Room
    public static String examineItem(GameState gameState, String rest) {
        if (!gameState.doesItemExist(rest)) {
            return "You can't see that here.";
        }
        boolean doesItemExistInCurrentRoom = gameState.isItemInCurrentRoom(rest);
        boolean doesItemExistInInventory = gameState.itemLocations.get(rest).getLocation() == -1;

        if (!doesItemExistInCurrentRoom && !doesItemExistInInventory) {
            return "You can't see that here.";
        }
        return "You examine the " + rest + " closely and see: " + descriptionOfItem(gameState, rest);
    }

    // Examine specific room
    public static String examineRoom(GameState gameState) {
        String itemsFound = "";
        for (Item item : gameState.itemLocations.values()) {
            if (item.getLocation() == gameState.currentRoom) {
                itemsFound += item.getName() + ", ";
            }
        }
        if (!itemsFound.equals("")) {
            // substring to remove the last comma and space
            return "You examined the room closely and noticed the following items: " + itemsFound.substring(0, itemsFound.length() - 2);
        }
        return "You thoroughly examined the room but found nothing of interest.";
    }

    // Helper methods

    private static boolean getQuitGameResult(String input) {
        if (input.equals("y")) {
            System.out.println("Quitting game...");
            return false;
        }
        return true;
    }

    private static String getStringInput() {
        Scanner stdin = new Scanner(System.in);
        return stdin.nextLine().toLowerCase();
    }

    private static String descriptionOfItem(GameState gameState, String item) {
        return gameState.itemLocations.get(item).getDescription();
    }
}
