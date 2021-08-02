package dev.hoksv.common.strings;


/**
 * Class used for all string utilities
 */
public class StringUtils {


    /**
     * Counts how many times a certain character occurs
     * @param string The string to test on
     * @param character the character
     * @return How many characters there are
     */
    public static int countCharacter(String string, char character) {
        int count = 0;
        for (char c : string.toCharArray()) {
            if(c == character) count++;
        }

        return count;
    }

    /**
     * Translates color code with the char '&'
     * @param text Text to translate
     * @return {@link #translateColorCodes(String, char)}
     */
    public static String translateColorCodes(String text) {
        return translateColorCodes(text, '&');
    }

    /**
     * Translate color codes
     * @param text The text to translate
     * @param toReplace the character to replace
     * @return Translated color text
     */
    public static String translateColorCodes(String text, char toReplace) {
        return text.replace(toReplace, '§');
    }
}
