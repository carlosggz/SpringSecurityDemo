package es.security.example.springsecuritydemo.infrastructure;

import es.security.example.springsecuritydemo.domain.ApplicationAuthority;
import es.security.example.springsecuritydemo.domain.ApplicationRol;
import es.security.example.springsecuritydemo.domain.valueobjects.IdValueObject;
import es.security.example.springsecuritydemo.infrastructure.persistence.BooksRepository;
import es.security.example.springsecuritydemo.infrastructure.persistence.security.*;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class Bootstrap implements CommandLineRunner {

    private final RolesRepository rolesRepository;
    private final AuthoritiesRepository authoritiesRepository;
    private final UsersRepository usersRepository;
    private final BooksRepository booksRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        val listAuthority = AuthorityEntity.builder().authority(ApplicationAuthority.LIST.getAuthority()).build();
        val readAuthority = AuthorityEntity.builder().authority(ApplicationAuthority.READ.getAuthority()).build();
        val writeAuthority = AuthorityEntity.builder().authority(ApplicationAuthority.WRITE.getAuthority()).build();
        val deleteAuthority = AuthorityEntity.builder().authority(ApplicationAuthority.DELETE.getAuthority()).build();
        authoritiesRepository.saveAll(List.of(listAuthority, readAuthority, writeAuthority, deleteAuthority));

        val adminRole = RoleEntity.builder().name(ApplicationRol.ADMIN.getRole()).build();
        val userRole = RoleEntity.builder().name(ApplicationRol.USER.getRole()).build();
        rolesRepository.saveAll(List.of(adminRole, userRole));

        adminRole.setAuthorities(Set.of(listAuthority, readAuthority, writeAuthority, deleteAuthority));
        userRole.setAuthorities(Set.of(listAuthority, readAuthority));
        rolesRepository.saveAll(List.of(adminRole, userRole));

        val user = UserEntity.builder()
                .id(new IdValueObject().getId())
                .firstName("test")
                .lastName("user")
                .email("user@example.com")
                .role(userRole)
                .username("user")
                .password(passwordEncoder.encode("user"))
                .build();

        val admin = UserEntity.builder()
                .id(new IdValueObject().getId())
                .firstName("test")
                .lastName("admin")
                .email("admin@example.com")
                .role(userRole)
                .role(adminRole)
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .build();

        usersRepository.saveAll(List.of(user, admin));
    }

}
