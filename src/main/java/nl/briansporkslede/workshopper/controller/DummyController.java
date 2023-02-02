package nl.briansporkslede.workshopper.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@CrossOrigin
@RestController
public class DummyController {
    @GetMapping("/error")
    public ResponseEntity<String> error() {
        return new ResponseEntity<>("Er is iets misgegaan ...", HttpStatus.NOT_FOUND);
    }
}