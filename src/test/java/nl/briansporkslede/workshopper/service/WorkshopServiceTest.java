package nl.briansporkslede.workshopper.service;

import nl.briansporkslede.workshopper.dto.WorkshopOutputDto;
import nl.briansporkslede.workshopper.model.Workshop;
import nl.briansporkslede.workshopper.repository.WorkshopRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class WorkshopServiceTest {

    @Mock
    WorkshopRepository repos;

    @InjectMocks
    WorkshopService service;

    @Test
//    @WithMockUser(username="planner", roles="PLANNER")    // check authorization, not authentication
    void shouldReturnCorrectWorkshop() {
        // arrange
        Workshop workshop = new Workshop();
        workshop.setId(123L);
        workshop.setTitle("Zomaar een workshop");
        workshop.setRoom("room11");
        workshop.setCategory("ckv");
        Mockito.when(repos.findById(anyLong())).thenReturn(Optional.of(workshop));

        // act

        WorkshopOutputDto odto = service.getWorkshop(123L);

        // assert

        assertEquals("Zomaar een workshop", odto.title);
        assertEquals("room11", odto.room);
        assertEquals("ckv", odto.category);

    }

    @Test
    void shouldReturnListOfRooms() {
        // arrange
        String[] foo1 = {"room1", "room2", "room3", "room4"};
        String[] foo2 = {"room1", "room2", "room3", "room4"};
        Iterable<String> rooms = Arrays.asList(foo1);

        Mockito.when(repos.findDistinctRooms()).thenReturn(rooms);

        // act

        Iterable<String> a = service.getRooms();

        // assert

        assertIterableEquals(Arrays.asList(foo2), a);
    }
}