package es.security.example.springsecuritydemo.infrastructure.persistence.books;

import es.security.example.springsecuritydemo.domain.entities.Book;
import es.security.example.springsecuritydemo.domain.exceptions.InvalidArgumentsException;
import es.security.example.springsecuritydemo.domain.models.BookDto;
import es.security.example.springsecuritydemo.domain.valueobjects.IdValueObject;
import es.security.example.springsecuritydemo.domain.valueobjects.TitleValueObject;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public class BooksMapper {
    public BookDto EntityToDto(@NonNull Book book) {
        return BookDto.builder()
                .id(book.getId().getId())
                .title(book.getTitle().getTitle())
                .build();
    }

    public BookEntity EntityToJpa(@NonNull Book book) {
        return BookEntity.builder()
                .id(book.getId().getId())
                .title(book.getTitle().getTitle())
                .build();
    }

    public BookDto JpaToDto(@NonNull BookEntity book){
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .build();
    }

    public Book JpaToEntity(@NonNull BookEntity book) throws InvalidArgumentsException {
        return new Book(
                new IdValueObject(book.getId()),
                new TitleValueObject(book.getTitle())
        );
    }

    public Book DtoToEntity(@NonNull BookDto bookDto) throws InvalidArgumentsException {
        return new Book(
                StringUtils.isBlank(bookDto.getId()) ? new IdValueObject() : new IdValueObject(bookDto.getId()),
                new TitleValueObject(bookDto.getTitle())
        );
    }

    public BookEntity DtoToJpa(@NonNull BookDto bookDto) {
        return BookEntity.builder()
                .id(bookDto.getId())
                .title(bookDto.getTitle())
                .build();
    }
}
