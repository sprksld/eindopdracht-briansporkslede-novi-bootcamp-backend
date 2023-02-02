package nl.briansporkslede.workshopper.dto;

import nl.briansporkslede.workshopper.model.Booking;
import nl.briansporkslede.workshopper.model.Student;
import nl.briansporkslede.workshopper.model.Workshop;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

public class BookingInputDto {
    public Long id;
    @NotNull
    public Student student;
    @NotNull
    public Workshop workshop;
    public Boolean attended;
    public String feedback;
    public LocalDateTime dtBooked;

    public Booking toClass() {
        Booking booking = new Booking();

        booking.setStudent(this.student);
        booking.setWorkshop(this.workshop);
        booking.setAttended(this.attended);
        booking.setFeedback(this.feedback);
        booking.setDtBooked(this.dtBooked);

        return booking;
    }
}