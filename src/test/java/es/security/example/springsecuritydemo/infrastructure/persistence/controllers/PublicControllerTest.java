package es.security.example.springsecuritydemo.infrastructure.persistence.controllers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class PublicControllerTest extends BaseControllersTest {

    @Test
    @SneakyThrows
    @DisplayName("When an anonymous user access to the public entrypoint hello then the result is ok ")
    void ShouldAllowAnonymousAccessToPublicHelloEntrypoint() {
        mockMvc.perform(get("/public/hello"))
                .andExpect(status().isOk());
    }

}
