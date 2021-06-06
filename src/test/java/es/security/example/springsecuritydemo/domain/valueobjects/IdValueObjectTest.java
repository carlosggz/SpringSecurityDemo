package es.security.example.springsecuritydemo.domain.valueobjects;

import es.security.example.springsecuritydemo.domain.exceptions.InvalidArgumentsException;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class IdValueObjectTest {

    @ParameterizedTest
    @MethodSource("provideInvalidId")
    void NullOrInvalidIdThrowsException(String id) {
        assertThrows(InvalidArgumentsException.class, () -> new IdValueObject(id));
    }

    @ParameterizedTest
    @MethodSource("provideValidId")
    @SneakyThrows
    void MustAllowValidId(String id) {
        val value = new IdValueObject(id);

        assertEquals(id, value.getId());
    }

    @Test
    void MustCreateNewIdIfItIsNotProvided() {
        val value = new IdValueObject();

        assertNotNull(value.getId());
    }

    private static Stream<Arguments> provideInvalidId() {
        return Stream.of(
                Arguments.of((String)null),
                Arguments.of(""),
                Arguments.of("  "),
                Arguments.of("not valid"),
                Arguments.of("1234")
        );
    }

    private static Stream<Arguments> provideValidId() {
        return Stream.of(
                Arguments.of(UUID.randomUUID().toString())
        );
    }
}