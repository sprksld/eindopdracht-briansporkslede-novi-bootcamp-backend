package nl.briansporkslede.workshopper.controller;

import nl.briansporkslede.workshopper.dto.TeacherOutputDto;
import nl.briansporkslede.workshopper.service.TeacherService;
import nl.briansporkslede.workshopper.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@WebMvcTest(TeacherController.class)
class TeacherControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    JwtUtil jwtUtil;

    @MockBean
    TeacherService TeacherService;

    @Test
    @WithMockUser(username = "planner", roles = "PLANNER")  // check authorization, not authentication
    void shouldRetrieveCorrectTeacher() throws Exception {

        TeacherOutputDto outputDto = new TeacherOutputDto();
        outputDto.id = 123L;
        outputDto.name = "Mijnheer Jansen";
        Mockito.when(TeacherService.getTeacher(123L)).thenReturn(outputDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/teachers/123"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(123L)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.title", is("Plannen")))
        ;

    }
}