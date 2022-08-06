package es.security.example.springsecuritydemo.infrastructure.persistence.books;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<BookEntity, String> {
}
