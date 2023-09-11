package nl.briansporkslede.workshopper.controller;

import nl.briansporkslede.workshopper.dto.StudentInputDto;
import nl.briansporkslede.workshopper.dto.StudentOutputDto;
import nl.briansporkslede.workshopper.dto.UserOutputDto;
import nl.briansporkslede.workshopper.service.StudentService;
import nl.briansporkslede.workshopper.service.UserService;
import nl.briansporkslede.workshopper.util.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@CrossOrigin
@RestController
@RequestMapping("${apiPrefix}/students")
public class StudentController {
    private final StudentService service;

    private final UserService userService;

    public StudentController(StudentService s, UserService u) {
        this.service = s;
        this.userService = u;
    }

    @GetMapping
    public ResponseEntity<Iterable<StudentOutputDto>> getStudents() {
        return ResponseEntity.ok(service.getStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getStudent(@PathVariable Long id) {
        return ResponseEntity.ok(service.getStudent(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable Long id) {
        if (service.deleteStudent(id) != null)
            return new ResponseEntity<>("unknown student", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>("student removed", HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<String> createStudent(@Valid @RequestBody StudentInputDto studentInputDto, BindingResult br) {
        System.out.println("Studentje aanvragen ..?");
        if (br.hasErrors()) {
            String errorStr = Utils.reportErrors(br);
//            System.out.println(errorStr);
            return new ResponseEntity<>(errorStr, HttpStatus.BAD_REQUEST);
        } else {
            Long createdId = service.createStudent(studentInputDto);

            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/students/" + createdId).toUriString()
            );
            return ResponseEntity.created(uri).body("Created student");
        }
    }

    @GetMapping("/onlymine")
    public ResponseEntity<Iterable<StudentOutputDto>> myStudents() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("In de functie myStudents()");
        if (! (auth.getPrincipal() instanceof UserDetails)) {
            System.out.println("Helaas geen match qua UserDetails");
            return null;
        } else {
            System.out.println("We're on to something ...");
        }

        UserDetails ud = (UserDetails) auth.getPrincipal();
        UserOutputDto optionalUser = userService.getUser(ud.getUsername());

        System.out.println( "Jij bent ... " + ud.getUsername()
                + "\n\n" + optionalUser.username
                + "\n\n" + optionalUser.mentor.getId()
        );

        System.out.println(optionalUser.mentor.getId());

        return ResponseEntity.ok(service.getMyStudents(optionalUser.mentor.getId()));

    }


}
