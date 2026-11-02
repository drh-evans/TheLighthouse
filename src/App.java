import main.*;
import java.util.Scanner;


public class App {
    static Scanner stdin = new Scanner(System.in);
    public static void main(String[] args) throws Exception {
        GameState gameState = new GameState();
        boolean running = true;

        while(running){
            System.out.print("> ");
            String input = stdin.nextLine();
            String[] result = Parser.parser(input);
            String verb = result[0];
            String rest = "";
            if (result.length > 1) {
                rest = result[1];
            }

            Actions.executeAction(gameState, verb, rest);
        }
    }
}    