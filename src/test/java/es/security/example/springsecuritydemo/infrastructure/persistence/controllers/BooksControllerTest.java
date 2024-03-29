package es.security.example.springsecuritydemo.infrastructure.persistence.controllers;

import es.security.example.springsecuritydemo.infrastructure.persistence.books.BooksMapper;
import es.security.example.springsecuritydemo.objectmothers.BooksObjectMother;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BooksControllerTest extends BaseControllersTest{

    static final String BOOKS_URL = "/books";

    @Test
    @SneakyThrows
    void ShouldNotAllowAnonymousAccessToListBooks() {
        mockMvc.perform(get(BOOKS_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @SneakyThrows
    void ShouldNotAllowAnonymousAccessToGetBook() {
        mockMvc.perform(get(BOOKS_URL + "/any-id"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @SneakyThrows
    void ShouldNotAllowAnonymousAccessToAddBook() {
        val dto = BooksObjectMother.getDto();
        dto.setId(null);

        mockMvc.perform(post(BOOKS_URL)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @SneakyThrows
    void ShouldNotAllowAnonymousAccessToUpdateBook() {
        val dto = BooksObjectMother.getDto();

        mockMvc.perform(put(BOOKS_URL)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @SneakyThrows
    void ShouldNotAllowAnonymousAccessToDeleteBook() {
        mockMvc.perform(delete(BOOKS_URL + "/any-id"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @SneakyThrows
    @WithUserDetails("user")
    void ShouldAllowUserAccessToListBooks() {
        mockMvc.perform(get(BOOKS_URL))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @WithUserDetails("user")
    void ShouldAllowUserAccessToGetBook() {
        val dto = BooksObjectMother.getDto();
        booksRepository.save(BooksMapper.DtoToJpa(dto));

        mockMvc.perform(get(BOOKS_URL + "/" + dto.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @WithUserDetails("user")
    void ShouldNotAllowUserAccessToAddBook() {
        val dto = BooksObjectMother.getDto();
        dto.setId(null);

        mockMvc.perform(post(BOOKS_URL)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    @WithUserDetails("user")
    void ShouldNotAllowUserAccessToUpdateBook() {
        val dto = BooksObjectMother.getDto();

        mockMvc.perform(put(BOOKS_URL)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    @WithUserDetails("user")
    void ShouldNotAllowUserAccessToDeleteBook() {
        mockMvc.perform(delete(BOOKS_URL + "/any-id"))
                .andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    @WithUserDetails("admin")
    void ShouldAllowAdminAccessToListBooks() {
        mockMvc.perform(get(BOOKS_URL))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @WithUserDetails("admin")
    void ShouldAllowAdminAccessToGetBook() {
        val dto = BooksObjectMother.getDto();
        booksRepository.save(BooksMapper.DtoToJpa(dto));

        mockMvc.perform(get(BOOKS_URL + "/" + dto.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @WithUserDetails("admin")
    void ShouldAllowAdminAccessToAddBook() {
        val dto = BooksObjectMother.getDto();
        dto.setId(null);

        mockMvc.perform(post(BOOKS_URL)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    @SneakyThrows
    @WithUserDetails("admin")
    void ShouldAllowAdminAccessToUpdateBook() {
        val dto = BooksObjectMother.getDto();
        booksRepository.save(BooksMapper.DtoToJpa(dto));

        mockMvc.perform(put(BOOKS_URL)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isAccepted());
    }

    @Test
    @SneakyThrows
    @WithUserDetails("admin")
    void ShouldAllowAdminAccessToDeleteBook() {
        val dto = BooksObjectMother.getDto();
        booksRepository.save(BooksMapper.DtoToJpa(dto));

        mockMvc.perform(delete(BOOKS_URL + "/" + dto.getId()))
                .andExpect(status().isNoContent());
    }
}
