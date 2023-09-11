package nl.briansporkslede.workshopper.dto;

import nl.briansporkslede.workshopper.model.Teacher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeacherOutputDtoTest {

    @Test
    void shouldReturnCorrectValues() {
        // arrange
        TeacherOutputDto outputDto = new TeacherOutputDto();
        outputDto.id = 123L;
        outputDto.name = "Miltenburg";

        // act
        Teacher teacher = new Teacher();
        teacher = outputDto.toClass();

        // assert
        assertEquals(123, teacher.getId());
        assertEquals("Miltenburg", teacher.getName());

    }

}