package nl.briansporkslede.workshopper;

import nl.briansporkslede.workshopper.exception.BadRequestException;
import nl.briansporkslede.workshopper.exception.RecordNotFoundException;
import nl.briansporkslede.workshopper.exception.UsernameNotFoundException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ExceptionTest {

    @Test
    void testTheExceptions() {
        assertThrows(RecordNotFoundException.class, () -> { throw new RecordNotFoundException(); });
        assertThrows(RecordNotFoundException.class, () -> { throw new RecordNotFoundException("Heel"); } );
        assertThrows(BadRequestException.class, () -> { throw new BadRequestException(); } );
        assertThrows(BadRequestException.class, () -> { throw new BadRequestException("leuk"); } );
        assertThrows(UsernameNotFoundException.class, () -> { throw new UsernameNotFoundException("dit"); } );
    }
}
