package com.sociablesphere.usersociablesphere.privacy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ApiTokenGeneratorTest {

    @Test
    public void testApiTokenLength() {
        String token = ApiTokenGenerator.generateRandomApiToken();

        assertTrue(token.length() <= 255, "La longitud del token debe ser menor o igual a 255");
    }

    @Test
    public void testApiTokenIsPositive() {
        String token = ApiTokenGenerator.generateRandomApiToken();

        long tokenLong = Long.parseLong(token);

        assertTrue(tokenLong >= 0, "El token debe ser un número positivo");
    }

    @Test
    public void testApiTokenIsNumeric() {
        String token = ApiTokenGenerator.generateRandomApiToken();

        assertDoesNotThrow(() -> Long.parseLong(token), "El token debe ser numérico");
    }
}
