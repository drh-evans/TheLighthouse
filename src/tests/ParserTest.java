package tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParserTest{
    @Test 
    void testSingleWord() throws Exception
    {
        String [] result = main.Parser.parser("Move");
        assertArrayEquals(new String []{"move", ""}, result);
    }

    @Test 
    void testTwoWords() throws Exception 
    {
        String [] result = main.Parser.parser("move left");
        assertArrayEquals(new String [] {"move", "left"}, result);
    }
    @Test 
    void testMultipleWords () throws Exception
    {
        String [] result = main.Parser.parser("move east then go up");
        assertArrayEquals(new String []{"move", "east then go up"}, result);
    }

    @Test 
    void testForLeadingSpaces () throws Exception
    {
        String [] result = main.Parser.parser("   move west");
        assertArrayEquals(new String [] {"move", "west"}, result);
    }

    @Test 
    void testForTrailingSpaces () throws Exception
    {
        String [] result = main.Parser.parser("move west     ");
        assertArrayEquals(new String [] {"move", "west"}, result);
    }

    @Test 
    void testMultipleSpacesBetweenWords() throws Exception
    {
        String [] result = main.Parser.parser("move     west");
        assertArrayEquals(new String [] {"move", "west"}, result);
    }

    @Test 
    void testForEmptyInput () throws Exception
    {
        String [] result = main.Parser.parser("");
        assertArrayEquals(new String [] {"", ""},result );
    }    
}
