package es.security.example.springsecuritydemo.domain.valueobjects;

import es.security.example.springsecuritydemo.domain.exceptions.InvalidArgumentsException;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NameValueObjectTest {

    @ParameterizedTest
    @MethodSource("provideInvalidName")
    void InvalidNameThrowsException(String firstName, String lastName) {
        assertThrows(InvalidArgumentsException.class, () -> new NameValueObject(firstName, lastName));
    }

    @ParameterizedTest
    @MethodSource("provideValidName")
    @SneakyThrows
    void MustAllowValidName(String firstName, String lastName) {
        val name = new NameValueObject(firstName, lastName);

        assertEquals(firstName, name.getFirstName());
        assertEquals(lastName, name.getLastName());
    }

    private static Stream<Arguments> provideInvalidName() {
        return Stream.of(
                Arguments.of((String)null, (String)null),
                Arguments.of((String)null, "test"),
                Arguments.of("test", (String)null),

                Arguments.of("", ""),
                Arguments.of("", "test"),
                Arguments.of("test", ""),

                Arguments.of(" ", " "),
                Arguments.of(" ", "test"),
                Arguments.of("test", " ")
        );
    }

    private static Stream<Arguments> provideValidName() {
        return Stream.of(
                Arguments.of("test first", "test last")
        );
    }
}
