package es.security.example.springsecuritydemo.application.services;

import es.security.example.springsecuritydemo.domain.dtos.BookDto;
import es.security.example.springsecuritydemo.domain.exceptions.BookNotFoundException;
import es.security.example.springsecuritydemo.domain.exceptions.InvalidArgumentsException;

import java.util.List;
import java.util.Optional;

public interface BooksService {
    List<BookDto> getBooks();
    Optional<BookDto> getById(String id) throws InvalidArgumentsException;
    void add(BookDto bookDto) throws InvalidArgumentsException;
    void update(BookDto bookDto) throws InvalidArgumentsException;
    void delete(String id) throws InvalidArgumentsException, BookNotFoundException;
}
