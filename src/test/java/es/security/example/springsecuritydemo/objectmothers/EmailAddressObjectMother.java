package es.security.example.springsecuritydemo.objectmothers;

import es.security.example.springsecuritydemo.domain.valueobjects.EmailValueObject;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import net.datafaker.Faker;

@UtilityClass
public class EmailAddressObjectMother {
    Faker faker = new Faker();

    @SneakyThrows
    public EmailValueObject getEmailAddress() {
        return new EmailValueObject(faker.internet().emailAddress());
    }
}
