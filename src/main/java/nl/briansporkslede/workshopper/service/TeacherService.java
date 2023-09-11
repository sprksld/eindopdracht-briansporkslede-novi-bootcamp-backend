package nl.briansporkslede.workshopper.service;

import nl.briansporkslede.workshopper.dto.TeacherOutputDto;
import org.springframework.stereotype.Service;
import nl.briansporkslede.workshopper.exception.RecordNotFoundException;
import nl.briansporkslede.workshopper.repository.TeacherRepository;
import nl.briansporkslede.workshopper.dto.TeacherInputDto;
import nl.briansporkslede.workshopper.model.Teacher;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class TeacherService {
    private final TeacherRepository repos;

    public TeacherService( TeacherRepository r) {
        this.repos = r;
    }

    public Iterable<TeacherOutputDto> getTeachers() {
        Iterable<Teacher> allTeachers = repos.findAll();
        ArrayList<TeacherOutputDto> foundTeachers = new ArrayList<>();

        for( Teacher teacher : allTeachers ) {
            TeacherOutputDto dto = new TeacherOutputDto();
            foundTeachers.add(dto.toDto(teacher));
        }
        return( foundTeachers );
    }

    public TeacherOutputDto getTeacher(Long id) {
        Optional<Teacher> optionalTeacher = repos.findById(id);
        if ( optionalTeacher.isEmpty())
            throw new RecordNotFoundException("teacher id not found");

        Teacher teacher = optionalTeacher.get();
        TeacherOutputDto dto = new TeacherOutputDto();
        return dto.toDto(teacher);
    }

    public Long createTeacher( TeacherInputDto teacherInputDto ) {
        Teacher teacher = teacherInputDto.toClass();
        repos.save(teacher);
        return teacher.getId();
    }

    public List<Teacher> getTeacherList() {
        return repos.findAll();
    }
}
