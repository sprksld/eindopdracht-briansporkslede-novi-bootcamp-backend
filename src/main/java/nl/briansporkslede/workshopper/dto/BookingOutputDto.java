package nl.briansporkslede.workshopper.dto;

import nl.briansporkslede.workshopper.model.Booking;
import nl.briansporkslede.workshopper.model.Student;
import nl.briansporkslede.workshopper.model.Workshop;

import java.time.LocalDateTime;

public class BookingOutputDto {
    public Long id;
    public Student student;
    public Workshop workshop;
    public Boolean attended;
    public String feedback;
    public LocalDateTime dtBooked;

    public BookingOutputDto toDto(Booking booking) {
        BookingOutputDto dto = new BookingOutputDto();

        dto.id = booking.getId();
        dto.student = booking.getStudent();
        dto.workshop = booking.getWorkshop();
        dto.attended = booking.isAttended();
        dto.feedback = booking.getFeedback();
        dto.dtBooked = booking.getDtBooked();

        return dto;
    }

}