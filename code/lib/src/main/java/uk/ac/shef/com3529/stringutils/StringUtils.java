package uk.ac.shef.com3529.stringutils;

import java.util.Set;
import java.util.TreeSet;

public class StringUtils {

    public static Set<Character> duplicateLetters(String s) {
        // lower case the string and remove all characters that are not letters
        s = s.toLowerCase().replaceAll("[^a-z.]", "");

        // initialise the result set
        Set<Character> duplicates = new TreeSet<>();

        // iterate through the string
        for (int i = 0; i < s.length(); i++) {
            char si = s.charAt(i);

            // iterate through the rest of the string, checking for the same letter
            for (int j = i + 1; j < s.length(); j++) {
                char sj = s.charAt(j);

                if (si == sj) {
                    // a match has been found, add it to the result set
                    duplicates.add(si);
                }
            }
        }

        return duplicates;
    }
}
