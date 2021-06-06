package es.security.example.springsecuritydemo.application.services;

import es.security.example.springsecuritydemo.domain.dtos.BookDto;
import es.security.example.springsecuritydemo.domain.exceptions.BookNotFoundException;
import es.security.example.springsecuritydemo.domain.exceptions.InvalidArgumentsException;
import es.security.example.springsecuritydemo.domain.stores.BooksStore;
import es.security.example.springsecuritydemo.domain.valueobjects.IdValueObject;
import es.security.example.springsecuritydemo.infrastructure.persistence.BooksMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BooksServiceImpl implements BooksService {
    private final BooksStore booksStore;

    @Override
    public List<BookDto> getBooks() {
        return booksStore.getBooks();
    }

    @Override
    public Optional<BookDto> getById(@NonNull String id) throws InvalidArgumentsException {

        return booksStore
                .getById(new IdValueObject(id))
                .map(BooksMapper::EntityToDto);
    }

    @Override
    public void add(@NonNull BookDto bookDto) throws InvalidArgumentsException {
        if (!StringUtils.isBlank(bookDto.getId())) {
            throw new InvalidArgumentsException("New books must not have an id");
        }

        val book = BooksMapper.DtoToEntity(bookDto);
        booksStore.save(book);
    }

    @Override
    public void update(@NonNull BookDto bookDto) throws InvalidArgumentsException {
        if (StringUtils.isBlank(bookDto.getId())) {
            throw new InvalidArgumentsException("Invalid id");
        }

        val book = BooksMapper.DtoToEntity(bookDto);
        booksStore.save(book);
    }

    @Override
    public void delete(@NonNull String id) throws InvalidArgumentsException, BookNotFoundException {
        try {
            booksStore.delete(new IdValueObject(id));
        }
        catch (EmptyResultDataAccessException ex) {
            throw new BookNotFoundException(String.format("Book with id [%s] was not found", id));
        }
    }
}
