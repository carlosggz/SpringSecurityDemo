package es.security.example.springsecuritydemo.domain.stores;

import es.security.example.springsecuritydemo.domain.entities.Book;
import es.security.example.springsecuritydemo.domain.exceptions.InvalidArgumentsException;
import es.security.example.springsecuritydemo.domain.models.BookDto;
import es.security.example.springsecuritydemo.domain.valueobjects.IdValueObject;

import java.util.List;
import java.util.Optional;

public interface BooksStore {

    List<BookDto> getBooks();
    Optional<Book> getById(IdValueObject id) throws InvalidArgumentsException;
    void save(Book book);
    void delete(IdValueObject id);
}
