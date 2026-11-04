import main.*;
import java.util.Scanner;

public class App {
    static Scanner stdin = new Scanner(System.in);
    private static final String WELCOME_MESSAGE = "\nWelcome to the Lighthouse Text Adventure Game! Type 'help' for a list of commands. \n";
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
        
        while(running){
            if (isFirstRun) {
                System.out.println(WELCOME_MESSAGE);
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

            if (rest.equals("") && verb.equals("quit")) {
                running = Actions.quit();
            }

            System.out.println(Actions.executeAction(gameState, verb, rest));
        }

        System.out.println("You won the game!");
    }
}