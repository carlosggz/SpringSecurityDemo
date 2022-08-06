package es.security.example.springsecuritydemo.domain.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDto {

    private String id;

    private String title;
}
