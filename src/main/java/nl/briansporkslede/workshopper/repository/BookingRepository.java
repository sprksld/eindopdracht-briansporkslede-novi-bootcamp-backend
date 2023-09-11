package nl.briansporkslede.workshopper.repository;

import nl.briansporkslede.workshopper.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Iterable<Booking> findBookingsByWorkshopId( Long id );
    Iterable<Booking> findBookingsByStudentId( Long id );

    @Query( value = "SELECT * FROM bookings b"
            + " JOIN workshops w ON b.workshop_id = w.id"
            + " WHERE student_id = ?1"
            + " ORDER BY w.dt_start"
            , nativeQuery = true)
    Iterable<Booking> findBookingsByStudent( Long id );

    @Query( value = "SELECT * FROM bookings b"
            + " JOIN workshops w ON w.id = b.workshop_id"
            + " JOIN students s ON s.id = b.student_id"
            + " WHERE s.mentor_id = ?1"
//            + " AND w.dt_start < CURRENT_DATE"
            , nativeQuery = true)
    Iterable<Booking> findBookingsForMyStudents( Long id );

    @Query( value = "SELECT * FROM bookings b"
            + " JOIN workshops w ON w.id = b.workshop_id"
            + " JOIN students s ON s.id = b.student_id"
            + " WHERE s.mentor_id = ?1"
//            + " AND w.dt_start <= CURRENT_DATE"
            , nativeQuery = true)
    Iterable<Booking> findBookingsFeedbackForMentor( Long id);

    @Query( value = "SELECT * FROM bookings b"
            + " JOIN workshops w ON w.id = b.workshop_id"
            + " JOIN students s ON s.id = b.student_id"
            + " WHERE w.teacher_id = ?1"
//            + " AND w.dt_start <= CURRENT_DATE"
            , nativeQuery = true)
    Iterable<Booking> findBookingsFeedbackForTeacher( Long id);

}