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
        Teacher mentor = new Teacher();
        mentor.setName("Aad Veldhuizen");
        Student student = new Student();
        student.setName("Leer Ling");
        UserInputDto dto = new UserInputDto();
        dto.setEnabled(true);
        dto.setUsername("brian");
        dto.setPassword("this-password-is-unencrypted");
        dto.setEmail("brian@example.com");
        dto.setMentor(mentor);
        dto.setStudent(student);
        User user = dto.toClass();

        // act
        Boolean enabled = user.isEnabled();
        String username = user.getUsername();
        String password = user.getPassword();
        String email = user.getEmail();
        Teacher myMentor = user.getMentor();

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
        auth1.setAuthority("EXTRA");
        auth2.setAuthority("SUPER");
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
    }

    @Test
    void testRemovalOfAuthorities() {
        // arrange
        Authority auth1 = new Authority( "bob", "TEACHER" );
        Authority auth2 = new Authority( "bob", "STUDENT");

        Set<Authority> auths = new HashSet<>();
        auths.add(auth2);

        User u = new User();
        u.addAuthority(auth1);
        u.addAuthority(auth2);
        u.removeAuthority(auth1);

        assertArrayEquals(auths.toArray(), u.getAuthorities().toArray());

//        setMentor
//        setStudent
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