package es.security.example.springsecuritydemo.application.services;

import es.security.example.springsecuritydemo.domain.dtos.BookDto;
import es.security.example.springsecuritydemo.domain.entities.Book;
import es.security.example.springsecuritydemo.domain.exceptions.BookNotFoundException;
import es.security.example.springsecuritydemo.domain.exceptions.InvalidArgumentsException;
import es.security.example.springsecuritydemo.domain.stores.BooksStore;
import es.security.example.springsecuritydemo.domain.valueobjects.IdValueObject;
import es.security.example.springsecuritydemo.infrastructure.persistence.BooksMapper;
import es.security.example.springsecuritydemo.objectmothers.BooksObjectMother;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BooksServiceImplTest {

    @Mock
    BooksStore booksStore;

    @InjectMocks
    BooksServiceImpl booksService;

    @Captor
    ArgumentCaptor<Book> bookArgumentCaptor;

    @Captor
    ArgumentCaptor<IdValueObject> idValueObjectArgumentCaptor;

    @Test
    void getBooksShouldCallStore() {
        val givenBooks = new ArrayList<BookDto>();

        for(int index = 1; index <= 10; index++) {
            givenBooks.add(BooksObjectMother.getDto());
        }

        when(booksStore.getBooks()).thenReturn(givenBooks);

        val result = booksService.getBooks();

        assertEquals(givenBooks, result);
    }

    @Test
    @SneakyThrows
    void getBookByIdReturnEmptyIfBooksDoesNotExists() {
        when(booksStore.getById(any())).thenReturn(Optional.empty());

        val result = booksService.getById(new IdValueObject().getId());

        assertTrue(result.isEmpty());
    }

    @Test
     void getBookByIdShouldThrowExceptionForInvalidId() {
        assertThrows(InvalidArgumentsException.class, () -> booksService.getById("Not a valid id"));
    }

    @Test
    @SneakyThrows
    void getBookByIdReturnTheBookIfBooksExists() {
        val givenBook = BooksObjectMother.getBook();
        when(booksStore.getById(givenBook.getId())).thenReturn(Optional.of(givenBook));

        val result = booksService.getById(givenBook.getId().getId());

        assertTrue(result.isPresent());
        assertEquals(BooksMapper.EntityToDto(givenBook), result.get());
    }

    @Test
    void addInvalidDtoThrowsException(){
        assertThrows(InvalidArgumentsException.class, () -> booksService.add(BookDto.builder().build()));
    }

    @Test
    @SneakyThrows
    void addWithIdThrowsException(){
        assertThrows(InvalidArgumentsException.class, () -> booksService.add(BooksObjectMother.getDto()));
    }

    @Test
    @SneakyThrows
    void addValidDtoCallsStoreToSave(){
        val givenDto = BooksObjectMother.getDto();
        givenDto.setId(null);

        booksService.add(givenDto);

        verify(booksStore).save(bookArgumentCaptor.capture());
        val savedBook = bookArgumentCaptor.getValue();
        assertTrue(Objects.nonNull(savedBook));
        givenDto.setId(savedBook.getId().getId());
        assertEquals(givenDto, BooksMapper.EntityToDto(savedBook));
    }

    @Test
    void updateInvalidDtoThrowsException(){
        assertThrows(InvalidArgumentsException.class, () -> booksService.update(BookDto.builder().build()));
    }

    @Test
    @SneakyThrows
    void updateValidDtoCallsStoreToSave(){
        val givenDto = BooksObjectMother.getDto();

        booksService.update(givenDto);

        verify(booksStore).save(bookArgumentCaptor.capture());
        val savedBook = bookArgumentCaptor.getValue();
        assertTrue(Objects.nonNull(savedBook));
        assertEquals(givenDto, BooksMapper.EntityToDto(savedBook));
    }

    @Test
    void deleteWithInvalidIdThrowsException() {
        assertThrows(InvalidArgumentsException.class, () -> booksService.delete("invalid id"));
    }

    @Test
    void deleteWithInvalidBookIdThrowsException() {
        doThrow(EmptyResultDataAccessException.class).when(booksStore).delete(any());

        assertThrows(BookNotFoundException.class, () -> booksService.delete(new IdValueObject().getId()));
    }

    @Test
    @SneakyThrows
    void deleteWithValidBookCallsStore() {
        val id = new IdValueObject();
        booksService.delete(id.getId());

        verify(booksStore).delete(idValueObjectArgumentCaptor.capture());
        assertEquals(id, idValueObjectArgumentCaptor.getValue());
    }
}