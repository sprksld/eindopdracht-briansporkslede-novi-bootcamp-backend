package nl.briansporkslede.workshopper.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LikeDtoTest {

    // not a very useful test, but I like the 100% coverage on the classes :-)

    @Test
    void assigningValueMayNotFail() {

        // arrange
        LikeDto inputDto = new LikeDto();
        inputDto.likeAmount = 3;

        // act

        // assert

        assertEquals(3, inputDto.likeAmount);

    }

}