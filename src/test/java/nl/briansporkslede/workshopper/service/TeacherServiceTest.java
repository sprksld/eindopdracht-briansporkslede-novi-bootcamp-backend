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

//    @Disabled
    @Test
    @WithMockUser(username="admin", roles="ADMIN")    // check authorization, not authentication
    void shouldReturnCorrectTeacherName() {
        // arrange

        TeacherInputDto inputDto = new TeacherInputDto();
        inputDto.id = 789L;
        inputDto.name = "Mijnheer Jansen";
        service.createTeacher(inputDto);

        Teacher teacher = inputDto.toClass();

        TeacherOutputDto outputDto = new TeacherOutputDto();
        outputDto.id = teacher.getId();
        outputDto.name = teacher.getName();

        Mockito.when(repos.findById(anyLong())).thenReturn(Optional.of(teacher));

        // act

        Optional<Teacher> teacherFound = repos.findById(456L);

        // assert

        assertEquals(789, teacherFound.get().getId());
        assertEquals("Mijnheer Jansen", teacherFound.get().getName());

    }
}