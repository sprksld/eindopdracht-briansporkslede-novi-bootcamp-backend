package nl.briansporkslede.workshopper.model;

import nl.briansporkslede.workshopper.dto.UserInputDto;
import nl.briansporkslede.workshopper.dto.UserOutputDto;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

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
        assertNotEquals("moresecrets", outputDto.getPassword());
        assertTrue(outputDto.getEnabled());
        assertEquals("987654321", outputDto.getApikey());

    }
}