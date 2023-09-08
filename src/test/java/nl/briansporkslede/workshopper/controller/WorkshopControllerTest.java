package nl.briansporkslede.workshopper.controller;

import nl.briansporkslede.workshopper.dto.TeacherOutputDto;
import nl.briansporkslede.workshopper.dto.WorkshopInputDto;
import nl.briansporkslede.workshopper.dto.WorkshopOutputDto;
import nl.briansporkslede.workshopper.model.Teacher;
import nl.briansporkslede.workshopper.model.Workshop;
import nl.briansporkslede.workshopper.service.*;
import nl.briansporkslede.workshopper.util.JwtUtil;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static nl.briansporkslede.workshopper.controller.TeacherControllerTest.asJsonString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;

@ActiveProfiles("test")
@WebMvcTest(WorkshopController.class)
class WorkshopControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    JwtUtil jwtUtil;

    @MockBean
    UserService userService;

    @MockBean
    UserDetailsService userDetailsService;

    @MockBean
    CustomUserDetailsService cudServer;

    @MockBean
    WorkshopService workshopService;

    @MockBean
    ReservationService reservationService;

    @MockBean
    TeacherService teacherService;

    @Test
    @WithMockUser(username = "student", roles = "STUDENT")
        // check authorization, not authentication
    void shouldRetrieveCorrectWorkshop() throws Exception {

        Teacher teacher = new Teacher();
        teacher.setName("Fred Mulder");

        WorkshopOutputDto outputDto = new WorkshopOutputDto();
        outputDto.id = 123L;
        outputDto.title = "Plannen kun je leren";
        outputDto.dtStart = LocalDateTime.parse("2023-09-25T09:00:00");
        outputDto.duration = 45;
        outputDto.teacher = teacher;
        Mockito.when(workshopService.getWorkshop(anyLong())).thenReturn(outputDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/workshops/123"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(123)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", is("Plannen kun je leren")))
        ;

    }

    @Disabled
    @Test
    @WithMockUser(username = "planner", roles = "PLANNER") // check authorization, not authentication
    void shouldCreateWorkshop() throws Exception {
        Teacher teacher = new Teacher();
        teacher.setName("New Teacher");
        TeacherOutputDto teacherOutputDto = new TeacherOutputDto();
        teacherOutputDto = teacherOutputDto.toDto(teacher);

        Teacher creator = new Teacher();
        teacher.setName("New Creator");
        TeacherOutputDto creatorOutputDto = new TeacherOutputDto();
        creatorOutputDto = creatorOutputDto.toDto(creator);

        WorkshopInputDto inputDto = new WorkshopInputDto();
//        inputDto.dtStart = LocalDateTime.parse("2024-12-03T11:00:00");
//        inputDto.dtStart = LocalDateTime.of(2024,12,3,11,0);
        inputDto.duration = 60;
        inputDto.room = "aula";
        inputDto.category = "sport";
        inputDto.title = "Houtbewerken";
        inputDto.description = "het hout niet op";
        inputDto.teacher_id = 567L;
        inputDto.creator_id = 987L;
//        inputDto.creator = creator;
//        inputDto.teacher = teacher;

        Workshop workshop = inputDto.toClass();

        Mockito.when(teacherService.getTeacher(anyLong())).thenReturn(creatorOutputDto);
        Mockito.when(teacherService.getTeacher(anyLong())).thenReturn(teacherOutputDto);
        Mockito.when(workshopService.createWorkshop(inputDto)).thenReturn(workshop.getId());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/workshops")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputDto))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
        ;

    }

}