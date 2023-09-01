package nl.briansporkslede.workshopper;

import nl.briansporkslede.workshopper.model.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentTest {

    @Test
    public void nameShouldBeBrian() {
        Student student = new Student();

        student.setName("Brian");
        assertEquals("Brian", student.getName());
        // assetFalse, assertTrue
    }
}
