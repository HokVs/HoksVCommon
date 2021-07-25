package dev.hoksv.common.strings;

import java.util.List;
import java.util.Random;

public class RandomStrings {

    /** Constant <code>ALPHABET="abcdefghijklmnopqrstuvwxyz0123456789"</code> */
    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz0123456789";
    /** Constant <code>ALPHABET2="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklm"{trunked}</code> */
    public static final String ALPHABET2 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random RANDOM = new Random();

    /**
     * Generate a randomString alphanumeric String with the given length, using RandomUtils.ALPHABET as it's alphabet
     * `abcdefghijklmnopqrstuvwxyz0123456789`
     *
     * @param length The length of the String to generate
     * @return A randomString string with the given length
     */
    public static String randomString(int length) {
        return randomString(length, ALPHABET);
    }

    public static String randomString(int length, String alphabet) {
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            // Pick a randomString character from the alphabet
            char randomChar = alphabet.charAt(RANDOM.nextInt(alphabet.length()));

            sb.append(randomChar);
        }

        return sb.toString();
    }

    public static <T> T randomPick(List<T> list) {
        return list.get(RANDOM.nextInt(list.size()));
    }
}

