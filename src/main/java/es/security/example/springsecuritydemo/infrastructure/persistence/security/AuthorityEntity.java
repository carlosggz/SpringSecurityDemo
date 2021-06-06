package es.security.example.springsecuritydemo.infrastructure.persistence.security;

import es.security.example.springsecuritydemo.domain.ApplicationAuthority;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Table(name = "authorities")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class AuthorityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String authority;

    @ManyToMany(mappedBy = "authorities")
    private Set<RoleEntity> roles;

}
