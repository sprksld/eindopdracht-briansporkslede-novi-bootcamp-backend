package nl.briansporkslede.workshopper.dto;

import nl.briansporkslede.workshopper.model.Authority;
import nl.briansporkslede.workshopper.model.Student;
import nl.briansporkslede.workshopper.model.Teacher;
import nl.briansporkslede.workshopper.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserOutputDtoTest {

    UserOutputDto dto;
    Student student;
    Teacher mentor;

    @BeforeEach
    void setUp() {
        mentor = new Teacher();
        mentor.setName("Mentortje");

        student = new Student();
        student.setMentor(mentor);

        dto = new UserOutputDto();
        dto.setMentor(mentor);
        dto.setStudent(student);
    }

    @Test
    void getAuthorities() {
        Authority authority = new Authority("piet","ADMIN");

        User user = new User();
        user.setUsername("piet");
        user.addAuthority(authority);

        UserOutputDto userOutputDto = new UserOutputDto();
        userOutputDto = userOutputDto.toDto(user);
        Set<Authority> authoritySet = userOutputDto.getAuthorities();
        assertEquals("ADMIN", authoritySet.iterator().next().getAuthority());

    }

    @Test
    void getMentor() {
        mentor.setId(3L);
        assertEquals(mentor, dto.getMentor());
        assertEquals(3L, dto.getMentor().getId());
    }

    @Test
    void getStudent() {
        student.setId(73L);
        assertEquals(student, dto.getStudent());
        assertEquals(73L, dto.getStudent().getId());
    }

}