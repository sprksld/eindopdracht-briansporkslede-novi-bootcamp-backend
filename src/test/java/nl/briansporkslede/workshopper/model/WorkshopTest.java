package nl.briansporkslede.workshopper.model;

import nl.briansporkslede.workshopper.dto.WorkshopInputDto;
import nl.briansporkslede.workshopper.dto.WorkshopOutputDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class WorkshopTest {

    @Test
    void testClassToDtoViceVersa() {
        // arrange

        WorkshopInputDto inputDto = new WorkshopInputDto();
        inputDto.teacher_id = 43L;
        inputDto.creator_id = 1L;
        inputDto.title = "Plannen";
        inputDto.dtStart = LocalDateTime.parse("2024-01-10T10:00:00");
        inputDto.duration = 50;
        inputDto.dtReservationsStart = LocalDateTime.parse("2024-01-10T09:00:00");
        inputDto.dtReservationsEnd = LocalDateTime.parse("2024-01-10T10:00:00");

        // act

        Workshop workshop = inputDto.toClass();
        WorkshopOutputDto outputDto = new WorkshopOutputDto();
        outputDto = outputDto.toDto(workshop);

        // assert
        assertNotNull(outputDto.teacher);
        assertNotNull(outputDto.creator);
        assertEquals("Plannen", outputDto.title);
        assertEquals("2024-01-10T10:00", outputDto.dtStart.toString());
        assertEquals(50, outputDto.duration);
        assertEquals("2024-01-10T09:00", outputDto.dtReservationsStart.toString());
        assertEquals("2024-01-10T10:00", outputDto.dtReservationsEnd.toString());

    }

}