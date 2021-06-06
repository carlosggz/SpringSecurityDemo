package es.security.example.springsecuritydemo.infrastructure.persistence;

import es.security.example.springsecuritydemo.domain.dtos.BookDto;
import es.security.example.springsecuritydemo.domain.entities.Book;
import es.security.example.springsecuritydemo.domain.exceptions.InvalidArgumentsException;
import es.security.example.springsecuritydemo.domain.stores.BooksStore;
import es.security.example.springsecuritydemo.domain.valueobjects.IdValueObject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class JpaBooksStore implements BooksStore {

    private final BooksRepository booksRepository;

    @Override
    public List<BookDto> getBooks() {
        return booksRepository
                .findAll()
                .stream()
                .map(BooksMapper::JpaToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> getById(@NonNull IdValueObject id) throws InvalidArgumentsException {
        val book = booksRepository.findById(id.getId());

        if (book.isEmpty())
            return Optional.empty();

        val entity = BooksMapper.JpaToEntity(book.get());
        return Optional.of(entity);
    }

    @Override
    public void save(@NonNull Book book) {
        booksRepository.save(BooksMapper.EntityToJpa(book));
    }

    @Override
    public void delete(@NonNull IdValueObject id) {
        booksRepository.deleteById(id.getId());
    }
}
