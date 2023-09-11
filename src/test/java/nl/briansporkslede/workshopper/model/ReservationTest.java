package nl.briansporkslede.workshopper.model;

import nl.briansporkslede.workshopper.dto.ReservationInputDto;
import nl.briansporkslede.workshopper.dto.ReservationOutputDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {

    @Test
    void shouldReturnCorrectPriority() {
        // arrange
        Reservation reservation = new Reservation();
        reservation.setPriority(3);

        // act
        Integer priority = reservation.getPriority();

        // assert
        assertEquals(3, priority);

    }

    @Test
    void testClassToDtoViceVersa() {
        // arrange
        Student student = new Student();
        student.setName("Rustige Jacob");
        Workshop workshop = new Workshop();
        workshop.setTitle("Reserveren kun je leren");

        ReservationInputDto inputDto = new ReservationInputDto();
        inputDto.student = student;
        inputDto.workshop = workshop;
        inputDto.dtReserved = LocalDateTime.parse("2023-09-25T08:59");

        // act
        Reservation reservation = inputDto.toClass();
        ReservationOutputDto outputDto = new ReservationOutputDto();
        outputDto = outputDto.toDto(reservation);

        Student retrievedStudent = reservation.getStudent();
        Workshop retrievedWorkshop = reservation.getWorkshop();

        // assert
        assertEquals("Rustige Jacob", retrievedStudent.getName());
        assertEquals("Reserveren kun je leren", retrievedWorkshop.getTitle());
        assertEquals("2023-09-25T08:59", outputDto.dtReserved.toString());

    }

}