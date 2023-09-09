package nl.briansporkslede.workshopper.controller;

import nl.briansporkslede.workshopper.dto.TeacherOutputDto;
import nl.briansporkslede.workshopper.dto.WorkshopInputDto;
import nl.briansporkslede.workshopper.dto.WorkshopOutputDto;
import nl.briansporkslede.workshopper.model.Student;
import nl.briansporkslede.workshopper.model.Teacher;
import nl.briansporkslede.workshopper.model.Workshop;
import nl.briansporkslede.workshopper.service.*;
import nl.briansporkslede.workshopper.util.JwtUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static nl.briansporkslede.workshopper.util.Utils.asJsonString;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
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

    Teacher teacher, teacher1, teacher2;
    Teacher mentor, mentor1, mentor2;
    Teacher creator;
    Student student, student1, student2;
    WorkshopInputDto workshopInputDto;


    @BeforeEach
    void setUp() {
        teacher = new Teacher();
        teacher.setName("Fred Mulder");
        creator = new Teacher();
        teacher.setName("New Creator");

        workshopInputDto = new WorkshopInputDto();
//        workshopInputDto.dtStart = LocalDateTime.parse("2024-12-03T11:00:00");
//        workshopInputDto.dtStart = LocalDateTime.of(2024,12,3,11,0);
        workshopInputDto.duration = 60;
        workshopInputDto.room = "aula";
        workshopInputDto.category = "sport";
        workshopInputDto.title = "Houtbewerken";
        workshopInputDto.description = "het hout niet op";
        workshopInputDto.teacher_id = 567L;
        workshopInputDto.creator_id = 987L;
        workshopInputDto.minGradeYear = 1;
        workshopInputDto.maxGradeYear = 2;
        workshopInputDto.maxParticipants = 20;


//        workshopInputDto.creator = creator;
//        workshopInputDto.teacher = teacher;

    }

    @Test
    @WithMockUser(username = "student", roles = "STUDENT")
    void getWorkshop() throws Exception {
        // arrange
        WorkshopOutputDto outputDto = new WorkshopOutputDto();
        outputDto.id = 123L;
        outputDto.title = "Plannen kun je leren";
        outputDto.dtStart = LocalDateTime.parse("2023-09-25T09:00:00");
        outputDto.duration = 45;
        outputDto.teacher = teacher;
        Mockito.when(workshopService.getWorkshop(anyLong())).thenReturn(outputDto);

        // act and assert
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/workshops/123"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(123)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", is("Plannen kun je leren")))
        ;

    }

    @Test
    @WithMockUser(username = "planner", roles = "PLANNER")
    void createWorkshop() throws Exception {
        // arrange
        TeacherOutputDto teacherOutputDto = new TeacherOutputDto().toDto(teacher);
        TeacherOutputDto creatorOutputDto = new TeacherOutputDto().toDto(creator);

        Workshop workshop = workshopInputDto.toClass();

        Mockito.when(teacherService.getTeacher(anyLong())).thenReturn(creatorOutputDto);
        Mockito.when(teacherService.getTeacher(anyLong())).thenReturn(teacherOutputDto);
        Mockito.when(workshopService.createWorkshop(workshopInputDto)).thenReturn(workshop.getId());

        mockMvc.perform(MockMvcRequestBuilders
                    .post("/api/v1/workshops")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(workshopInputDto))
                    )
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isCreated())
        ;

    }

}