package com.sociablesphere.usersociablesphere.privacy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;

public class ApiTokenGenerator {
    private static final Logger logger = LoggerFactory.getLogger(ApiTokenGenerator.class);
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789?¿!:.;,·$%&/(=)";
    private static final int TOKEN_LENGTH = 255;

    public static String generateRandomApiToken() {
        SecureRandom random = new SecureRandom();
        StringBuilder apiToken = new StringBuilder(TOKEN_LENGTH);

        for (int i = 0; i < TOKEN_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            apiToken.append(CHARACTERS.charAt(index));
        }

        String generatedToken = apiToken.toString();
        logger.info("Generated API token: {}", generatedToken);
        return generatedToken;
    }
}
