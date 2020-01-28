package com.mobisec.DecisionMaker.utils;

import java.security.SecureRandom;

public class RandomUtils {

    private static final String ALPHABETH = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String randomString(int len) {
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(ALPHABETH.charAt(rnd.nextInt(ALPHABETH.length())));
        return sb.toString();
    }

}
