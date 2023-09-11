package nl.briansporkslede.workshopper.service;

import nl.briansporkslede.workshopper.dto.StudentOutputDto;
import org.springframework.stereotype.Service;
import nl.briansporkslede.workshopper.exception.RecordNotFoundException;
import nl.briansporkslede.workshopper.repository.StudentRepository;
import nl.briansporkslede.workshopper.dto.StudentInputDto;
import nl.briansporkslede.workshopper.model.Student;

import java.util.Optional;
import java.util.ArrayList;

@Service
public class StudentService {
    private final StudentRepository repos;

    public StudentService(StudentRepository r) {
        this.repos = r;
    }

    public StudentOutputDto getStudent(Long id) {
        Optional<Student> optionalStudent = repos.findById(id);
        if (optionalStudent.isEmpty())
            throw new RecordNotFoundException("student: id not found");

        Student student = optionalStudent.get();
        StudentOutputDto dto = new StudentOutputDto();
        return dto.toDto(student);
    }

    public Iterable<StudentOutputDto> getStudents() {
        Iterable<Student> allStudents = repos.findAll();
        ArrayList<StudentOutputDto> foundStudents = new ArrayList<>();

        for (Student student : allStudents) {
            StudentOutputDto dto = new StudentOutputDto();
            foundStudents.add(dto.toDto(student));
        }
        return (foundStudents);
    }

    public Long createStudent(StudentInputDto studentInputDto) {
        Student student = studentInputDto.toClass();
        repos.save(student);
        return student.getId();
    }

    public Long deleteStudent(Long id) {
        repos.deleteById(id);
        if (repos.existsById(id))
            throw new RecordNotFoundException("student could not be deleted");

        return null;
    }

    public Iterable<StudentOutputDto> getMyStudents(Long teacher_id) {
        Iterable<Student> allStudents = repos.findStudentsByMentorId(teacher_id);
        ArrayList<StudentOutputDto> foundStudents = new ArrayList<>();

        for (Student student : allStudents) {
            StudentOutputDto dto = new StudentOutputDto();
            foundStudents.add(dto.toDto(student));
        }
        return (foundStudents);
    }

}
