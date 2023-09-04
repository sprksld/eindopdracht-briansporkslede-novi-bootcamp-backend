package nl.briansporkslede.workshopper.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileUploadResponseTest {

    @Test
    void shouldReturnCorrectValues() {
        // arrange
        FileUploadResponse response = new FileUploadResponse( "hello", "text/plain", "https://example.com/hello");

        // act has nothing to do, because there's not much to test

        // assert

        assertEquals("hello", response.getFileName());
        assertEquals("text/plain", response.getContentType());
        assertEquals("https://example.com/hello", response.getUrl());

    }

}