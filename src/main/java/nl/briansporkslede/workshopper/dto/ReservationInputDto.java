package nl.briansporkslede.workshopper.dto;

import nl.briansporkslede.workshopper.model.Reservation;
import nl.briansporkslede.workshopper.model.Student;
import nl.briansporkslede.workshopper.model.Workshop;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

public class ReservationInputDto {
    public Long id;
    @PastOrPresent
    public LocalDateTime dtReserved;
    @PastOrPresent
    public LocalDateTime dtCancelled;
    @PastOrPresent
    public LocalDateTime dtProcessed;
    public Integer priority;
    @NotNull
    public Student student;
    @NotNull
    public Workshop workshop;

    public Reservation toClass() {
        Reservation reservation = new Reservation();

        reservation.setDtReserved(this.dtReserved);
        reservation.setDtCancelled(this.dtCancelled);
        reservation.setDtProcessed(this.dtProcessed);
        reservation.setPriority(this.priority);


        reservation.setStudent(this.student);
        reservation.setWorkshop(this.workshop);

        return reservation;
    }
}
