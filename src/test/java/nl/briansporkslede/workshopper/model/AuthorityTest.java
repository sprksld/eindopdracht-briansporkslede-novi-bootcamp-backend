package nl.briansporkslede.workshopper.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorityTest {
    Authority a;

    @Test
    void shouldReturnCorrectUsername() {
        // arrange
        a = new Authority();
        a.setUsername("john");

        // act
        String result = a.getUsername();

        // assert
        assertEquals("john", result);

    }

    @Test
    void shouldReturnCorrectAuthority() {
        // arrange
        a = new Authority();
        a.setAuthority("USER");

        // act
        String result = a.getAuthority();

        // assert
        assertEquals("USER", result);

    }

    @Test
    void shouldReturnCorrectUsernameAndAuthority() {
        a = new Authority( "john", "plannertje");

        // act
        String resultingUsername = a.getUsername();
        String resultingAuthority = a.getAuthority();

        // assert
        assertEquals("john", resultingUsername);
        assertEquals("plannertje", resultingAuthority);

    }

}