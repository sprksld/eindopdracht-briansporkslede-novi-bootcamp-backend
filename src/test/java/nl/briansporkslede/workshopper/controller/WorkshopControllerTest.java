package nl.briansporkslede.workshopper.controller;

import nl.briansporkslede.workshopper.dto.WorkshopOutputDto;
import nl.briansporkslede.workshopper.model.Teacher;
import nl.briansporkslede.workshopper.service.CustomUserDetailsService;
import nl.briansporkslede.workshopper.service.ReservationService;
import nl.briansporkslede.workshopper.service.UserService;
import nl.briansporkslede.workshopper.service.WorkshopService;
import nl.briansporkslede.workshopper.util.JwtUtil;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.is;

import java.time.LocalDateTime;

@ActiveProfiles("test")
@WebMvcTest(WorkshopController.class)
class WorkshopControllerTest {
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
    WorkshopService workshopService;

    @MockBean
    ReservationService reservationService;

    @Disabled
    @Test
    @WithMockUser(username="admin", roles="ADMIN")    // check authorization, not authentication
    void shouldRetrieveCorrectWorkshop() throws Exception {

        Teacher teacher = new Teacher();
        teacher.setName("Fred Mulder");

        WorkshopOutputDto outputDto = new WorkshopOutputDto();
        outputDto.id = 123L;
        outputDto.title = "Plannen kun je leren";
        outputDto.dtStart = LocalDateTime.parse("2023-09-25T09:00:00");
        outputDto.duration = 45;
        outputDto.teacher = teacher;
        Mockito.when(workshopService.getWorkshop(123L )).thenReturn(outputDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/workshops/list"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(123L)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.title", is("Plannen kun je leren")))
                ;

    }
}