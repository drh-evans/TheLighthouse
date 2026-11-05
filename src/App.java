import main.*;
import main.Character;

import java.util.Scanner;

public class App {
    static Scanner stdin = new Scanner(System.in);
    // ASCII art made on https://www.asciiart.eu/ascii-draw-studio/app using their built in fonts and tool
    private static final String WELCOME_MESSAGE = 
              "\n\n                         ████████╗██╗  ██╗███████╗                             \n" + //
                "                         ╚══██╔══╝██║  ██║██╔════╝                             \n" + //
                "                            ██║   ███████║█████╗                               \n" + //
                "                            ██║   ██╔══██║██╔══╝                               \n" + //
                "                            ██║   ██║  ██║███████╗                             \n" + //
                "                            ╚═╝   ╚═╝  ╚═╝╚══════╝                             \n" + //
                "                                                                               \n" + //
                "██╗     ██╗ ██████╗ ██╗  ██╗████████╗██╗  ██╗ ██████╗ ██╗   ██╗███████╗███████╗\n" + //
                "██║     ██║██╔════╝ ██║  ██║╚══██╔══╝██║  ██║██╔═══██╗██║   ██║██╔════╝██╔════╝\n" + //
                "██║     ██║██║  ███╗███████║   ██║   ███████║██║   ██║██║   ██║███████╗█████╗  \n" + //
                "██║     ██║██║   ██║██╔══██║   ██║   ██╔══██║██║   ██║██║   ██║╚════██║██╔══╝  \n" + //
                "███████╗██║╚██████╔╝██║  ██║   ██║   ██║  ██║╚██████╔╝╚██████╔╝███████║███████╗\n" + //
                "╚══════╝╚═╝ ╚═════╝ ╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝ ╚═════╝  ╚═════╝ ╚══════╝╚══════╝\n";

    private static final String INSTRUCTIONS = "\n\nWelcome to the Lighthouse Text Adventure Game by Jason's Argonauts!\n          Feeling stuck? Type 'help' for a list of commands.\n";
    private static final String INTRO_STORY = """
                                            You find yourself on the shore of the Gower as a storm rages around you. In the distance you 
                                            notice a ship sailing through the darkness towards land. You spot an old lighthouse in the 
                                            distance and you decide to head towards it to find shelter and warn the boat crew that they 
                                            are approaching land. You wonder what secrets await you inside the lighthouse on this gloomy night. 
                                            """;

    public static void main(String[] args) throws Exception {
        GameState gameState = World.initialiseGameState();
        boolean running = true;
        boolean isFirstRun = true;
        boolean hasQuit = false;
        
        while(running){
            if (isFirstRun) {
                System.out.println(WELCOME_MESSAGE);
                System.out.println(INSTRUCTIONS);
                System.out.println(INTRO_STORY);
                isFirstRun = false;
            }

            if (gameState.checkGameWon()) {
                running = false;
                break;
            }

            String currentRoomLabel = gameState.getCurrentRoomLabel();
            System.out.printf("%s > ", currentRoomLabel);
            String input = stdin.nextLine();
            String[] result = Parser.parser(input);
            String verb = result[0];
            String rest = "";
            if (result.length > 1) {
                rest = result[1];
            }

            if (rest.equals("") && verb.equals("restart")) {
                gameState = World.initialiseGameState();
                System.out.println("Game has been restarted.");
                continue;
            }

            System.out.println("\n" + Actions.executeAction(gameState, verb, rest));
            printRoomDialogue(gameState, verb);
            
            if (rest.equals("") && verb.equals("quit")) {
                running = Actions.quit();
                hasQuit = true;
            }
        }

        if (!hasQuit) {
            System.out.println("You had collected an oil canister on your way to the lamp room, allowing you to light the lamps and save the ship from crashing into the rocks! You have won the game!");
        } else {
            System.out.println("You failed to light the lamp and the ship crashed into the rocks. You have lost the game.");
        }
        System.out.println("\nThank you for playing The Lighthouse!\nCreated by Jason's Argonauts: Jason Smith, Gareth Llewelyn, Suhayb Musa, and Rhys Evans.");
    }

    private static void printRoomDialogue(GameState gameState, String verb) {
        Character roomCharacter = gameState.getRoomCharacter();
            if (roomCharacter != null && !roomCharacter.getHasReceivedRequiredItem()) {
                System.out.println("The " + roomCharacter.getName() + " mutters, \"It's dark in here. Have you seen the " + roomCharacter.getRequiredItem() + " anywhere?\"");
            } else if (roomCharacter != null && roomCharacter.getHasReceivedRequiredItem() && !verb.equals("give")) {
                System.out.println("The " + roomCharacter.getName() + " greets you warmly and says, \"Thank you for bringing me the " + roomCharacter.getRequiredItem() + "!\"");
            }
    }
}