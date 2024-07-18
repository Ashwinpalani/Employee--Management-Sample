package com.employee.management.utility;

import java.security.SecureRandom;

public class CustomIdGenerator  {

    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateAlphanumericID() {
        StringBuilder id = new StringBuilder("EMP");
        for (int i = 0; i < 8; i++) {
            id.append(ALPHANUMERIC.charAt(RANDOM.nextInt(ALPHANUMERIC.length())));
        }
        return id.toString();
    }
    
}
