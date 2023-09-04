package nl.briansporkslede.workshopper.model;

import nl.briansporkslede.workshopper.dto.UserInputDto;
import nl.briansporkslede.workshopper.dto.UserOutputDto;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    User u;

    @Test
    void shouldReturnCorrectUsernameAndEmail() {
        // arrange
        u = new User();
        u.setUsername("brian");
        u.setEmail("brian@example.com");
        u.setEnabled(true);

        // act
        String username = u.getUsername();
        String email = u.getEmail();
        Boolean enabled = u.isEnabled();

        // assert
        assertEquals("brian", username);
        assertEquals("brian@example.com", email);
        assertEquals(true, enabled);

    }

    @Test
    void shouldReturnEncryptedPassword() {
        // arrange
        UserInputDto dto = new UserInputDto();
        dto.setUsername("brian");
        dto.setPassword("this-password-is-unencrypted");
        dto.setEmail("brian@example.com");
        dto.setEnabled(true);
        User user = dto.toClass();

        // act
        String username = user.getUsername();
        String password = user.getPassword();
        String email = user.getEmail();
        Boolean enabled = user.isEnabled();

        // assert
        assertEquals("brian", username);
        assertNotEquals("this-password-is-unencrypted", password);    // has to be encoded
        assertEquals("brian@example.com", email);
        assertEquals(true, enabled);

    }

    @Test
    void shouldReturnCorrectAuthorities() {
        // arrange
        Authority auth1 = new Authority();
        Authority auth2 = new Authority();
        Authority auth3 = new Authority();      // for xtra negative test
        auth1.setAuthority("EXTRA");
        auth2.setAuthority("SUPER");
        auth3.setAuthority("ULTRA");            // for xtra negative test
        Set<Authority> auths = new HashSet<>();
        auths.add(auth1);
        auths.add(auth2);

        // act
        User u = new User();
        u.addAuthority(auth1);
        u.addAuthority(auth2);

        // assert
        Set<Authority> authorities = u.getAuthorities();
        assertEquals(auths, authorities);
        assertArrayEquals(auths.toArray(), authorities.toArray());
/*
        // to test if authorities are removed correctly ...
        u.removeAuthority(auth1);
        u.addAuthority(auth3);
        assertArrayEquals(auths.toArray(), authorities.toArray());
*/
    }

    @Test
    void testClassToDtoViceVersa() {
        // arrange
        UserInputDto inputDto = new UserInputDto();
        inputDto.setUsername("giovanni");
        inputDto.setPassword("moresecrets");
        inputDto.setEnabled(true);
        inputDto.setEmail("giovanni@example.com");
        inputDto.setApikey("987654321");

        // act
        User user = inputDto.toClass();
        UserOutputDto outputDto = new UserOutputDto();
        outputDto = outputDto.toDto(user);

        // assert
        assertEquals("giovanni", outputDto.getUsername());
        assertEquals("giovanni@example.com", outputDto.getEmail());
        assertNotNull(outputDto.getPassword());
        assertEquals(outputDto.getPassword(), user.getPassword());
        assertNotEquals("moresecrets", outputDto.getPassword());
        assertTrue(outputDto.getEnabled());
        assertEquals("987654321", outputDto.getApikey());

    }

    @Test
    void shouldReturnSameName() {
        // arrange

        Authority authority1 = new Authority("brian", "ADMIN");
        Authority authority2 = new Authority("piet", "USER");
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority1);
        authorities.add(authority2);

        UserInputDto inputDto = new UserInputDto();
        inputDto.setUsername("Angelo");
        inputDto.setPassword("some-encoded-string");
        inputDto.setApikey("78675645");
        inputDto.setEmail("someone@example.com");
        inputDto.setEnabled(true);
        inputDto.setAuthorities(authorities);

        // act

        User user = inputDto.toClass();
        Set<Authority> retrievedAuthorities = inputDto.getAuthorities();
        Iterator itr = retrievedAuthorities.iterator();

        Boolean foundName = false;
        while (itr.hasNext()) {
            Authority auth = (Authority) itr.next();
            if ( auth.getUsername() == "brian" )
                foundName = true;
        }

        // assert

        assertEquals(inputDto.getUsername(), user.getUsername());
        assertEquals(inputDto.getPassword(), user.getPassword());
        assertEquals(inputDto.getApikey(), user.getApikey());
        assertEquals(inputDto.getEmail(), user.getEmail());
        assertEquals(inputDto.getEnabled(), user.isEnabled());

        assertEquals("Angelo", user.getUsername());
        assertNotEquals("some-encoded-string", user.getPassword());
        assertEquals("78675645", user.getApikey());
        assertEquals("someone@example.com", user.getEmail());
        assertTrue(user.isEnabled());

        assertTrue(foundName);

    }

}