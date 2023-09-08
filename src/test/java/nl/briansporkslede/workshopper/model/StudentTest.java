package nl.briansporkslede.workshopper.model;

import nl.briansporkslede.workshopper.dto.StudentInputDto;
import nl.briansporkslede.workshopper.dto.StudentOutputDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.print.Book;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    Student student;

    Workshop workshop, workshop1, workshop2, workshop3;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setName("Nieuwe Student");
        student.setGender('M');
        student.setClassName("1E");
        student.setGradeYear(1);

        workshop = new Workshop();
        workshop1 = new Workshop();
        workshop1.setTitle("DJ Workshop");
        workshop2 = new Workshop();
        workshop1.setTitle("Breien");
        workshop3 = new Workshop();
        workshop1.setTitle("Schilderen");

    }

    @Test
    void shouldKeepStudentName() {
        // arrange
        student.setName("Brian");

        // act
        String name = student.getName();
        Character gender = student.getGender();
        String className = student.getClassName();
        Integer gradeYear = student.getGradeYear();

        // assert
        assertEquals("Brian", name);
        assertEquals('M', gender);
        assertEquals("1E", className);
        assertEquals(1, gradeYear);
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

    @Test
    void setReservations() {
        // arrange
        Reservation reservation1 = new Reservation();
        reservation1.setStudent(student);
        reservation1.setWorkshop(workshop1);

        Reservation reservation2 = new Reservation();
        List<Reservation> reservations = List.of(reservation1, reservation2);

        student.setName("Serge");

        // act
        student.setReservations(reservations);
        Iterable<Reservation> reservation = student.getReservations();

        // assert
        assertEquals("Serge", student.getName());
        assertEquals(workshop1.getTitle(), reservations.iterator().next().getWorkshop().getTitle());


    }

    @Test
    void setBookings() {

        // arrange
        Booking booking1 = new Booking();
        booking1.setStudent(student);
        booking1.setWorkshop(workshop1);

        Booking booking2 = new Booking();
        booking2.setStudent(student);
        booking2.setWorkshop(workshop2);

        List<Booking> bookings = List.of(booking1, booking2);

        student.setName("Brooke");

        // act
        student.setBookings(bookings);
        Iterable<Booking> booking = student.getBookings();

        // assert
        assertEquals("Brooke", student.getName());
        assertEquals(workshop1.getTitle(), bookings.iterator().next().getWorkshop().getTitle());

    }

}