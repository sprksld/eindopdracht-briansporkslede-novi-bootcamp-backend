package nl.briansporkslede.workshopper.service;

import nl.briansporkslede.workshopper.dto.WorkshopInputDto;
import nl.briansporkslede.workshopper.dto.WorkshopOutputDto;
import nl.briansporkslede.workshopper.exception.RecordNotFoundException;
import nl.briansporkslede.workshopper.model.Teacher;
import nl.briansporkslede.workshopper.model.Workshop;
import nl.briansporkslede.workshopper.repository.WorkshopRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkshopServiceTest {

    @Mock
    WorkshopRepository repos;

    @InjectMocks
    WorkshopService service;

    @Captor
    ArgumentCaptor<Workshop> captor;


    Workshop workshop1, workshop2, workshop3, workshopNew;
    WorkshopInputDto workshopInputDto;
    WorkshopOutputDto workshopOutputDto;
    Teacher teacher;
    Teacher mentor;


    @BeforeEach
    void setUp() {
        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("Mevrouw Bouwer");

        mentor = new Teacher();
        mentor.setId(2L);
        mentor.setName("Allround mentor");

        workshop1 = new Workshop();
        workshop1.setId(1L);
        workshop1.setTitle("DJ Workshop");

        workshop2 = new Workshop();
        workshop2.setId(2L);
        workshop2.setTitle("Schilderen");

        workshop3 = new Workshop();
        workshop3.setId(3L);
        workshop3.setTitle("Volksdansen");

        workshopInputDto = new WorkshopInputDto();
        workshopInputDto.dtStart = LocalDateTime.of(2024, 1, 1, 13, 0, 0);
        workshopInputDto.title = "Let's Doodle";
        workshopInputDto.room = "room3";
        workshopInputDto.category = "ckv";
        workshopInputDto.description = "lekker tekenen";
        workshopInputDto.teacher_id = teacher.getId();
        workshopInputDto.creator_id = mentor.getId();
        workshopInputDto.duration = 30;
        workshopInputDto.minGradeYear = 1;
        workshopInputDto.maxGradeYear = 2;
        workshopInputDto.maxParticipants = 20;
        workshopNew = workshopInputDto.toClass();

        workshopOutputDto = new WorkshopOutputDto();
    }

    @Test
    void getWorkshop() {
        // arrange
        when(repos.findById(2L)).thenReturn(Optional.ofNullable(workshop1));

        // act
        WorkshopOutputDto workshopFound = service.getWorkshop(2L);

        // assert
        assertEquals(workshop1.getTitle(), workshopFound.title);
        assertEquals(workshop1.getRoom(), workshopFound.room);
        assertEquals(workshop1.getCategory(), workshopFound.category);
        assertThrows(RecordNotFoundException.class, () -> service.getWorkshop(1234L));
    }

    @Test
    void getWorkshops() {
        // arrange
        when(repos.findAllByOrderByDtStart()).thenReturn(List.of(workshop1, workshop2));

        // act
        Iterable<WorkshopOutputDto> workshopsFound = service.getWorkshops();

        // assert
        assertEquals(workshop1.getTitle(), workshopsFound.iterator().next().title);
    }

    @Test
    void createWorkshop() {
        // arrange
        when(repos.save(any(Workshop.class))).thenReturn(workshopNew);

        // act
        service.createWorkshop(workshopInputDto);
        verify(repos, times(1)).save(captor.capture());
        Workshop workshop = captor.getValue();

        // assert
        assertEquals(workshopNew.getTitle(), workshop.getTitle());
    }

    @Test
    void updateWorkshop() {
        // arrange
        when(repos.save(any(Workshop.class))).thenReturn(workshopNew);
        when(repos.findById(anyLong())).thenReturn(Optional.of(workshop1));
        workshopInputDto.title = "New Cool Dance Workshop";
        workshopInputDto.creator_id = mentor.getId();
        workshopInputDto.teacher_id = teacher.getId();

        // act
        service.updateWorkshop(1L, workshopInputDto);
        verify(repos, times(1)).save(captor.capture());
        Workshop workshop = captor.getValue();

        // assert
        assertEquals(workshopInputDto.title, workshop.getTitle());
    }

    @Test
    void deleteWorkshop() {
        // arrange
        when(repos.existsById(3L)).thenReturn(false);
        when(repos.existsById(321L)).thenReturn(true);

        // act n assert
        assertThrows(RecordNotFoundException.class, () -> service.deleteWorkshop(321L));
        service.deleteWorkshop(3L);
        verify(repos).deleteById(3L);
    }

    @Test
    void getMyWorkshopOptions() {
        // arrange
        when(repos.findWorkshopsForMyGradeYear(anyInt())).thenReturn(List.of(workshop1, workshop2));

        // act
        Iterable<WorkshopOutputDto> result = service.getMyWorkshopOptions(1);

        // assert
        assertEquals(workshop1.getTitle(), result.iterator().next().title);
    }

    @Test
    void getWorkshopsByStudent() {
        // arrange
        when(repos.findAllOpenWorkshopsByStudent(anyLong())).thenReturn(List.of(workshop2, workshop3));

        // act
        Iterable<WorkshopOutputDto> result = service.getWorkshopsByStudent(1L);

        // assert
        assertEquals(workshop2.getTitle(), result.iterator().next().title);
    }

    @Test
    void getWorkshopsByTeacher() {
        // arrange
        when(repos.findAllWorkshopsByTeacher(anyLong())).thenReturn(List.of(workshop3, workshop1));

        // act
        Iterable<WorkshopOutputDto> result = service.getWorkshopsByTeacher(1L);

        // assert
        assertEquals(workshop3.getTitle(), result.iterator().next().title);
    }

    @Test
    void getWorkshopsByMentor() {
        // arrange
        when(repos.findAllWorkshopsByMentor(anyLong())).thenReturn(List.of(workshop2, workshop1));

        // act
        Iterable<WorkshopOutputDto> result = service.getWorkshopsByMentor(1L);

        // assert
        assertEquals(workshop2.getTitle(), result.iterator().next().title);
    }

    @Test
    void getCategories() {
        // arrange
        String[] categories = {"ckv", "sport", "techniek"};
        String[] categories2 = {"ckv", "sport", "techniek"};
        when(repos.findDistinctCategories()).thenReturn(List.of(categories));

        // act
        Iterable<String> result = service.getCategories();

        // assert
        assertIterableEquals(Arrays.asList(categories2), result);
    }

    @Test
    void getRooms() {
        // arrange
        String[] rooms = {"room1", "room2", "room3", "room4"};
        String[] rooms2 = {"room1", "room2", "room3", "room4"};
        when(repos.findDistinctRooms()).thenReturn(List.of(rooms));

        // act
        Iterable<String> result = service.getRooms();

        // assert
        assertIterableEquals(Arrays.asList(rooms2), result);
    }

}