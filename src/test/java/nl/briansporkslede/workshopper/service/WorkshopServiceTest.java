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
    @WithMockUser(username="planner", roles="PLANNER")    // check authorization, not authentication
    void shouldReturnCorrectWorkshop() {
        // arrange
        Workshop workshop = new Workshop();
        workshop.setId(123L);
        workshop.setTitle("Zomaar een workshop");
        Mockito.when(repos.findById(anyLong())).thenReturn(Optional.of(workshop));

        // act

        WorkshopOutputDto odto = service.getWorkshop(123L);

        // assert

        assertEquals("Zomaar een workshop", odto.title );

    }


}