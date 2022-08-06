package es.security.example.springsecuritydemo.objectmothers;

import es.security.example.springsecuritydemo.domain.valueobjects.NameValueObject;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import net.datafaker.Faker;

@UtilityClass
public class NameObjectMother {
    Faker faker = new Faker();

    @SneakyThrows
    public NameValueObject getName() {
        return new NameValueObject(faker.name().firstName(), faker.name().lastName());
    }

}
