package nl.briansporkslede.workshopper.service;

import nl.briansporkslede.workshopper.dto.StudentOutputDto;
import nl.briansporkslede.workshopper.model.Student;
import nl.briansporkslede.workshopper.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    StudentRepository repos;

    @InjectMocks
    StudentService service;

    @Test
//    @WithMockUser(username="planner", roles="PLANNER")    // check authorization, not authentication
    void shouldReturnCorrectStudent() {
        // arrange
        Student student = new Student();
        student.setId(123L);
        student.setName("Piet Jansen");
        Mockito.when(repos.findById(anyLong())).thenReturn(Optional.of(student));

        // act

        StudentOutputDto odto = service.getStudent(123L);

        // assert

        assertEquals("Piet Jansen", odto.name );

    }
}