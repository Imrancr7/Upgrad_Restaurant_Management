package com.upgradProject.restaurantManagementSystem.service;


import com.upgradProject.restaurantManagementSystem.service.impls.JWTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Base64;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class JWTServiceTest {

    private static final String SECRET = Base64.getEncoder().encodeToString("mysecretkeymysecretkeymysecretkey12".getBytes());
    private JWTService jwtService;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        jwtService = new JWTService(SECRET);
        userDetails = new User("testuser", "password", Collections.singleton(() -> "ROLE_USER"));
    }

    @Test
    void generateToken_CreatesValidJwtToken() {
        String token = jwtService.generateToken(userDetails);
        assertThat(token).isNotBlank();
        String username = jwtService.extractUserName(token);
        assertThat(username).isEqualTo("testuser");
    }

    @Test
    void extractUserName_ReturnsCorrectUsername() {
        String token = jwtService.generateToken(userDetails);
        String username = jwtService.extractUserName(token);
        assertThat(username).isEqualTo("testuser");
    }

    @Test
    void validateToken_ReturnsTrueForValidToken() {
        String token = jwtService.generateToken(userDetails);
        boolean valid = jwtService.validateToken(token, userDetails);
        assertThat(valid).isTrue();
    }

    @Test
    void validateToken_ReturnsFalseForInvalidUsername() {
        String token = jwtService.generateToken(userDetails);
        UserDetails otherUser = new User("otheruser", "password", Collections.singleton(() -> "ROLE_USER"));
        boolean valid = jwtService.validateToken(token, otherUser);
        assertThat(valid).isFalse();
    }

    @Test
    void extractUserName_ThrowsExceptionForMalformedToken() {
        assertThatThrownBy(() -> jwtService.extractUserName("not.a.jwt.token"))
                .isInstanceOf(Exception.class);
    }

    @Test
    void extractUserName_ThrowsExceptionForNullToken() {
        assertThatThrownBy(() -> jwtService.extractUserName(null))
                .isInstanceOf(Exception.class);
    }
}
