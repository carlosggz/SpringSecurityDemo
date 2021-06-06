package es.security.example.springsecuritydemo.application.controllers;

import es.security.example.springsecuritydemo.domain.dtos.BookDto;
import es.security.example.springsecuritydemo.application.services.BooksService;
import es.security.example.springsecuritydemo.domain.exceptions.BookNotFoundException;
import es.security.example.springsecuritydemo.domain.exceptions.InvalidArgumentsException;
import es.security.example.springsecuritydemo.infrastructure.components.BookDeleteAccess;
import es.security.example.springsecuritydemo.infrastructure.components.BookListAccess;
import es.security.example.springsecuritydemo.infrastructure.components.BookReadAccess;
import es.security.example.springsecuritydemo.infrastructure.components.BookWriteAccess;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("books")
public class BooksController {

    private final BooksService booksService;

    @BookListAccess
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<BookDto> getBooks() {
        return booksService.getBooks();
    }

    @BookReadAccess
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{id}")
    public BookDto getBook(@PathVariable String id) throws Exception {

        return booksService
                .getById(id)
                .orElseThrow(() -> new BookNotFoundException(String.format("Book with id [%s] was not found", id)));
    }

    @BookWriteAccess
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void addBook(@RequestBody BookDto bookDto) throws InvalidArgumentsException {
        booksService.add(bookDto);
    }

    @BookWriteAccess
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping
    public void updateBook(@RequestBody BookDto bookDto) throws InvalidArgumentsException {
        booksService.update(bookDto);
    }

    @BookDeleteAccess
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void deleteBook(@PathVariable String id) throws InvalidArgumentsException, BookNotFoundException {
        booksService.delete(id);
    }
}
