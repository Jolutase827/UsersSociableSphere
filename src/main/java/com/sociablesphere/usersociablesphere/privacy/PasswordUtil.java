package com.sociablesphere.usersociablesphere.privacy;

import com.sociablesphere.usersociablesphere.exceptions.InvalidCredentialsException;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) throws InvalidCredentialsException{
        if (BCrypt.checkpw(plainPassword, hashedPassword))
            return true;
        throw new InvalidCredentialsException("Invalid credentials");
    }
}
