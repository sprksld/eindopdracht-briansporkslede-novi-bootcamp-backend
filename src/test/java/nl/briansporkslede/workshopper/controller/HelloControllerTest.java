package nl.briansporkslede.workshopper.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloControllerTest {
    HelloController hello = new HelloController();

    @Test
    void helloWorld() {
        assertEquals("Hello from Spring Boot", hello.helloWorld());
    }
}