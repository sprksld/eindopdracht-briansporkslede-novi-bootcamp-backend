package nl.briansporkslede.workshopper.model;

import nl.briansporkslede.workshopper.dto.BookingInputDto;
import nl.briansporkslede.workshopper.dto.BookingOutputDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BookingTest {

    @Test
    void shouldReturnCorrectFeedback() {
        // arrange
        Booking booking = new Booking();
        booking.setAttended(true);
        booking.setFeedback("Hele leuke workshop!");

        // act
        BookingOutputDto outputDto = new BookingOutputDto();
        outputDto = outputDto.toDto(booking);

        // assert
        assertEquals( "Hele leuke workshop!", outputDto.feedback);
    }

    @Test
    void testClassToDtoViceVersa() {
        // arrange
        Workshop workshop = new Workshop();
        workshop.setTitle("Lachen kun je leren");
        Student student = new Student();
        student.setName("John Blijdschap");

        BookingInputDto inputDto = new BookingInputDto();
        inputDto.workshop = workshop;
        inputDto.student = student;
        inputDto.dtBooked = LocalDateTime.parse("2023-09-25T12:34:56");
        inputDto.feedback = "Super!";
        inputDto.attended = true;

        Booking booking = inputDto.toClass();
        BookingOutputDto outputDto = new BookingOutputDto();
        outputDto = outputDto.toDto(booking);

        // act
        Workshop retrievedWorkshop = outputDto.workshop;
        Student retrievedStudent = outputDto.student;

        // assert
        assertEquals("Lachen kun je leren", retrievedWorkshop.getTitle());
        assertEquals("John Blijdschap", retrievedStudent.getName());
        assertEquals("Super!", outputDto.feedback);
        assertEquals("2023-09-25T12:34:56", outputDto.dtBooked.toString());

    }
}