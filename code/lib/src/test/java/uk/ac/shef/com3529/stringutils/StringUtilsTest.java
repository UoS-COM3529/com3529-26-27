package uk.ac.shef.com3529.stringutils;

import java.util.Set;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

// change "StringUtils" to "StringUtilsBuggy1" and "StringUtilsBuggy2" to get test failures
import static uk.ac.shef.com3529.stringutils.StringUtils.duplicateLetters;

public class StringUtilsTest {

    @Test
    public void shouldReturnRepeatedChar() {
        Set<Character> resultSet = duplicateLetters("software testing");
        assertTrue(resultSet.contains('t'));
    }

    @Test
    public void shouldNotReturnNonRepeatedChar() {
        Set<Character> resultSet = duplicateLetters("software debugging");
        assertFalse(resultSet.contains('t'));
    }

    @Test
    public void shouldReturnRepeatedCharFromStartOfString() {
        Set<Character> resultSet = duplicateLetters("software skill");
        assertTrue(resultSet.contains('s'));
    }
}
