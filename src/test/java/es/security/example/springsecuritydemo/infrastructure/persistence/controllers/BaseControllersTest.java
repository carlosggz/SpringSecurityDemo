package es.security.example.springsecuritydemo.infrastructure.persistence.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.security.example.springsecuritydemo.infrastructure.persistence.books.BooksRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

public abstract class BaseControllersTest {

    @Autowired
    BooksRepository booksRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    protected MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();

        booksRepository.deleteAll();
    }
}
