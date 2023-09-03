package nl.briansporkslede.workshopper.controller;

import nl.briansporkslede.workshopper.dto.ReservationOutputDto;
import nl.briansporkslede.workshopper.model.Student;
import nl.briansporkslede.workshopper.model.Workshop;
import nl.briansporkslede.workshopper.service.CustomUserDetailsService;
import nl.briansporkslede.workshopper.service.ReservationService;
import nl.briansporkslede.workshopper.service.UserService;
import nl.briansporkslede.workshopper.util.JwtUtil;
import org.hibernate.jdbc.Work;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@WebMvcTest(ReservationController.class)
class ReservationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    UserDetailsService userDetailsService;

    @MockBean
    CustomUserDetailsService cudServer;

    @MockBean
    JwtUtil jwtUtil;

    @MockBean
    ReservationService reservationService;

    @Test
    @WithMockUser(username="planner", roles="PLANNER")    // check authorization, not authentication
    void shouldRetrieveCorrectReservation() throws Exception {

        Student student = new Student();
        student.setName("Mark Mulder");

        Workshop workshop = new Workshop();
        workshop.setTitle("Graffiti");
        workshop.setDtStart( LocalDateTime.parse("2024-09-01T10:00:00") );
        workshop.setDuration(45);

        ReservationOutputDto outputDto = new ReservationOutputDto();
        outputDto.id = 123L;
        outputDto.dtReserved = LocalDateTime.parse("2023-09-25T09:00:00");
        outputDto.student = student;
        outputDto.workshop = workshop;

        Mockito.when(reservationService.getReservation(123L )).thenReturn(outputDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/reservations/123"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(123)))
        //.andExpect(MockMvcResultMatchers.jsonPath("$.title", is("Plannen kun je leren")))
        ;

    }
}