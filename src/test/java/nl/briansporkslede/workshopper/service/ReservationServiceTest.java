package nl.briansporkslede.workshopper.service;

import nl.briansporkslede.workshopper.dto.ReservationOutputDto;
import nl.briansporkslede.workshopper.model.Reservation;
import nl.briansporkslede.workshopper.model.Student;
import nl.briansporkslede.workshopper.model.Workshop;
import nl.briansporkslede.workshopper.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
    @Mock
    ReservationRepository repos;

    @InjectMocks
    ReservationService service;

    @Test
    @WithMockUser(username="planner", roles="PLANNER")    // check authorization, not authentication
    void shouldReturnCorrectReservation() {
        // arrange

        Workshop workshop = new Workshop();
        workshop.setTitle("Graffiti");
        Student student = new Student();
        student.setName("Pietje Puk");

        Reservation reservation = new Reservation();
        reservation.setId(123L);
        reservation.setStudent(student);
        reservation.setWorkshop(workshop);
        reservation.setDtReserved(LocalDateTime.parse("2023-09-25T09:00"));
        reservation.setDtCancelled(LocalDateTime.parse("2023-09-25T09:01"));
        reservation.setDtProcessed(LocalDateTime.parse("2023-09-25T09:02"));
        Mockito.when(repos.findById(anyLong())).thenReturn(Optional.of(reservation));

        // act

        ReservationOutputDto odto = service.getReservation(123L);

        // assert

        assertEquals(123L, odto.id );
        assertEquals(LocalDateTime.parse("2023-09-25T09:00"), odto.dtReserved);
        assertEquals(LocalDateTime.parse("2023-09-25T09:01"), odto.dtCancelled);
        assertEquals(LocalDateTime.parse("2023-09-25T09:02"), odto.dtProcessed);

    }
}