package nl.briansporkslede.workshopper.service;

import net.bytebuddy.asm.Advice;
import nl.briansporkslede.workshopper.dto.ReservationOutputDto;
import nl.briansporkslede.workshopper.model.Student;
import nl.briansporkslede.workshopper.model.Workshop;
import org.springframework.stereotype.Service;
import nl.briansporkslede.workshopper.exception.RecordNotFoundException;
import nl.briansporkslede.workshopper.repository.ReservationRepository;
import nl.briansporkslede.workshopper.dto.ReservationInputDto;
import nl.briansporkslede.workshopper.model.Reservation;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class ReservationService {
    private final ReservationRepository repos;

    public ReservationService( ReservationRepository r) {
        this.repos = r;
    }

    public Iterable<ReservationOutputDto> getReservations() {
        Iterable<Reservation> allReservations = repos.findAll();
        ArrayList<ReservationOutputDto> foundReservations = new ArrayList<>();

        for( Reservation reservation : allReservations ) {
            ReservationOutputDto dto = new ReservationOutputDto();
            foundReservations.add(dto.toDto(reservation));
        }
        return( foundReservations );
    }

    public ReservationOutputDto getReservation(Long id) {
        Optional<Reservation> optionalReservation = repos.findById(id);
        if ( optionalReservation.isEmpty())
            throw new RecordNotFoundException("reservation id not found");

        Reservation reservation = optionalReservation.get();
        ReservationOutputDto dto = new ReservationOutputDto();
        return dto.toDto(reservation);
    }

    public Long createReservation( ReservationInputDto reservationInputDto ) {
        Reservation reservation = reservationInputDto.toClass();
        repos.save(reservation);
        return reservation.getId();
    }

    public ReservationOutputDto getReservationByStudentIdAndWorkshopId(Long studentId, Long workshopId) {
        Optional<Reservation> optionalReservation = repos.findReservationByStudentIdAndWorkshopId(studentId, workshopId);
        if ( optionalReservation.isEmpty())
            throw new RecordNotFoundException("Die moet dus nog gemaakt worden ...");

        Reservation reservation = optionalReservation.get();
        ReservationOutputDto dto = new ReservationOutputDto();
        return dto.toDto(reservation);
    }


    public ReservationOutputDto like( Long studentId, Long workshopId, Integer likeAmount ) {
        ReservationOutputDto dto = new ReservationOutputDto();
        Optional<Reservation> optionalReservation = repos.findReservationByStudentIdAndWorkshopId(studentId, workshopId);
        if ( optionalReservation.isEmpty()) {
//            throw new RecordNotFoundException("Die moet dus nog gemaakt worden ...");
            Reservation reservation = new Reservation();
            Student student = new Student();
            Workshop workshop = new Workshop();

            student.setId(studentId);
            workshop.setId(workshopId);
            reservation.setStudent(student);
            reservation.setWorkshop(workshop);
            reservation.setPriority(likeAmount);
            reservation.setDtReserved(LocalDateTime.now());
            repos.save(reservation);
            return dto.toDto(reservation);
        } else {
            Reservation or = optionalReservation.get();
            or.setPriority(likeAmount);
            repos.save(or);
            return dto.toDto(or);
        }
    }

    public Iterable<ReservationOutputDto> getReservationsByStudent( Long studentId) {
        Iterable<Reservation> allReservations = repos.findReservationsByStudentId(studentId);
        ArrayList<ReservationOutputDto> foundReservations = new ArrayList<>();

        for( Reservation reservation : allReservations ) {
            ReservationOutputDto dto = new ReservationOutputDto();
            foundReservations.add(dto.toDto(reservation));
        }
        return( foundReservations );
    }

    public Iterable<ReservationOutputDto> getReservationsByTeacher( Long teacherId) {
        Iterable<Reservation> allReservations = repos.findReservationsByTeacherId(teacherId);
        ArrayList<ReservationOutputDto> foundReservations = new ArrayList<>();

        for( Reservation reservation : allReservations ) {
            ReservationOutputDto dto = new ReservationOutputDto();
            foundReservations.add(dto.toDto(reservation));
        }
        return( foundReservations );
    }


}
