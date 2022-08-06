package es.security.example.springsecuritydemo.domain.entities;

import es.security.example.springsecuritydemo.domain.valueobjects.IdValueObject;
import es.security.example.springsecuritydemo.domain.valueobjects.TitleValueObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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
