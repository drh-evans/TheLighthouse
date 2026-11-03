package main;

import java.util.stream.Collectors;
import java.util.Scanner;

public class Actions {
    public static String executeAction(GameState gameState, String verb, String rest) {
        return switch (verb) {
            case "move", "go" -> move(gameState, rest);
            case "take" -> take(gameState, rest);
            case "check", "inv", "inventory" -> inventory(gameState, rest);
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
            gameState.itemLocations.replace(rest, -1);
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
        .filter(entry -> entry.getValue() == -1)
        .map(entry -> entry.getKey())
        .collect(Collectors.joining(", "));

        if (itemString.equals("")) {
            return "Inventory empty";
        } else {
            return "Inventory: " + itemString;
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
}
