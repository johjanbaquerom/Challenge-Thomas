package com.challenge;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BCryptPasswordEncoderTest {

    @Test
    public void testPasswordEncoder() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String rawPassword = "12345";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        System.out.println("Contraseña codificada: " + encodedPassword);

        // Verifica que la contraseña codificada coincida con la contraseña original
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));

        // Verifica que una contraseña incorrecta no coincida
        assertFalse(passwordEncoder.matches("wrongPassword", encodedPassword));
    }
}

