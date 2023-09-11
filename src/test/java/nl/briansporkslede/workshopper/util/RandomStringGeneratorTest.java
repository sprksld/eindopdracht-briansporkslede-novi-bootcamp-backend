package nl.briansporkslede.workshopper.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomStringGeneratorTest {

    @Test
    void generatedStringsShouldNotMatch() {
        // arrange and act
        String firstRandomString = RandomStringGenerator.generateAlphaNumeric(10);
        String secondRandomString = RandomStringGenerator.generateAlphaNumeric(10);

        // assert
        assertNotEquals( firstRandomString, secondRandomString);
    }

    @Test
    void shouldReturnStringOfGivenLength() {
        // arrange
        String withLength5;
        String withLength10;
        String withLength15;
        String withLength20;
        String withLength80;

        // act
        withLength5 = RandomStringGenerator.generateAlphaNumeric(5);
        withLength10 = RandomStringGenerator.generateAlphaNumeric(10);
        withLength15 = RandomStringGenerator.generateAlphaNumeric(15);
        withLength20 = RandomStringGenerator.generateAlphaNumeric(20);
        withLength80 = RandomStringGenerator.generateAlphaNumeric(80);

        // assert
        assertEquals( 5, withLength5.length());
        assertEquals( 10, withLength10.length());
        assertEquals( 15, withLength15.length());
        assertEquals( 20, withLength20.length());
        assertEquals( 80, withLength80.length());

    }
}