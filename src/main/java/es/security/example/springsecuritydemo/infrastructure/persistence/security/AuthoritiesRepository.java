package es.security.example.springsecuritydemo.infrastructure.persistence.security;

import org.springframework.data.repository.CrudRepository;

public interface AuthoritiesRepository extends CrudRepository<AuthorityEntity, Integer> {
}
