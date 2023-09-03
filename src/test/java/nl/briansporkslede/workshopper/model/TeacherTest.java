package nl.briansporkslede.workshopper.model;

import nl.briansporkslede.workshopper.dto.TeacherInputDto;
import nl.briansporkslede.workshopper.dto.TeacherOutputDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeacherTest {
    Teacher teacher;

    @Test
    void shouldReturnCorrectTeacherName() {
        // arrange
        teacher = new Teacher();
        teacher.setId(123L);
        teacher.setName("Fred Schrijvers");

        // act
        Long id = teacher.getId();
        String name = teacher.getName();

        // assert

        assertEquals(123L, id);
        assertEquals("Fred Schrijvers", name);
    }

    @Test
    void testClassToDtoViceVersa() {
        // arrange
        TeacherInputDto inputDto = new TeacherInputDto();
        inputDto.id = 456L;
        inputDto.name = "Aart Staartjes";

        // act
        Teacher teacher = inputDto.toClass();
        TeacherOutputDto outputDto = new TeacherOutputDto();
        outputDto = outputDto.toDto(teacher);

        // assert
        assertEquals(456L, teacher.getId());
        assertEquals("Aart Staartjes", teacher.getName());
        assertEquals("Aart Staartjes", outputDto.name);
    }
}