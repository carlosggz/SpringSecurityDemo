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

class TitleValueObjectTest {

    @ParameterizedTest
    @MethodSource("provideInvalidTitle")
    void InvalidNameThrowsException(String title) {
        assertThrows(InvalidArgumentsException.class, () -> new TitleValueObject(title));
    }

    @ParameterizedTest
    @MethodSource("provideValidTitle")
    @SneakyThrows
    void MustAllowValidName(String title) {
        val name = new TitleValueObject(title);

        assertEquals(title, name.getTitle());
    }

    private static Stream<Arguments> provideInvalidTitle() {
        return Stream.of(
                Arguments.of((String)null),
                Arguments.of(""),
                Arguments.of(" ")
        );
    }

    private static Stream<Arguments> provideValidTitle() {
        return Stream.of(
                Arguments.of("valid title")
        );
    }
}