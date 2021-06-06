package es.security.example.springsecuritydemo.infrastructure.persistence;

import lombok.*;

import javax.persistence.*;

@Table(name = "books")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class BookEntity {
    @Id
    private String id;

    @Column(nullable = false)
    private String title;
}
