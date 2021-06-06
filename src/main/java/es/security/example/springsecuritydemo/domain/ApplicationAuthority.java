package es.security.example.springsecuritydemo.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ApplicationAuthority {
    LIST("list"),
    READ("read"),
    WRITE("write"),
    DELETE("delete");

    private final String authority;
}
