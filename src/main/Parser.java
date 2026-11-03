package main;

public class Parser {
    public static String[] parser(String input) throws Exception {
        String userInput = input.toLowerCase().trim().replaceAll("\\s+", " ");
        int spaceIndex = userInput.indexOf(" ");
        String verb = "", rest = "";
        // if only one word was found spaceIndex is -1
        if (spaceIndex != -1) {
            verb = userInput.substring(0, spaceIndex);
            rest = userInput.substring(spaceIndex + 1);
        }

        /* if we found only one word, return it. Otherwise return the first word 
            in result[0] followed by the rest of the word. */
        return spaceIndex == -1 ? new String[] {userInput, ""} : new String[] {verb, rest};
    }
}
