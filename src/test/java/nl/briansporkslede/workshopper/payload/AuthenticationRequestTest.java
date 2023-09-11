package nl.briansporkslede.workshopper.payload;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationRequestTest {

    @Test
    void shouldReturnCorrectValues() {
        AuthenticationRequest ar1 = new AuthenticationRequest( "piet", "password");
        AuthenticationRequest ar2 = new AuthenticationRequest();
        ar2.setUsername("john");
        ar2.setPassword("secret");

        assertEquals("piet", ar1.getUsername());
        assertEquals("password", ar1.getPassword());
        assertEquals("john", ar2.getUsername());
        assertEquals("secret", ar2.getPassword());
    }

}