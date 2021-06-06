package es.security.example.springsecuritydemo.domain.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Builder
public class BookDto {

    private String id;

    private String title;
}
