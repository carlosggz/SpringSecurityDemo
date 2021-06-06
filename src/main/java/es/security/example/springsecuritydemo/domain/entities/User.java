package es.security.example.springsecuritydemo.domain.entities;

import es.security.example.springsecuritydemo.domain.valueobjects.EmailValueObject;
import es.security.example.springsecuritydemo.domain.valueobjects.IdValueObject;
import es.security.example.springsecuritydemo.domain.valueobjects.NameValueObject;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class User {
    @Getter
    private final IdValueObject id;

    @Getter
    @Setter
    @NonNull
    private NameValueObject nameValueObject;

    @Getter
    @Setter
    @NonNull
    private EmailValueObject email;

    public User(@NonNull NameValueObject nameValueObject, @NonNull EmailValueObject email) {
        this(new IdValueObject(), nameValueObject, email);
    }

    public User(@NonNull IdValueObject id, @NonNull NameValueObject nameValueObject, @NonNull EmailValueObject email) {
        this.id = id;
        this.nameValueObject = nameValueObject;
        this.email = email;
    }

}
