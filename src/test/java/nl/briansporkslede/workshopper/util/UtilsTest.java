package nl.briansporkslede.workshopper.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import nl.briansporkslede.workshopper.model.AuthorityKey;
import org.assertj.core.api.ThrowableAssert;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static nl.briansporkslede.workshopper.util.Utils.asJsonString;
import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void testAsJsonString() {
        List<String> names = new ArrayList<>();
        names.add("Brian");
        names.add("Was");
        names.add("Here");
        assertEquals("[\"Brian\",\"Was\",\"Here\"]", asJsonString( names ) );
    }

}