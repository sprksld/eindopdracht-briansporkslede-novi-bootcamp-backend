package nl.briansporkslede.workshopper.controller;

import nl.briansporkslede.workshopper.dto.ReservationInputDto;
import nl.briansporkslede.workshopper.dto.ReservationOutputDto;
import nl.briansporkslede.workshopper.dto.UserOutputDto;
import nl.briansporkslede.workshopper.service.ReservationService;
import nl.briansporkslede.workshopper.service.UserService;
import nl.briansporkslede.workshopper.util.Utils;
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

@CrossOrigin
@RestController
@RequestMapping("${apiPrefix}/reservations")
public class ReservationController {
    private final ReservationService service;

    private final UserService userService;

    public ReservationController( ReservationService rs, UserService us ) {
        this.service = rs;
        this.userService = us;
    }

    @GetMapping
    public ResponseEntity<Iterable<ReservationOutputDto>> getReservations() {
        return ResponseEntity.ok(service.getReservations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getReservation(@PathVariable Long id) {
        return ResponseEntity.ok(service.getReservation(id));
    }

    @PostMapping
    public ResponseEntity<String> createReservation(@Valid @RequestBody ReservationInputDto reservationInputDto, BindingResult br) {
        if (br.hasErrors()) {
            String errorStr = Utils.reportErrors(br);
            return new ResponseEntity<>(errorStr, HttpStatus.BAD_REQUEST);
        } else {
            Long createdId = service.createReservation(reservationInputDto);

            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/reservations/"+createdId).toUriString()
            );
            return ResponseEntity.created(uri).body("Successfully created a reservation");
        }
    }

    @GetMapping("/upcoming")
    public ResponseEntity<Object> getReservationsByStudentId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth.getPrincipal() instanceof UserDetails)) {
            throw new UsernameNotFoundException("Identiteit kan niet bevestigd worden. Daarom worden er geen workshops getoond.");
        }

        UserDetails ud = (UserDetails) auth.getPrincipal();
        UserOutputDto optionalUser = userService.getUser(ud.getUsername());
        Long studentId = optionalUser.student.getId();

        return ResponseEntity.ok(service.getReservationsByStudent(studentId));

    }

    @GetMapping("/bymystudents")
    public ResponseEntity<Object> getReservationsByTeacherId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth.getPrincipal() instanceof UserDetails)) {
            throw new UsernameNotFoundException("Identiteit kan niet bevestigd worden. Daarom worden er geen workshops getoond.");
        }

        UserDetails ud = (UserDetails) auth.getPrincipal();
        UserOutputDto optionalUser = userService.getUser(ud.getUsername());
        Long teacherId = optionalUser.mentor.getId();

        return ResponseEntity.ok(service.getReservationsByTeacher(teacherId));

    }

}
