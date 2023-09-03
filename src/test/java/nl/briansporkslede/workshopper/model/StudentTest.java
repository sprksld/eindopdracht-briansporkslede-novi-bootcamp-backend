package nl.briansporkslede.workshopper.model;

import nl.briansporkslede.workshopper.dto.StudentInputDto;
import nl.briansporkslede.workshopper.dto.StudentOutputDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    Student s;

    @Test
    void shouldKeepStudentName() {
        // arrange
        s = new Student();
        s.setName("Brian");
        s.setGender('M');
        s.setClassName("1E");
        s.setGradeYear(1);

        // act
        String name = s.getName();
        Character gender = s.getGender();
        String className = s.getClassName();
        Integer gradeYear = s.getGradeYear();

        // assert
        assertEquals("Brian", name);
        assertEquals('M', gender);
        assertEquals("1E", className);
        assertEquals( 1, gradeYear);
    }

    @Test
    void testClassToDtoViceVersa() {
        // arrange
        StudentInputDto inputDto = new StudentInputDto();
        inputDto.name = "Klaasje van Dijk";
        inputDto.className = "2H";
        inputDto.gender = 'V';
        inputDto.mentor_id = 1L;

        StudentInputDto inputDto2 = new StudentInputDto();
        inputDto2.name = "Fred de Groot";
        inputDto2.className = "4B";
        inputDto2.gender = 'M';
        inputDto2.mentor_id = 0L;

        // act
        Student student = inputDto.toClass();
        StudentOutputDto outputDto = new StudentOutputDto();
        outputDto = outputDto.toDto(student);
        Student studentNoMentor = inputDto2.toClass();

        // assert
        assertEquals("Klaasje van Dijk", student.getName());
        assertEquals("Klaasje van Dijk", outputDto.name);
        assertEquals("2H", student.getClassName());
        assertEquals("2H", outputDto.className);
        assertNull(studentNoMentor.getMentor());

    }
}