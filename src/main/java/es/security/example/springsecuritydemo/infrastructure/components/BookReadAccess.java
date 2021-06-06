package es.security.example.springsecuritydemo.infrastructure.components;

import es.security.example.springsecuritydemo.domain.ApplicationAuthority;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('read')")
public @interface BookReadAccess {
}
