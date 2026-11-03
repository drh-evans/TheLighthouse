import main.*;
import java.util.Scanner;

public class App {
    static Scanner stdin = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        GameState gameState = World.initialiseGameState();
        boolean running = true;
        
        while(running){
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
    }
}