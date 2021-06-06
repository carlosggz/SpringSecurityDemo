package es.security.example.springsecuritydemo.domain.entities;

import es.security.example.springsecuritydemo.domain.valueobjects.IdValueObject;
import es.security.example.springsecuritydemo.domain.valueobjects.NameValueObject;
import es.security.example.springsecuritydemo.domain.valueobjects.TitleValueObject;
import lombok.*;

@RequiredArgsConstructor
@EqualsAndHashCode
public class Book {

    @Getter
    private final IdValueObject id;

    @Getter
    @Setter
    @NonNull
    private TitleValueObject title;
}
