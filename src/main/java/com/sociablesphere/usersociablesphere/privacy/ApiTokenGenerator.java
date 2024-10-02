package com.sociablesphere.usersociablesphere.privacy;

import java.util.Random;

public class ApiTokenGenerator {

    public static String generateRandomApiToken() {
        long randomLong = Math.abs(new Random().nextLong());
        String apiToken = Long.toString(randomLong);

        return apiToken.length() <= 255 ? apiToken : apiToken.substring(0, 255);
    }
}
