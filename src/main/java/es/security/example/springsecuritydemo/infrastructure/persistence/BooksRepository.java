package es.security.example.springsecuritydemo.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface BooksRepository extends JpaRepository<BookEntity, String> {
}
