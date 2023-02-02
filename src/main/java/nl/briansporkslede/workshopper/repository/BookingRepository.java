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
}
