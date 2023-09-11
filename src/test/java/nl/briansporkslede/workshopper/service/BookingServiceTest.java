package nl.briansporkslede.workshopper.service;

import nl.briansporkslede.workshopper.dto.BookingOutputDto;
import nl.briansporkslede.workshopper.model.Booking;
import nl.briansporkslede.workshopper.repository.BookingRepository;
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
class BookingServiceTest {
    @Mock
    BookingRepository repos;

    @InjectMocks
    BookingService service;

    @Test
    @WithMockUser(username="planner", roles="PLANNER")    // check authorization, not authentication
    void shouldReturnCorrectBooking() {
        // arrange
        Booking booking = new Booking();
        booking.setId(123L);
        booking.setFeedback("Leuke workshop!");
        booking.setAttended(true);
        Mockito.when(repos.findById(anyLong())).thenReturn(Optional.of(booking));

        // act
        BookingOutputDto odto = service.getBooking(123L);

        // assert
        assertEquals("Leuke workshop!", odto.feedback );

    }

}