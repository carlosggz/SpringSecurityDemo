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

class EmailValueObjectTest {

    @ParameterizedTest
    @MethodSource("provideInvalidEmailAddresses")
    void NullOrEmptyEmailThrowsException(String emailAddress) {
        assertThrows(InvalidArgumentsException.class, () -> new EmailValueObject(emailAddress));
    }

    @ParameterizedTest
    @MethodSource("provideValidEmailAddresses")
    @SneakyThrows
    void MustAllowValidEmailAddress(String emailAddress) {
        val email = new EmailValueObject(emailAddress);

        assertEquals(emailAddress, email.getEmail());
    }

    private static Stream<Arguments> provideInvalidEmailAddresses() {
        return Stream.of(
                Arguments.of((String)null),
                Arguments.of(""),
                Arguments.of("  "),
                Arguments.of("not valid"),
                Arguments.of("gmail.com"),
                Arguments.of("@gmail.com"),
                Arguments.of(".11@gmail.com"),
                Arguments.of("a...@gmail.com")
        );
    }

    private static Stream<Arguments> provideValidEmailAddresses() {
        return Stream.of(
                Arguments.of("test@gmail.com"),
                Arguments.of("test.two@gmail.com"),
                Arguments.of("test@gmail.uk.co"),
                Arguments.of("11@gmail.com"),
                Arguments.of("test-another@gmail.com")
        );
    }

}
