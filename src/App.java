import main.Parser;
import java.util.Scanner;

public class App {
    static Scanner stdin = new Scanner(System.in);
    public static void main(String[] args) throws Exception {
        while(true){
            System.out.print("< ");
            String input = stdin.nextLine();
            String[] result = Parser.parser(input);
            System.out.println(java.util.Arrays.toString(result));
            }
        }
    }    