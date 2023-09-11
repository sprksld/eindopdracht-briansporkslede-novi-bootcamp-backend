package nl.briansporkslede.workshopper.integrationTests;

import nl.briansporkslede.workshopper.dto.WorkshopInputDto;
import nl.briansporkslede.workshopper.model.Student;
import nl.briansporkslede.workshopper.model.Teacher;
import nl.briansporkslede.workshopper.model.Workshop;
import nl.briansporkslede.workshopper.repository.TeacherRepository;
import nl.briansporkslede.workshopper.repository.WorkshopRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static nl.briansporkslede.workshopper.util.Utils.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class WorkshopIntegrationTest {

    @Autowired
    WorkshopRepository repos;
    Workshop workshop, workshop1, workshop2, workshop3;
    Teacher teacher, teacher1, teacher2, teacher3;
    Teacher mentor, mentor1, mentor2, mentor3;
    Student student, student1, student2, student3;
    WorkshopInputDto workshopInputDto;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TeacherRepository teacherRepository;

    @BeforeEach
    void setUp() {
        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("Arie Plomp");
        teacherRepository.save(teacher);

        mentor = new Teacher();
        mentor.setId(2L);
        mentor.setName("Karel Schilder");
        teacherRepository.save(mentor);

        workshop1 = new Workshop();
        workshop1.setDtStart(LocalDateTime.of(2024, 1, 1, 13, 0, 0));
        workshop1.setTitle("Kleien");
        workshop1.setDescription("lekker tekenen");
        workshop1.setRoom("room3");
        workshop1.setCategory("ckv");
        workshop1.setDuration(30);
        workshop1.setMinGradeYear(1);
        workshop1.setMaxGradeYear(2);
        workshop1.setMaxParticipants(20);

        workshop2 = new Workshop();
        workshop2.setDtStart(LocalDateTime.of(2024, 2, 1, 11, 0, 0));
        workshop2.setTitle("Breien");
        workshop2.setDescription("insteken omslaan etc");
        workshop2.setRoom("room3");
        workshop2.setCategory("ckv");
        workshop2.setDuration(30);
        workshop2.setMinGradeYear(1);
        workshop2.setMaxGradeYear(2);
        workshop2.setMaxParticipants(20);

        repos.save(workshop1);
        repos.save(workshop2);

        workshopInputDto = new WorkshopInputDto();
        workshopInputDto.dtStart = LocalDateTime.of(2024, 1, 1, 13, 0, 0);
        workshopInputDto.duration = 30;
        workshopInputDto.room = "room3";
        workshopInputDto.category = "ckv";
        workshopInputDto.title = "Doodles tekenen";
        workshopInputDto.description = "lekker tekenen";
        workshopInputDto.teacher_id = teacher.getId();
        workshopInputDto.creator_id = mentor.getId();
        workshopInputDto.teacher = teacher;
        workshopInputDto.creator = mentor;
        workshopInputDto.minGradeYear = 1;
        workshopInputDto.maxGradeYear = 2;
        workshopInputDto.maxParticipants = 20;

    }

    @Test
    @WithMockUser(username = "planner", roles = "PLANNER")
    void getFirstWorkshop() throws Exception {
        mockMvc.perform(get("/api/v1/workshops/1"))
                .andExpect(status().isOk())
        ;
    }

    @Test
    @WithMockUser(username = "planner", roles = "PLANNER")
    void getAllWorkshops() throws Exception {
        mockMvc.perform(get("/api/v1/workshops"))
                .andExpect(status().isOk())
        ;
    }

    @Test
    @WithMockUser(username = "planner", roles = "PLANNER")
    void createWorkshop() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                    .post("/api/v1/workshops")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(workshopInputDto))
                    )
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isCreated())
        ;
    }

    @Test
    @WithMockUser(username = "planner", roles = "PLANNER")
    void getCategories() throws Exception {
        mockMvc.perform(get("/api/v1/workshops/categories"))
                .andExpect(status().isOk())
        ;
    }

    @Test
    @WithMockUser(username = "planner", roles = "PLANNER")
    void getRooms() throws Exception {
        mockMvc.perform(get("/api/v1/workshops/rooms"))
                .andExpect(status().isOk())
        ;
    }

}
