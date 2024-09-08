package com.course.miniapp.utils;

import java.util.Random;

public class RandomStrGen {
    private static final Random random = new Random();
    private static final String characters = "1234567890";

    public static String generate(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    public static Long generateCourseId() {
        return System.currentTimeMillis() + Long.parseLong(generate(3));
    }

    public static String generateUserId() {
        return  "UID_" + System.currentTimeMillis();
    }
}
