package es.security.example.springsecuritydemo.infrastructure;

import es.security.example.springsecuritydemo.domain.ApplicationAuthority;
import es.security.example.springsecuritydemo.domain.ApplicationRol;
import es.security.example.springsecuritydemo.domain.models.BookDto;
import es.security.example.springsecuritydemo.domain.valueobjects.IdValueObject;
import es.security.example.springsecuritydemo.infrastructure.persistence.books.BooksMapper;
import es.security.example.springsecuritydemo.infrastructure.persistence.books.BooksRepository;
import es.security.example.springsecuritydemo.infrastructure.persistence.security.AuthoritiesRepository;
import es.security.example.springsecuritydemo.infrastructure.persistence.security.AuthorityEntity;
import es.security.example.springsecuritydemo.infrastructure.persistence.security.RoleEntity;
import es.security.example.springsecuritydemo.infrastructure.persistence.security.RolesRepository;
import es.security.example.springsecuritydemo.infrastructure.persistence.security.UserEntity;
import es.security.example.springsecuritydemo.infrastructure.persistence.security.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Component
@Slf4j
public class Bootstrap implements CommandLineRunner {

    private final RolesRepository rolesRepository;
    private final AuthoritiesRepository authoritiesRepository;
    private final UsersRepository usersRepository;
    private final BooksRepository booksRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @SneakyThrows
    public void run(String... args) {
        log.info("Loading default data...");

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

        var demoBook = BookDto.builder().id("12345678-1234-1234-1234-123456789012").title("Demo book").build();
        booksRepository.save(BooksMapper.DtoToJpa(demoBook));

        log.info("Data Loaded");
    }

}
