package nl.briansporkslede.workshopper.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileUploadResponseTest {

    @Test
    void shouldReturnCorrectValues() {
        // arrange
        FileUploadResponse response = new FileUploadResponse( "hello", "text/plain", "https://example.com/hello");

        // assert
        assertEquals("hello", response.getFileName());
        assertEquals("text/plain", response.getContentType());
        assertEquals("https://example.com/hello", response.getUrl());

    }

    @Test
    void shouldBeAbleToSetValues() {
        // arrange
        FileUploadResponse response = new FileUploadResponse();

        // act
        response.setFileName("readme.txt");
        response.setContentType("text/plain");
        response.setUrl("https://example.com/uploads/readme.txt");

        // assert
        assertEquals("readme.txt", response.getFileName());
        assertEquals("text/plain", response.getContentType());
        assertEquals("https://example.com/uploads/readme.txt", response.getUrl());
    }
}