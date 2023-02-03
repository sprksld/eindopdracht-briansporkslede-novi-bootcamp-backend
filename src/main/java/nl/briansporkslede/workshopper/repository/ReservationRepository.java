package nl.briansporkslede.workshopper.repository;

import nl.briansporkslede.workshopper.model.Booking;
import nl.briansporkslede.workshopper.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    public Iterable<Reservation> findReservationsByWorkshopId(Long id );
    public Iterable<Reservation> findReservationsByStudentId(Long id );

    public Optional<Reservation> findReservationByStudentIdAndWorkshopId(Long studentId, Long workshopId);


    @Query(value = "SELECT * FROM reservations r"
            + " JOIN students s ON r.student_id = s.id"
            + " JOIN workshops w ON r.workshop_id = w.id"
            + " WHERE r.priority is not null AND r.priority > 0"
            + " AND s.mentor_id = ?1"
            + " AND w.dt_start >= CURRENT_DATE"
            , nativeQuery = true)
    public Iterable<Reservation> findReservationsByMyStudents(Long teacherId);


    // TODO - this is te old one, CHECK IT, AND REMOVE IT ASAP
    @Query( value = "SELECT * FROM reservations r"
            + " JOIN workshops w ON w.id = r.workshop_id"
            + " JOIN students s ON s.id = r.student_id"
            + " WHERE s.mentor_id = ?1"
//            + " AND w.dt_start < CURRENT_DATE"
            , nativeQuery = true)
    Iterable<Reservation> findReservationsByMentorId(Long id);





}
