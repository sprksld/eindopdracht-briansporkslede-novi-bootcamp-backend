package nl.briansporkslede.workshopper.controller;

import nl.briansporkslede.workshopper.dto.TeacherInputDto;
import nl.briansporkslede.workshopper.dto.TeacherOutputDto;
import nl.briansporkslede.workshopper.model.Teacher;
import nl.briansporkslede.workshopper.service.TeacherService;
import nl.briansporkslede.workshopper.util.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("${apiPrefix}/teachers")
public class TeacherController {
    private final TeacherService service;

    public TeacherController(TeacherService s) {
        this.service = s;
    }

    @GetMapping("")
    public ResponseEntity<Iterable<TeacherOutputDto>> getTeachers() {
        return ResponseEntity.ok(service.getTeachers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTeacher(@PathVariable Long id) {
        return ResponseEntity.ok(service.getTeacher(id));
    }

    @PostMapping("")
    public ResponseEntity<String> createTeacher(@Valid @RequestBody TeacherInputDto teacherInputDto, BindingResult br) {
        if (br.hasErrors()) {
            String errorStr = Utils.reportErrors(br);
            return new ResponseEntity<>(errorStr, HttpStatus.BAD_REQUEST);
        } else {
            Long createdId = service.createTeacher(teacherInputDto);

            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/teachers/" + createdId).toUriString()
            );
            return ResponseEntity.created(uri).body("Successfully created a new Teacher");
        }
    }

    @GetMapping("/list")
    public List<Teacher> getTeacherz() {
        List<Teacher> teachers;
        teachers = service.getTeacherz();
        return teachers;
    }

}
