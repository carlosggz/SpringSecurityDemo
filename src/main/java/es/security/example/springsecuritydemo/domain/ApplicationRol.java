package es.security.example.springsecuritydemo.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApplicationRol {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String role;
}
