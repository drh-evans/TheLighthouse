package main;

public class Parser {
    public static String[] parser(String input) throws Exception {
        String userInput = input.toLowerCase().trim();
        //initlize spaceIndex to -1 to indicate no space found
        int spaceIndex = 0;
        try {
            //look for a space in the user input
            spaceIndex = userInput.indexOf(" ");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        //if space is not found
        if (spaceIndex == -1) {
            return new String[] {userInput, ""};
        }
        String verb = userInput.substring(0, spaceIndex);
        String rest = userInput.substring(spaceIndex + 1);
         String[] result = new String[] {verb};
        if (rest != null && !rest.isEmpty()) {
            result = new String[] {verb, rest};
        }
        return result;
    }
}
