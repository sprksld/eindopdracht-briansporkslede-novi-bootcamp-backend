package nl.briansporkslede.workshopper.service;

import nl.briansporkslede.workshopper.dto.BookingBatchInputDto;
import nl.briansporkslede.workshopper.dto.BookingOutputDto;
import org.springframework.stereotype.Service;
import nl.briansporkslede.workshopper.exception.RecordNotFoundException;
import nl.briansporkslede.workshopper.repository.BookingRepository;
import nl.briansporkslede.workshopper.dto.BookingInputDto;
import nl.briansporkslede.workshopper.model.Booking;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class BookingService {
    private final BookingRepository repos;

    public BookingService( BookingRepository r) {
        this.repos = r;
    }

    public Long createBooking( BookingInputDto bookingInputDto ) {
        Booking booking = new Booking();
        repos.save(booking);
        return booking.getId();
    }

    public String createBookings(List<BookingBatchInputDto> allBookings ) {
        for( BookingBatchInputDto dto : allBookings ) {
            repos.save( dto.toClass() );
        }
        return "Gelukt, volgens mij!";
    }
    public BookingOutputDto getBooking(Long id) {
        Optional<Booking> optionalBooking = repos.findById(id);
        if ( optionalBooking.isEmpty())
            throw new RecordNotFoundException("booking id not found");

        Booking booking = optionalBooking.get();
        BookingOutputDto dto = new BookingOutputDto();
        return dto.toDto(booking);
    }

    public Iterable<BookingOutputDto> getBookings() {
        Iterable<Booking> allBookings = repos.findAll();
        ArrayList<BookingOutputDto> foundBookings = new ArrayList<>();

        for( Booking booking : allBookings ) {
            BookingOutputDto dto = new BookingOutputDto();
            foundBookings.add(dto.toDto(booking));
        }
        return( foundBookings );
    }
    public Iterable<BookingOutputDto> getBookingsByStudent(Long studentId) {
        Iterable<Booking> allBookings = repos.findBookingsByStudent(studentId);
        ArrayList<BookingOutputDto> foundBookings = new ArrayList<>();

        for( Booking booking : allBookings ) {
            BookingOutputDto dto = new BookingOutputDto();
            foundBookings.add(dto.toDto(booking));
        }
        return( foundBookings );
    }

    public Iterable<BookingOutputDto> getBookingsFeedbackForMentor(Long mentorId) {
        Iterable<Booking> allBookings = repos.findBookingsFeedbackForMentor(mentorId);
        ArrayList<BookingOutputDto> foundBookings = new ArrayList<>();

        for( Booking booking : allBookings ) {
            BookingOutputDto dto = new BookingOutputDto();
            foundBookings.add(dto.toDto(booking));
        }
        return( foundBookings );
    }

    public Iterable<BookingOutputDto> getBookingsFeedbackForTeacher(Long teacherId) {
        Iterable<Booking> allBookings = repos.findBookingsFeedbackForTeacher(teacherId);
        ArrayList<BookingOutputDto> foundBookings = new ArrayList<>();

        for( Booking booking : allBookings ) {
            BookingOutputDto dto = new BookingOutputDto();
            foundBookings.add(dto.toDto(booking));
        }
        return( foundBookings );
    }

    public Iterable<BookingOutputDto> getBookingsForMyStudents(Long teacherId) {
        Iterable<Booking> allBookings = repos.findBookingsForMyStudents(teacherId);
        ArrayList<BookingOutputDto> foundBookings = new ArrayList<>();

        for( Booking booking : allBookings ) {
            BookingOutputDto dto = new BookingOutputDto();
            foundBookings.add(dto.toDto(booking));
        }
        return( foundBookings );
    }






}
