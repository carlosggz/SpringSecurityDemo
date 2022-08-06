package es.security.example.springsecuritydemo.domain.models;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class ApiErrorResult {
    int statusCode;
    LocalDateTime timestamp;
    String message;
    String description;
}
