package es.security.example.springsecuritydemo.domain.entities;

import es.security.example.springsecuritydemo.domain.valueobjects.IdValueObject;
import es.security.example.springsecuritydemo.objectmothers.EmailAddressObjectMother;
import es.security.example.springsecuritydemo.objectmothers.NameObjectMother;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserTest {

    @Test
    @SneakyThrows
    void MustAllowCreateUserWithoutId() {
        //given
        val name = NameObjectMother.getName();
        val email = EmailAddressObjectMother.getEmailAddress();

        //when
        val user = new User(name, email);

        //then
        assertEquals(name, user.getNameValueObject());
        assertEquals(email, user.getEmail());
        assertNotNull(user.getId());
        assertNotNull(user.getId().getId());
    }

    @Test
    @SneakyThrows
    void MustAllowCreateUserWithId() {
        //given
        val id = new IdValueObject();
        val name = NameObjectMother.getName();
        val email = EmailAddressObjectMother.getEmailAddress();

        //when
        val user = new User(id, name, email);

        //then
        assertEquals(id, user.getId());
        assertEquals(name, user.getNameValueObject());
        assertEquals(email, user.getEmail());
    }

}
