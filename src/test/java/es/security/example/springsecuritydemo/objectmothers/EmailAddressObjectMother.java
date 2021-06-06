package es.security.example.springsecuritydemo.objectmothers;

import com.github.javafaker.Faker;
import es.security.example.springsecuritydemo.domain.valueobjects.EmailValueObject;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EmailAddressObjectMother {
    Faker faker = new Faker();

    @SneakyThrows
    public EmailValueObject getEmailAddress() {
        return new EmailValueObject(faker.internet().emailAddress());
    }
}
