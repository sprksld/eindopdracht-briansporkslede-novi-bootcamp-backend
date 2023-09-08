package nl.briansporkslede.workshopper.service;

import nl.briansporkslede.workshopper.dto.StudentInputDto;
import nl.briansporkslede.workshopper.dto.StudentOutputDto;
import nl.briansporkslede.workshopper.dto.TeacherInputDto;
import nl.briansporkslede.workshopper.exception.RecordNotFoundException;
import nl.briansporkslede.workshopper.model.Student;
import nl.briansporkslede.workshopper.model.Teacher;
import nl.briansporkslede.workshopper.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    StudentRepository repos;

    @InjectMocks
    StudentService studentService;
    Student student1;
    Student student2;
    Student newStudent;
    Student foundStudent;
    StudentInputDto studentInputDto1;
    StudentInputDto studentInputDto2;
    Teacher mentor;
    TeacherInputDto mentorInputDto;

    @BeforeEach
    void setUp() {
        mentor = new Teacher();
        mentor.setName("De heer Verkerk");
        mentor.setId(1L);

        studentInputDto1 = new StudentInputDto();
        studentInputDto1.name = "Jantje de Groot";

        studentInputDto2 = new StudentInputDto();
        studentInputDto2.name = "Keesje Dekker";

        student1 = new Student();
        student1.setId(1L);
        student1.setName("Student 1");
        student1.setMentor(mentor);

        student2 = new Student();
        student2.setId(2L);
        student2.setName("Student 2");
        student2.setMentor(mentor);

        newStudent = new Student();
        newStudent.setId(3L);
        newStudent.setName("New Student (three)");
        newStudent.setMentor(mentor);

        foundStudent = new Student();
        foundStudent.setId(123L);
        foundStudent.setName("Piet Jansen");

    }

    @Test
    void getSingleStudent() {
        // arrange
        when(repos.findById(2L)).thenReturn(Optional.ofNullable(student1));

        // act
        StudentOutputDto studentFound = studentService.getStudent(2L);

        // assert
        assertEquals(student1.getName(), studentFound.name);
        assertThrows(RecordNotFoundException.class, () -> studentService.getStudent(1234L) );

    }

    @Test
    void getAllStudents() {
        // arrange
        when(repos.findAll()).thenReturn(List.of(student1, student2));

        // act
        Iterable<StudentOutputDto> studentsFound = studentService.getStudents();

        // assert
        assertEquals(student1.getName(), studentsFound.iterator().next().name);
    }

    @Test
    void createStudent() {
        // arrange
        when(repos.save(any(Student.class))).thenReturn(newStudent);
        studentInputDto1.mentor_id = mentor.getId();

        // act
        studentService.createStudent(studentInputDto1);
        Student createdStudent = studentInputDto1.toClass();

        // assert
        assertEquals(studentInputDto1.name, createdStudent.getName());
    }

    @Test
    void getMyStudents() {
        // arrange
        when(repos.findStudentsByMentorId(anyLong())).thenReturn(List.of(student1, student2));

        // act
        Iterable<StudentOutputDto> studentsFound = studentService.getMyStudents(1L);

        // assert
        assertEquals(student1.getName(), studentsFound.iterator().next().name);

    }

    @Test
    void deleteStudent() {
        // arrange
        when(repos.existsById(3L)).thenReturn(false);
        when(repos.existsById(31L)).thenReturn(true);

        // act n assert
        assertThrows(RecordNotFoundException.class, () -> studentService.deleteStudent(31L));
        studentService.deleteStudent(3L);
        verify(repos).deleteById(3L);
    }

}