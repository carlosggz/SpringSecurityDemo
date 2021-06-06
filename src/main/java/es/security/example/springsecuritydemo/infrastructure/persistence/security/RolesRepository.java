package es.security.example.springsecuritydemo.infrastructure.persistence.security;

import es.security.example.springsecuritydemo.domain.ApplicationRol;
import org.springframework.data.repository.CrudRepository;

public interface RolesRepository extends CrudRepository<RoleEntity, ApplicationRol> {
}
