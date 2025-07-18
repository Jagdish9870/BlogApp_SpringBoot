package com.jack.BloggingApp.users.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.jack.BloggingApp.security.JWTService;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JWTServiceTests {

    JWTService jwtService = new JWTService();

    @Test
    void canCreateJwtFromUserId () {
        var jwt = jwtService.createJwt(1001L);

        assertNotNull(jwt);
    }
}
