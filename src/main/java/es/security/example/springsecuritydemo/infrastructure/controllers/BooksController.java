package es.security.example.springsecuritydemo.infrastructure.controllers;

import es.security.example.springsecuritydemo.domain.BooksService;
import es.security.example.springsecuritydemo.domain.exceptions.BookNotFoundException;
import es.security.example.springsecuritydemo.domain.exceptions.InvalidArgumentsException;
import es.security.example.springsecuritydemo.domain.models.BookDto;
import es.security.example.springsecuritydemo.infrastructure.components.BookDeleteAccess;
import es.security.example.springsecuritydemo.infrastructure.components.BookListAccess;
import es.security.example.springsecuritydemo.infrastructure.components.BookReadAccess;
import es.security.example.springsecuritydemo.infrastructure.components.BookWriteAccess;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
    public String addBook(@RequestBody BookDto bookDto) throws InvalidArgumentsException {
        return booksService.add(bookDto).getId();
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
