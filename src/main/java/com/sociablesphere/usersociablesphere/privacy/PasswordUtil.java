package com.sociablesphere.usersociablesphere.privacy;

import com.sociablesphere.usersociablesphere.exceptions.InvalidCredentialsException;
import org.mindrot.jbcrypt.BCrypt;


public class PasswordUtil {

    public static String hashPassword(String plainPassword) {
        if (plainPassword == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) throws InvalidCredentialsException {
        if (plainPassword == null || hashedPassword == null) {
            throw new InvalidCredentialsException("Invalid credentials");
        }
        if (BCrypt.checkpw(plainPassword, hashedPassword)) {
            return true;
        }
        throw new InvalidCredentialsException("Invalid credentials");
    }
}
