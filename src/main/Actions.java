package main;

import java.util.stream.Collectors;
import java.util.Scanner;
import java.util.Arrays;

public class Actions {
    public static String executeAction(GameState gameState, String verb, String rest) {
        return switch (verb) {
            case "move", "go" -> move(gameState, rest);
            case "unlock" -> unlock(gameState, rest);
            case "take" -> take(gameState, rest);
            case "give" -> give(gameState, rest);
            case "check", "inv", "inventory" -> inventory(gameState, rest);
            case "examine" -> examine(gameState, rest);
            case "look" -> look(gameState);
            case "help" -> help();
            case "discard", "drop" -> discard(gameState, rest);
            case "map" -> World.printMap(gameState.currentRoom);
            case "quit" -> "";
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
        // Check if exit is locked door
        if (gameState.isLockedDoor(direction)) {
            return "The door is locked, you need to find a key to unlock it.";
        }
         //check if they can move in that direction and return the next room name if they can
        int moveNext = World.map[gameState.currentRoom][direction];
        if (moveNext == -1) {
            return "You are not able to move " + rest + " in this room";
        }
        //update the current room and return the next room name
        gameState.currentRoom = moveNext;
        return "You move " + rest + "." + "\n" + World.roomDescriptions[gameState.currentRoom];
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

    // Unlock
    static String unlock(GameState gameState, String rest) {
        // If rest is direction
        String[] directions = new String[] {"north", "east", "south", "north", "up", "down"};
        int currentRoom = gameState.currentRoom;
        if (Arrays.asList(directions).contains(rest)) {
            // Need to check if door
            int directionIndex = sortDirection(rest);
            int lockedVal = gameState.doorStates[currentRoom][directionIndex];

            if (lockedVal == 0) return "This door is open";
            else if (lockedVal == 1) {
                boolean hasKey = gameState.doesInventoryContain("key");
                if (hasKey) {
                    gameState.doorStates[currentRoom][directionIndex] = 0;
                    return "Door unlocked!";
                } else return "Need key to unlock this door";
            }
            else return "Nothing here to unlock"; 
        } else if (rest.equals("door")) {
            int foundLockedDoorIndex = gameState.findLockedDoorForCurrentRoom();
            if (foundLockedDoorIndex == -1) return "No locked door here";
            else {
                // Check has key
                boolean hasKey = gameState.doesInventoryContain("key");

                if (hasKey) {
                    gameState.doorStates[currentRoom][foundLockedDoorIndex] = 0;
                    // return "Door unlocked!";
                    String direction = directions[foundLockedDoorIndex];
                    return "Unlocked the " + direction + " door!";
                } else return "Need key to unlock this door";
            }
            
        } else return "Unlock what?";
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

    // Give 
    static String give(GameState gameState, String rest) {
        // Does item exist?
        boolean doesItemExist = gameState.doesItemExist(rest);
        if (!doesItemExist) return "That item doesn't exist";

        // Is item in player inv?
        boolean isItemInInv = gameState.doesInventoryContain(rest);
        if (!isItemInInv) return "You don't have that in your inventory";

        // Is character present?
        boolean isPresent = gameState.isCharacterPresent();
        if (!isPresent) return "No character present";

        Character character = gameState.getRoomCharacter();

        // Does character require item?
        boolean characterRequiresItem = character.getRequiredItem().equals(rest);
        // String characterItem = character
        //     .getInventory().stream()
        //     .findFirst().orElse(null);

        
        if (characterRequiresItem) {
            String characterItem = character.getHasItem();
            gameState.itemLocations.get(rest).setLocation(-2);
            character.setHasItem(rest);
            gameState.itemLocations.get(characterItem).setLocation(-1);
            return "Aha, I've been looking for that! Take this " + characterItem + " as a reward!";
        } else return "I don't need that item!";
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
        if(rest.equals("keeper") && gameState.currentRoom == 1){
            return examineCharacter(gameState, rest);
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
    public static String examineCharacter(GameState gameState, String rest){
        return World.printFrog() + "\n" + "You examine the keeper closely.'\n" + "The Keeper is badly injured and cant climb the tower.";
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
    // Look
    static String look(GameState gameState){
    StringBuilder exits = new StringBuilder();
    for (int i = 0; i < World.directionNames.length; i++){
        int nextRoom = World.map[gameState.currentRoom][i];
        if(nextRoom != -1){
            exits.append(World.directionNames[i]).append("\n");
        }
    }
    if (exits.length() == 0){
        return "No Available Exits";
    }
    return "Available Exits: \n" + exits;

    }
    // Help command 
    static String help(){
        return """
                **************************************************************************
                *   Available commands:                                                  *
                *                move <direction> - Lets you move to that direction      *
                *                go <direction> - Functions the same as move             *
                *                look - Shows available exits                            *
                *                examine - Shows all the items in the current room       *
                *                examine <item> - Gives a description of the item        *
                *                inventory - Shows inventory                             *
                *                inv - Same as inventory                                 *
                *                help - Displays available commands                      *
                *                quit - Exists the game                                  *
                *                restart - Restarts the game                             *
                *                give <item> - Gives an item to a character in the room  *
                *                take <item> - Takes an item from the room               *
                *                discard <item> - Discards an item from the inventory    *
                *                drop <item> - Behaves the same as discard               *
                **************************************************************************
                """;
    }
    // Discard items 

    
    static String discard (GameState gameState, String rest){
        Item item = gameState.itemLocations.get(rest);
        if (item == null){
        return "This item doesn't exist!";
        }
        if (item.getLocation() != -1){
            return "You don't have that item!";
        }
       
        item.setLocation(gameState.currentRoom);
        return "You dropped the " + rest;
        
        

    }
   
}

