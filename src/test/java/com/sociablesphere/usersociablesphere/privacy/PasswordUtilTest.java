package com.sociablesphere.usersociablesphere.privacy;

import com.sociablesphere.usersociablesphere.exceptions.InvalidCredentialsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("PasswordUtil Tests")
public class PasswordUtilTest {

    private String plainPassword;
    private String hashedPassword;

    @BeforeEach
    public void setUp() {
        plainPassword = "password123";
        hashedPassword = PasswordUtil.hashPassword(plainPassword);
    }

    @Nested
    @DisplayName("Hash Password")
    class HashPassword {

        @Test
        @DisplayName("Generates a non-null hashed password")
        public void testHashPasswordNotNull() {
            String result = PasswordUtil.hashPassword(plainPassword);
            assertThat(result).isNotNull();
        }

        @Test
        @DisplayName("Generates different hashes for the same password")
        public void testHashPasswordGeneratesDifferentHashes() {
            String firstHash = PasswordUtil.hashPassword(plainPassword);
            String secondHash = PasswordUtil.hashPassword(plainPassword);
            assertThat(firstHash).isNotEqualTo(secondHash);
        }

        @Test
        @DisplayName("Throws exception for null input")
        public void testHashPasswordNullInput() {
            assertThatThrownBy(() -> PasswordUtil.hashPassword(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Password cannot be null");
        }
    }

    @Nested
    @DisplayName("Check Password")
    class CheckPassword {

        @Test
        @DisplayName("Returns true for correct password")
        public void testCheckPasswordCorrect() throws InvalidCredentialsException {
            boolean result = PasswordUtil.checkPassword(plainPassword, hashedPassword);
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("Throws exception for incorrect password")
        public void testCheckPasswordIncorrect() {
            String wrongPassword = "wrongPassword";
            assertThatThrownBy(() -> PasswordUtil.checkPassword(wrongPassword, hashedPassword))
                    .isInstanceOf(InvalidCredentialsException.class)
                    .hasMessage("Invalid credentials");
        }

        @Test
        @DisplayName("Throws exception for null plain password")
        public void testCheckPasswordNullPassword() {
            assertThatThrownBy(() -> PasswordUtil.checkPassword(null, hashedPassword))
                    .isInstanceOf(InvalidCredentialsException.class)
                    .hasMessage("Invalid credentials");
        }

        @Test
        @DisplayName("Throws exception for null hashed password")
        public void testCheckPasswordNullHashedPassword() {
            assertThatThrownBy(() -> PasswordUtil.checkPassword(plainPassword, null))
                    .isInstanceOf(InvalidCredentialsException.class)
                    .hasMessage("Invalid credentials");
        }

        @Test
        @DisplayName("Throws exception for both passwords being null")
        public void testCheckPasswordBothNull() {
            assertThatThrownBy(() -> PasswordUtil.checkPassword(null, null))
                    .isInstanceOf(InvalidCredentialsException.class)
                    .hasMessage("Invalid credentials");
        }

        @Test
        @DisplayName("Throws exception for empty plain password")
        public void testCheckPasswordEmptyPlainPassword() {
            assertThatThrownBy(() -> PasswordUtil.checkPassword("", hashedPassword))
                    .isInstanceOf(InvalidCredentialsException.class)
                    .hasMessage("Invalid credentials");
        }
    }
}
