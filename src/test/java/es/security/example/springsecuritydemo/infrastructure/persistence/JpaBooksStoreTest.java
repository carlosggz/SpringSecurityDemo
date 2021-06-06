package es.security.example.springsecuritydemo.infrastructure.persistence;

import es.security.example.springsecuritydemo.domain.dtos.BookDto;
import es.security.example.springsecuritydemo.domain.valueobjects.IdValueObject;
import es.security.example.springsecuritydemo.objectmothers.BooksObjectMother;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JpaBooksStoreTest {

    @Autowired
    BooksRepository booksRepository;

    @Autowired
    JpaBooksStore booksStore;

    @BeforeEach
    void setup() {
        booksRepository.deleteAll();
    }

    @Test
    void getBooksReturnsExistingBooks(){
        val givenBooks = new HashMap<String, BookDto>();

        for(int index = 1; index < 10; index++) {
            val book = BooksObjectMother.getDto();
            givenBooks.put(book.getId(), book);
            booksRepository.save(BooksMapper.DtoToJpa(book));
        }

        val existingBooks = booksStore.getBooks();

        assertEquals(givenBooks.size(), existingBooks.size());

        for(val bookDto: existingBooks) {
            assertTrue(givenBooks.containsKey(bookDto.getId()));
            assertEquals(givenBooks.get(bookDto.getId()), bookDto);
        }
    }

    @Test
    @SneakyThrows
    void shouldReturnEmptyForNotExistingBook() {
        val book = booksStore.getById(new IdValueObject());

        assertTrue(book.isEmpty());
    }

    @Test
    @SneakyThrows
    void shouldReturnTheBookWhenUsingValidId() {
        val givenBook = BooksObjectMother.getBook();
        booksRepository.save(BooksMapper.EntityToJpa(givenBook));

        val book = booksStore.getById(givenBook.getId());

        assertTrue(book.isPresent());
        assertEquals(givenBook, book.get());
    }

    @Test
    void shouldSaveBook() {
        val givenBook = BooksObjectMother.getBook();

        booksStore.save(givenBook);

        val books = booksStore.getBooks();
        assertEquals(1, books.size());
        val givenBookDto = BooksMapper.EntityToDto(givenBook);
        assertEquals(givenBookDto, books.get(0));
    }

    @Test
    void deleteUsingInvalidIdShouldThrowException() {
        assertThrows(EmptyResultDataAccessException.class, () -> booksStore.delete(new IdValueObject()));
    }

    @Test
    void deleteUsingValidIdShouldRemoveBook() {
        val givenBook = BooksObjectMother.getBook();
        booksRepository.save(BooksMapper.EntityToJpa(givenBook));

        booksStore.delete(givenBook.getId());

        val books = booksStore.getBooks();
        assertEquals(0, books.size());
    }
}