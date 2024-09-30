package com.sociablesphere.usersociablesphere.privacy;

import com.sociablesphere.usersociablesphere.exceptions.InvalidCredentialsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PasswordUtilTest {

    private String plainPassword;
    private String hashedPassword;

    @BeforeEach
    public void setUp() {
        plainPassword = "password123";
        hashedPassword = PasswordUtil.hashPassword(plainPassword);
    }

    @Test
    @DisplayName("Test hashPassword generates a non-null hashed password")
    public void testHashPasswordNotNull() {
        // Act
        String result = PasswordUtil.hashPassword(plainPassword);

        // Assert
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("Test hashPassword generates different hashes for the same password")
    public void testHashPasswordGeneratesDifferentHashes() {
        // Act
        String firstHash = PasswordUtil.hashPassword(plainPassword);
        String secondHash = PasswordUtil.hashPassword(plainPassword);

        // Assert
        assertThat(firstHash).isNotEqualTo(secondHash);
    }

    @Test
    @DisplayName("Test checkPassword returns true for correct password")
    public void testCheckPasswordCorrect() throws InvalidCredentialsException {
        // Act & Assert
        boolean result = PasswordUtil.checkPassword(plainPassword, hashedPassword);
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Test checkPassword throws exception for incorrect password")
    public void testCheckPasswordIncorrect() {
        // Arrange
        String wrongPassword = "wrongPassword";

        // Act & Assert
        assertThatThrownBy(() -> PasswordUtil.checkPassword(wrongPassword, hashedPassword))
                .isInstanceOf(InvalidCredentialsException.class)
                .hasMessage("Invalid credentials");
    }

    @Test
    @DisplayName("Test checkPassword throws exception for null password")
    public void testCheckPasswordNullPassword() {
        // Act & Assert
        assertThatThrownBy(() -> PasswordUtil.checkPassword(null, hashedPassword))
                .isInstanceOf(InvalidCredentialsException.class);
    }

    @Test
    @DisplayName("Test checkPassword throws exception for null hashed password")
    public void testCheckPasswordNullHashedPassword() {
        // Act & Assert
        assertThatThrownBy(() -> PasswordUtil.checkPassword(plainPassword, null))
                .isInstanceOf(InvalidCredentialsException.class);
    }

    @Test
    @DisplayName("Test hashPassword throws exception for null input")
    public void testHashPasswordNullInput() {
        // Act & Assert
        assertThatThrownBy(() -> PasswordUtil.hashPassword(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Password cannot be null");
    }
}
