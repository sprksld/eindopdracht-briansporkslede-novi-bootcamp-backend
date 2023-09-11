package nl.briansporkslede.workshopper.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthInputDtoTest {

    @Test
    void shouldReturnSpecifiedValues() {
        // arrange
        AuthInputDto inputDto = new AuthInputDto();
        inputDto.username = "brian";
        inputDto.password = "zeergeheim";

        // act
        String username = inputDto.username;
        String password = inputDto.password;

        // assert
        assertEquals("brian", username);
        assertEquals("zeergeheim", password);
    }

}