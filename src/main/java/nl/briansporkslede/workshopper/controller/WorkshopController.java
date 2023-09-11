package nl.briansporkslede.workshopper.controller;

import nl.briansporkslede.workshopper.dto.*;
import nl.briansporkslede.workshopper.model.Workshop;
import nl.briansporkslede.workshopper.dto.UserOutputDto;
import nl.briansporkslede.workshopper.service.ReservationService;
import nl.briansporkslede.workshopper.service.UserService;
import nl.briansporkslede.workshopper.service.WorkshopService;
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
@RequestMapping("${apiPrefix}/workshops")
public class WorkshopController {
    private final WorkshopService service;
    private final UserService userService;
    private final ReservationService reservationService;


    public WorkshopController(WorkshopService s, UserService u, ReservationService r) {
        this.service = s;
        this.userService = u;
        this.reservationService = r;
    }

    @GetMapping
    public ResponseEntity<Iterable<WorkshopOutputDto>> getWorkshops() {
        return ResponseEntity.ok(service.getWorkshops());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getWorkshop(@PathVariable Long id) {
        return ResponseEntity.ok(service.getWorkshop(id));
    }

    @PostMapping
    public ResponseEntity<String> createWorkshop(@Valid @RequestBody WorkshopInputDto workshopInputDto, BindingResult br) {
        if (br.hasErrors()) {
            String errorStr = Utils.reportErrors(br);
            return new ResponseEntity<>(errorStr, HttpStatus.BAD_REQUEST);
        } else {
            Long createdId = service.createWorkshop(workshopInputDto);

            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/workshops/" + createdId).toUriString()
            );
            return ResponseEntity.created(uri).body("Workshop created!");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteWorkshop(@PathVariable Long id) {
        if (service.deleteWorkshop(id) != null)
            return new ResponseEntity<>("unknown workshop", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>("workshop removed", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/options/{gradeYear}")
    public ResponseEntity<Object> myOptions(@PathVariable Integer gradeYear) {
        return ResponseEntity.ok(service.getMyWorkshopOptions(gradeYear));
    }

    @GetMapping("/onlymine")
    public String myWorkshops() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getPrincipal() instanceof UserDetails) {
            UserDetails ud = (UserDetails) auth.getPrincipal();
            return "Workshops van ... " + ud.getUsername()
                    + "\n\n" + ud;
        } else {
            return "Hallo vreemdeling";
        }

    }

    @PostMapping("/like/{id}")
    public String myWorkshopz(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        System.out.println(auth.getDetails());

        if (auth.getPrincipal() instanceof UserDetails) {
            System.out.println("We're onto something ...");
        } else {
            System.out.println("Helaas geen match qua UserDetails");
            throw new UsernameNotFoundException("Identiteit kan niet bevestigd worden. Daarom worden er geen workshops getoond.");
        }

        UserDetails ud = (UserDetails) auth.getPrincipal();
        UserOutputDto optionalUser = userService.getUser(ud.getUsername());

        ReservationInputDto reservation = new ReservationInputDto();

        reservation.student = optionalUser.student;
        Workshop ws = new Workshop();
        ws.setId(id);
        reservation.workshop = ws;

        reservationService.createReservation(reservation);

        return ud.getUsername() + "(" + optionalUser.student.getId() + ") likes "
                + "workshop " + id
                + "\n\n"
                + ud;

    }

    @PatchMapping("/like/{workshopId}")
    public LikeDto submitLikes(@PathVariable Long workshopId, @RequestBody LikeDto dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth.getPrincipal() instanceof UserDetails)) {
            throw new UsernameNotFoundException("Identiteit kan niet bevestigd worden. Daarom worden er geen workshops getoond.");
        }

        UserDetails ud = (UserDetails) auth.getPrincipal();
        UserOutputDto optionalUser = userService.getUser(ud.getUsername());
        Long studentId = optionalUser.student.getId();

        //

        reservationService.like(studentId, workshopId, dto.likeAmount);

        System.out.println("WorkshopId = " + workshopId);
        System.out.println("LikeAmount = " + dto.likeAmount);
        System.out.println("student_id = " + studentId);

        LikeDto ret = new LikeDto();
        ret.likeAmount = dto.likeAmount;
        return ret;
    }

    @GetMapping("/upcoming")
    public ResponseEntity<Object> myWorkshopsAsStudent() {
        // alleen de workshops die bedoeld zijn voor de ingelogde student, en waarvan inschrijfperiode nog actief is

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth.getPrincipal() instanceof UserDetails)) {
            throw new UsernameNotFoundException("Identiteit kan niet bevestigd worden. Daarom worden er geen workshops getoond.");
        }

        UserDetails ud = (UserDetails) auth.getPrincipal();
        UserOutputDto optionalUser = userService.getUser(ud.getUsername());
        Long studentId = optionalUser.student.getId();

        return ResponseEntity.ok(service.getWorkshopsByStudent(studentId));

    }

    @GetMapping("/ihavetoteach")
    public ResponseEntity<Object> myWorkshopsAsTeacher() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth.getPrincipal() instanceof UserDetails)) {
            throw new UsernameNotFoundException("Identiteit kan niet bevestigd worden. Daarom worden er geen workshops getoond.");
        }

        UserDetails ud = (UserDetails) auth.getPrincipal();
        UserOutputDto optionalUser = userService.getUser(ud.getUsername());
        Long teacherId = optionalUser.mentor.getId();

        System.out.println("op zoek naar workshops gegevens door teacher " + teacherId);


        return ResponseEntity.ok(service.getWorkshopsByTeacher(teacherId));

    }

    @GetMapping("/mystudentscanchoosefrom")
    public ResponseEntity<Object> myWorkshopsAsMentor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth.getPrincipal() instanceof UserDetails)) {
            throw new UsernameNotFoundException("Identiteit kan niet bevestigd worden. Daarom worden er geen workshops getoond.");
        }

        UserDetails ud = (UserDetails) auth.getPrincipal();
        UserOutputDto optionalUser = userService.getUser(ud.getUsername());
        Long teacherId = optionalUser.mentor.getId();

        System.out.println("op zoek naar workshops gegevens door teacher " + teacherId);

        return ResponseEntity.ok(service.getWorkshopsByMentor(teacherId));

    }

    @GetMapping("/categories")
    public ResponseEntity<Object> getCategories() {
        return ResponseEntity.ok(service.getCategories());
    }

    @GetMapping("/rooms")
    public ResponseEntity<Object> getRooms() {
        return ResponseEntity.ok(service.getRooms());
    }

}
