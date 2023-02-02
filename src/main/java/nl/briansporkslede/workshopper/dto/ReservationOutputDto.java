package nl.briansporkslede.workshopper.dto;

import nl.briansporkslede.workshopper.model.Reservation;
import nl.briansporkslede.workshopper.model.Student;
import nl.briansporkslede.workshopper.model.Workshop;

import java.time.LocalDateTime;

public class ReservationOutputDto {
    public Long id;
    public LocalDateTime dtReserved;
    public LocalDateTime dtCancelled;
    public LocalDateTime dtProcessed;
    public Integer priority;
    public Student student;
    public Workshop workshop;

    public ReservationOutputDto toDto(Reservation reservation) {
        ReservationOutputDto dto = new ReservationOutputDto();

        dto.id = reservation.getId();
        dto.dtReserved = reservation.getDtReserved();
        dto.dtCancelled = reservation.getDtCancelled();
        dto.dtProcessed = reservation.getDtProcessed();
        dto.priority = reservation.getPriority();
        dto.student = reservation.getStudent();
        dto.workshop = reservation.getWorkshop();

        return dto;
    }

}