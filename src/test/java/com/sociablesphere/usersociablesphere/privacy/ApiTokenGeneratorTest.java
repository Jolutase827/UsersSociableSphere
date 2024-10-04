package com.sociablesphere.usersociablesphere.privacy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApiTokenGeneratorTest {

    @Test
    public void testApiTokenLength() {
        String token = ApiTokenGenerator.generateRandomApiToken();

        assertEquals(255, token.length(), "La longitud del token debe ser exactamente 255");
    }

    @Test
    public void testApiTokenIsAlphanumeric() {
        String token = ApiTokenGenerator.generateRandomApiToken();

        assertTrue(token.matches("^[a-zA-Z0-9]*$"), "El token debe ser alfanumérico");
    }

    @Test
    public void testApiTokenContainsValidCharacters() {
        String token = ApiTokenGenerator.generateRandomApiToken();

        for (char c : token.toCharArray()) {
            assertTrue("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".indexOf(c) >= 0,
                    "El token contiene caracteres no válidos: " + c);
        }
    }
}
