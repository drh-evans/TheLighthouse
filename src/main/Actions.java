package main;

import java.util.Scanner;

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
        if (rest.isEmpty()) {
            return "Move where?";
        }
        int direction = sortDirection(rest);
        if (direction == -1) {
            return "That is not a valid direction.";
        }
        int moveNext = nextRoom(gameState.currentRoom, direction);
        if (moveNext == -1) {
            return "You are not able to move" + rest + " in this room";
        }

        gameState.currentRoom = moveNext;
        System.out.println("You move " + rest + ".");
        return World.roomNames[gameState.currentRoom];
    }

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

    public static boolean quit() {
        Scanner stdin = new Scanner(System.in);
        System.out.println("This action cannot be reversed, are you sure you want to quit? (y/n)");

        String input = stdin.nextLine().toLowerCase();

        // stdin.close();

        if (input.equals("y")) {
            System.out.println("Quitting game...");
            return false;
        }
        return true;
    }
}
