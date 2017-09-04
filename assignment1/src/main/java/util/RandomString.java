package util;

import java.util.Random;

/**
 * Class taken and modified from
 * https://codereview.stackexchange.com/questions/84330/random-5-character-token-generator
 */
public class RandomString {
    private String CHARS = "abcdefghijklmnopqrstuvwxyz"
            + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "0123456789";
    private final Random random = new Random();

    private String randomString;

    public RandomString(int length)
    {
        StringBuilder sb = new StringBuilder(length);
        for(int i=0; i<length; i++)
        {
            sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        randomString = sb.toString();
    }

    public String getString() {
        return randomString;
    }
}
