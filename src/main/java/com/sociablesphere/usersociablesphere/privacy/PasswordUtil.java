package com.sociablesphere.usersociablesphere.privacy;

import com.sociablesphere.usersociablesphere.exceptions.InvalidCredentialsException;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordUtil {
    private static final Logger logger = LoggerFactory.getLogger(PasswordUtil.class);

    public static String hashPassword(String plainPassword) {
        if (plainPassword == null) {
            logger.error("Attempted to hash a null password");
            throw new IllegalArgumentException("Password cannot be null");
        }

        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
        logger.info("Password hashed successfully");
        return hashedPassword;
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) throws InvalidCredentialsException {
        if (plainPassword == null || hashedPassword == null) {
            logger.warn("Invalid credentials check attempted with null values");
            throw new InvalidCredentialsException("Invalid credentials");
        }

        boolean passwordMatch = BCrypt.checkpw(plainPassword, hashedPassword);
        if (passwordMatch) {
            logger.info("Password verification successful");
            return true;
        } else {
            logger.error("Password verification failed for provided credentials");
            throw new InvalidCredentialsException("Invalid credentials");
        }
    }
}
