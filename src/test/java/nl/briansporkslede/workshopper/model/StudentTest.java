package nl.briansporkslede.workshopper.model;

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
}