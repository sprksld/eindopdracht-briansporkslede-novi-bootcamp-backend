package nl.briansporkslede.workshopper.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.briansporkslede.workshopper.dto.TeacherInputDto;
import nl.briansporkslede.workshopper.dto.TeacherOutputDto;
import nl.briansporkslede.workshopper.model.Teacher;
import nl.briansporkslede.workshopper.service.CustomUserDetailsService;
import nl.briansporkslede.workshopper.service.TeacherService;
import nl.briansporkslede.workshopper.service.UserService;
import nl.briansporkslede.workshopper.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(TeacherController.class)
class TeacherControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    JwtUtil jwtUtil;

    @MockBean
    UserService userService;

    @MockBean
    CustomUserDetailsService cudService;

    @MockBean
    TeacherService teacherService;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")  // check authorization, not authentication
    void createTeacher() throws Exception {

        TeacherInputDto inputDto = new TeacherInputDto();

        Mockito.when(teacherService.createTeacher(inputDto)).thenReturn(1L);

        inputDto.name = null;
        this.mockMvc
                .perform(post("/api/v1/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputDto))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;

        inputDto.name = "peter";
        this.mockMvc
                .perform(post("/api/v1/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputDto))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                ;

    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")  // check authorization, not authentication
    void getTeacherList() throws Exception {

        Teacher teacher1 = new Teacher();
        Teacher teacher2 = new Teacher();
        teacher1.setId(123L);
        teacher1.setName("Mijnheer Jansen");
        teacher2.setId(456L);
        teacher2.setName("Mevrouw de Bok");

        List<Teacher> teacherList = new ArrayList<>();
        teacherList.add(teacher1);
        teacherList.add(teacher2);

        Mockito.when(teacherService.getTeacherList()).thenReturn(teacherList);

        this.mockMvc
                .perform(get("/api/v1/teachers/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(123)))
                .andExpect(jsonPath("$[0].name", is("Mijnheer Jansen")))
        ;

    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")  // check authorization, not authentication
    void getTeacher() throws Exception {
        Teacher teacher = new Teacher();
        teacher.setId(123L);
        teacher.setName("Mijnheer Jansen");

        TeacherOutputDto teacherOutputDto = new TeacherOutputDto().toDto(teacher);

        Mockito.when(teacherService.getTeacher(anyLong())).thenReturn(teacherOutputDto);

        this.mockMvc
                .perform(get("/api/v1/teachers/123"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(123)))
                .andExpect(jsonPath("$.name", is("Mijnheer Jansen")))
        ;

    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")  // check authorization, not authentication
    void getTeachers() throws Exception {

        Teacher teacher1 = new Teacher();
        Teacher teacher2 = new Teacher();
        teacher1.setId(123L);
        teacher1.setName("Mijnheer Jansen");
        teacher2.setId(456L);
        teacher2.setName("Mevrouw de Bok");

        List<TeacherOutputDto> teacherList = new ArrayList<>();
        teacherList.add( new TeacherOutputDto().toDto(teacher1));
        teacherList.add( new TeacherOutputDto().toDto(teacher2));

        Mockito.when(teacherService.getTeachers()).thenReturn( teacherList);

        this.mockMvc
                .perform(get("/api/v1/teachers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(123)))
                .andExpect(jsonPath("$[0].name", is("Mijnheer Jansen")))
        ;

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}