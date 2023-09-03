package nl.briansporkslede.workshopper.dto;

import nl.briansporkslede.workshopper.model.Booking;
import nl.briansporkslede.workshopper.model.Student;
import nl.briansporkslede.workshopper.model.Workshop;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookingBatchInputDtoTest {

    @Test
    void testDtoToClass() {
        // arrange
        BookingBatchInputDto inputDto = new BookingBatchInputDto();
        inputDto.student_id = 27L;
        inputDto.workshop_id = 43L;

        // act
        Booking booking = inputDto.toClass();
        Student student = booking.getStudent();
        Workshop workshop = booking.getWorkshop();

        // assert
        assertEquals(27, student.getId());
        assertEquals(43, workshop.getId());
        assertFalse( booking.isAttended());
    }

}