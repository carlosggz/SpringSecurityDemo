package es.security.example.springsecuritydemo.objectmothers;


import com.github.javafaker.Faker;
import es.security.example.springsecuritydemo.domain.valueobjects.NameValueObject;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class NameObjectMother {
    Faker faker = new Faker();

    @SneakyThrows
    public NameValueObject getName() {
        return new NameValueObject(faker.name().firstName(), faker.name().lastName());
    }

}
