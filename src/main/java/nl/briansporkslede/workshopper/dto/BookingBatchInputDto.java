package nl.briansporkslede.workshopper.dto;

import nl.briansporkslede.workshopper.model.Booking;
import nl.briansporkslede.workshopper.model.Student;
import nl.briansporkslede.workshopper.model.Workshop;

import java.time.LocalDateTime;

public class BookingBatchInputDto {
    public Long student_id;
    public Long workshop_id;

    public Booking toClass() {
        Booking booking = new Booking();
        Student student = new Student();
        Workshop workshop = new Workshop();

        student.setId( this.student_id );
        workshop.setId( this.workshop_id);
        booking.setWorkshop(workshop);
        booking.setStudent(student);
        booking.setDtBooked(LocalDateTime.now());
        booking.setAttended(false);

        return booking;
    }

}
