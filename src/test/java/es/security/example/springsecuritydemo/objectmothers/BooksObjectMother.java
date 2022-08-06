package es.security.example.springsecuritydemo.objectmothers;

import es.security.example.springsecuritydemo.domain.entities.Book;
import es.security.example.springsecuritydemo.domain.models.BookDto;
import es.security.example.springsecuritydemo.domain.valueobjects.IdValueObject;
import es.security.example.springsecuritydemo.domain.valueobjects.TitleValueObject;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import net.datafaker.Faker;

@UtilityClass
public class BooksObjectMother {
    private final Faker faker = new Faker();

    @SneakyThrows
    public Book getBook() {
        return new Book(new IdValueObject(), new TitleValueObject(faker.book().title()));
    }

    public BookDto getDto() {
        return BookDto.builder()
                .id(new IdValueObject().getId())
                .title(faker.book().title())
                .build();
    }
}
