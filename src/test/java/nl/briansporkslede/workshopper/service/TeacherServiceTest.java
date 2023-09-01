package nl.briansporkslede.workshopper.service;

import nl.briansporkslede.workshopper.dto.TeacherInputDto;
import nl.briansporkslede.workshopper.dto.TeacherOutputDto;
import nl.briansporkslede.workshopper.model.Teacher;
import nl.briansporkslede.workshopper.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {
    @Mock
    TeacherRepository repos;

    @Mock
    TeacherService service;
    
    @Test
    @WithMockUser(username="planner", roles="PLANNER")    // check authorization, not authentication
    void shouldReturnCorrectTeacher() {
        // arrange

        TeacherInputDto inputDto = new TeacherInputDto();
        inputDto.name = "Mijnheer Jansen";

        Teacher teacher = new Teacher();
        teacher = inputDto.toClass();

        teacher.setId(1L);
        teacher.setName("Mijnheer Janssen");
        Mockito.when(repos.findById(anyLong())).thenReturn(Optional.of(teacher));

        // act
        TeacherOutputDto outputDto = service.getTeacher(1L);

        // assert
        assertEquals("Mijnheer Jansen", outputDto.name );
        
    }
}