package nl.briansporkslede.workshopper.payload;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationResponseTest {

    @Test
    void testAuthenticationRespons () {
        AuthenticationResponse ar = new AuthenticationResponse("12345678abcdefg");

        assertEquals( "12345678abcdefg", ar.getJwt());
    }

}