package nl.briansporkslede.workshopper.controller;

import nl.briansporkslede.workshopper.dto.BookingBatchInputDto;
import nl.briansporkslede.workshopper.dto.BookingInputDto;
import nl.briansporkslede.workshopper.dto.BookingOutputDto;
import nl.briansporkslede.workshopper.dto.UserOutputDto;
import nl.briansporkslede.workshopper.service.BookingService;
import nl.briansporkslede.workshopper.service.UserService;
import nl.briansporkslede.workshopper.util.Utils;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("${apiPrefix}/bookings")
public class BookingController {
    private final BookingService service;
    private final UserService userService;

    public BookingController( BookingService s, UserService u ) {
        this.service = s;
        this.userService = u;
    }

    @GetMapping
    public ResponseEntity<Iterable<BookingOutputDto>> getBookings() {
        return ResponseEntity.ok(service.getBookings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBooking(@PathVariable Long id) {
        return ResponseEntity.ok(service.getBooking(id));
    }

    @PostMapping
    public ResponseEntity<String> createBooking(@Valid @RequestBody BookingInputDto bookingInputDto, BindingResult br) {
        if (br.hasErrors()) {
            String errorStr = Utils.reportErrors(br);
            return new ResponseEntity<>(errorStr, HttpStatus.BAD_REQUEST);
        } else {
            Long createdId = service.createBooking(bookingInputDto);

            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/bookings/"+createdId).toUriString()
            );
            return ResponseEntity.created(uri).body("Booking created!");
        }
    }

    @PostMapping("/batch")
    public ResponseEntity<Object> createBookings(@RequestBody List<BookingBatchInputDto> bookings, BindingResult br) {
        if (br.hasErrors()) {
            String errorStr = Utils.reportErrors(br);
            return new ResponseEntity<>(errorStr, HttpStatus.BAD_REQUEST);
        }

        service.createBookings(bookings);
        return ResponseEntity.ok("Bookingen zijn verwerkt");
    }

    @GetMapping("/forme")
    public ResponseEntity<Object> getBookingsByStudent() {
        // alleen de workshops die zijn bevestigd voor de ingelogde student

        System.out.println("****************************** in getBookingsByStudent()");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth.getPrincipal() instanceof UserDetails)) {
            throw new UsernameNotFoundException("Identiteit kan niet bevestigd worden. Daarom worden er geen workshops getoond.");
        }

        UserDetails ud = (UserDetails) auth.getPrincipal();
        UserOutputDto optionalUser = userService.getUser(ud.getUsername());
        Long studentId = optionalUser.student.getId();

        return ResponseEntity.ok(service.getBookingsByStudent(studentId));

    }

    @GetMapping("/attended-and-feedback")
    public ResponseEntity<Iterable<BookingOutputDto>> getFeedbackForMentor() {
        // alleen de feedback over voorbije workshops van leerlingen van de ingelogde mentor

        System.out.println("****************************** in getFeedbackForMentor()");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth.getPrincipal() instanceof UserDetails)) {
            throw new UsernameNotFoundException("Identiteit kan niet bevestigd worden. Daarom worden er geen workshops getoond.");
        }

        UserDetails ud = (UserDetails) auth.getPrincipal();
        UserOutputDto optionalUser = userService.getUser(ud.getUsername());
        Long mentorId = optionalUser.mentor.getId();

        System.out.println("MentorId="+mentorId);

        return ResponseEntity.ok(service.getBookingsFeedbackForMentor(mentorId));

    }

    @GetMapping("/formystudents")
    public ResponseEntity<Object> getBookingsForMyStudents() {
        // alleen de workshops die zijn bevestigd voor de ingelogde student

        System.out.println("****************************** in getBookingsForMyStudents()");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth.getPrincipal() instanceof UserDetails)) {
            throw new UsernameNotFoundException("Identiteit kan niet bevestigd worden. Daarom worden er geen workshops getoond.");
        }

        UserDetails ud = (UserDetails) auth.getPrincipal();
        UserOutputDto optionalUser = userService.getUser(ud.getUsername());
        Long teacherId = optionalUser.mentor.getId();

        return ResponseEntity.ok(service.getBookingsForMyStudents(teacherId));

    }


}